package com.example.liyuze.cantoolapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int REQUEST_OPEN_BT = 0X01; // 请求打开蓝牙

    Button mButtonOpenBt;

    BluetoothAdapter mBluetoothAdapter;

    TextView mResult_text;

    Button mButtonStartSearch;

    private static final int ACCESS_LOCATION = 1001;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG,"方法零");
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.e(TAG,"方法一");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mResult_text.append(device.getName() + " : "+ device.getAddress() + System.getProperty("line.separator"));
                    Log.e(TAG,"方法二");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜素完成
                Log.e(TAG,"方法三");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 申请权限 http://blog.csdn.net/lqhed/article/details/52266507
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

        // 获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 判断蓝牙功能是否存在
        if(mBluetoothAdapter == null){
            showToast("该设备不支持蓝牙");
            return;
        }

        // 获取名字 MAC地址
        String name = mBluetoothAdapter.getName();
        String MAC = mBluetoothAdapter.getAddress();

        Log.e(TAG,"name : " +name + " -- " + "mac : " + MAC);

        // 获取当前蓝牙状态

        int state = mBluetoothAdapter.getState();

        mButtonOpenBt = (Button) this.findViewById(R.id.btn_open_bt);

        mResult_text = (TextView) this.findViewById(R.id.result_text);

        mButtonStartSearch = (Button) this.findViewById(R.id.btn_start_search);



        // 设置广播信息过滤
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播

        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.setPriority(Integer.MAX_VALUE);//设置优先级
        // 注册蓝牙搜索广播接收者，接收并处理搜索结果
        this.registerReceiver(receiver, filter);





        mButtonOpenBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBluetoothAdapter.isEnabled()){
                    showToast("蓝牙已经处于打开状态...");
                    // 关闭蓝牙
                    boolean isClose = mBluetoothAdapter.disable();

                    Log.e(TAG,"蓝牙是否关闭:"+isClose);

                }else{
                    // 打开蓝牙
//                    boolean isOpen = mBluetoothAdapter.enable();

                    // 调用系统 API 打开
                    Intent open = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(open,REQUEST_OPEN_BT);


                }
            }
        });

        mButtonStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        mResult_text.append(device.getName() + " : " + device.getAddress()+System.getProperty("line.separator"));
                    }
                }

                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }else {
                    //开启搜索
                    mBluetoothAdapter.startDiscovery();
                }
            }
        });



    }


    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_OPEN_BT == requestCode){
            if(resultCode == RESULT_CANCELED){
                showToast("请求失败");
            }else{
                showToast("请求成功");
            }
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

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


}
