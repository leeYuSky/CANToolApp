package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.Manifest;
import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.signal;
import com.example.liyuze.cantoolapp.mvp.presenter.BluetoothPresenter;
import com.example.liyuze.cantoolapp.mvp.utils.datatableUtil;
import com.example.liyuze.cantoolapp.mvp.view.mvpView.MvpMainView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final int REQUEST_ENABLE_BT = 1; // 请求打开蓝牙

    public static final int REQUEST_CONNECT_DEVICE = 2;


    BluetoothAdapter mBluetoothAdapter;


    BluetoothPresenter mBluetoothPresenter;

    private static final int ACCESS_LOCATION = 1001;

    Toolbar toolbar;

    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;
    private ArrayAdapter<String> mConversationArrayAdapter;
    private String mConnectedDeviceName = null;
    private StringBuffer mOutStringBuffer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle("No device connected");
        setSupportActionBar(toolbar);

        datatableUtil.read(this,new String[]{
                "canmsg-sample.txt",
                "PowerTrain.txt",
                "Comfort.txt"
        });

//        for(Map.Entry<String,List<signal>> entry : Constants.DATATABLE.entrySet()){
//            Log.e(TAG,entry.getKey());
//            for(signal s : entry.getValue()){
//                Log.e(TAG,"   " + s.toString());
//            }
//        }



        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            showToast("This device's bluetooth is not available");
            finish();
        }

        getLocationPermissons();

        mConversationView = (ListView) findViewById(R.id.in);
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mSendButton = (Button) findViewById(R.id.button_send);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            // 调用系统 API 打开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mBluetoothPresenter == null) {

            setupPresenter();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothPresenter != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBluetoothPresenter.getState() == BluetoothPresenter.STATE_NONE) {
                // Start the Bluetooth chat services
                mBluetoothPresenter.start();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothPresenter != null) {
            mBluetoothPresenter.stop();
        }
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void setupPresenter(){
        Log.d(TAG, "setupPresenter()");

        mConversationArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mConversationView.setAdapter(mConversationArrayAdapter);

        mOutEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                    String message = v.getText().toString();
                    sendMessage(message);
                }
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                    TextView textView = (TextView) findViewById(R.id.edit_text_out);
                    String message = textView.getText().toString();
                    sendMessage(message);
            }
        });

        mBluetoothPresenter = new BluetoothPresenter(mHandler);
        mOutStringBuffer = new StringBuffer("");
    }

    /*
     * @Author : liyuze
     * @Time : 17/10/12 下午1:56
     * @Description : 标题栏菜单
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    /*
     * @Author : liyuze
     * @Time : 17/10/12 下午1:57
     * @Description : 标题栏菜单点击响应
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You clicked remove",Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }


    /*
     * @Author : liyuze
     * @Time : 17/10/12 下午1:59
     * @Description : 传递消息响应
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    setupPresenter();
                }else{
                    showToast("Bluetooth was not enabled. Leaving Bluetooth Chat.");
                    finish();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == RESULT_OK){
                    connectDevice(data);
                }
            default:


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ACCESS_LOCATION:
                if (hasAllPermissionsGranted(grantResults)) {
                    // Permission Granted

                } else {
                    // Permission Denied

                }

                break;
        }
    }

    /*
     * @Author : liyuze
     * @Time : 17/10/11 下午7:19
     * @Description : 获取 位置 权限
     * */
    private void getLocationPermissons(){
        // 申请权限
        // http://blog.csdn.net/lqhed/article/details/52266507
        // http://blog.csdn.net/TSX_xiaoxiong/article/details/53156964
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int permissionCheck = 0;
            permissionCheck = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionCheck += this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                //注册权限
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        ACCESS_LOCATION); //Any number
                Log.e(TAG,"权限获取成功");
            }else{//已获得过权限
                //进行蓝牙设备搜索操作
                Log.e(TAG,"已经获得权限");
            }
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }






    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothPresenter.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothPresenter.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothPresenter.STATE_LISTEN:
                        case BluetoothPresenter.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    char temp = readMessage.charAt(0);
                    if(temp == '\r')
                    {
                        readMessage = "OK";
                    }
                    else if((int) temp == 7 )
                    {
                        readMessage = "Error";
                    }
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                        Toast.makeText(MainActivity.this, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_TOAST:
                        Toast.makeText(MainActivity.this, msg.getData().getString(Constants.TOAST),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mBluetoothPresenter.getState() != BluetoothPresenter.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            message += "\r";
            byte[] send = message.getBytes();
            mBluetoothPresenter.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    private void setStatus(int resId) {

        toolbar.setSubtitle(resId);
    }

    private void setStatus(CharSequence subTitle) {
        toolbar.setSubtitle(subTitle);
    }

    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetoothPresenter.connect(device);
    }


}
