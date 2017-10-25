package com.example.liyuze.cantoolapp.mvp.view.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.presenter.BluetoothPresenter;
import com.example.liyuze.cantoolapp.mvp.view.activity.DeviceListActivity;
import com.example.liyuze.cantoolapp.mvp.view.activity.MainActivity;
import com.example.liyuze.cantoolapp.mvp.view.activity.TreeDataActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    public static final int REQUEST_ENABLE_BT = 1; // 请求打开蓝牙

    public static final int REQUEST_CONNECT_DEVICE = 2;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothPresenter mBluetoothPresenter;
    public ArrayAdapter<String> mConversationArrayAdapter;
    private String mConnectedDeviceName = null;
    private StringBuffer mOutStringBuffer;

    public List<String> ArrayAdapterUUID;


    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"--------------------onCreate---------------------HomeFragment");
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if(mBluetoothAdapter == null){
//            MainActivity activity = (MainActivity) getActivity();
//            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//            activity.finish();
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"--------------------onStart---------------------HomeFragment");
//        if (!mBluetoothAdapter.isEnabled()) {
//            // 调用系统 API 打开蓝牙
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//            // Otherwise, setup the chat session
//        } else if (mBluetoothPresenter == null) {
//            setupPresenter();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG,"--------------------onCreateView---------------------HomeFragment");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        mConversationView =  view.findViewById(R.id.in);
        mOutEditText =  view.findViewById(R.id.edit_text_out);
        mSendButton = view.findViewById(R.id.button_send);

        MainActivity activity = (MainActivity) getActivity();
        if(activity.mConversationArrayAdapter != null){
            mConversationArrayAdapter = activity.mConversationArrayAdapter;

            ArrayAdapterUUID = activity.ArrayAdapterUUID;
        }else{
            mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
            activity.mConversationArrayAdapter = mConversationArrayAdapter;

            ArrayAdapterUUID = new ArrayList<>();
            activity.ArrayAdapterUUID = ArrayAdapterUUID;
        }

//        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        mConversationView.setAdapter(mConversationArrayAdapter);
        mOutEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                    String message = v.getText().toString();
                    ((MainActivity)getActivity()).sendMessage(message);
                }
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                String message = mOutEditText.getText().toString();
                ((MainActivity)getActivity()).sendMessage(message);
            }
        });

        mConversationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = mConversationArrayAdapter.getItem(position);
                String uuid = ArrayAdapterUUID.get(position);
                Intent intent = new Intent(getActivity(),TreeDataActivity.class);
                intent.putExtra("uuid",uuid);
                intent.putExtra("msg",msg);
                startActivity(intent);
            }
        });

//        MainActivity activity = (MainActivity) getActivity();
//        activity.mConversationArrayAdapter = mConversationArrayAdapter;

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mBluetoothPresenter != null) {
//            // Only if the state is STATE_NONE, do we know that we haven't started already
//            if (mBluetoothPresenter.getState() == BluetoothPresenter.STATE_NONE) {
//                // Start the Bluetooth chat services
//                mBluetoothPresenter.start();
//            }
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mBluetoothPresenter != null) {
//            mBluetoothPresenter.stop();
//        }
//    }

    /**
     * @Author : liyuze
     * @Time : 17/10/19 下午9:56
     * @Description : 设置蓝牙等
     * */
//    public void setupPresenter(){
//        Log.d(TAG, "setupPresenter()");
//
//        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
//        mConversationView.setAdapter(mConversationArrayAdapter);
//
//        mOutEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
//                    String message = v.getText().toString();
//                    sendMessage(message);
//                }
//                return true;
//            }
//        });
//
//        mSendButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Send a message using content of the edit text widget
//                    TextView textView = (TextView) v.findViewById(R.id.edit_text_out);
//                    String message = textView.getText().toString();
//                    sendMessage(message);
//            }
//        });
//
//        mBluetoothPresenter = new BluetoothPresenter(mHandler);
//        mOutStringBuffer = new StringBuffer("");
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode){
//            case REQUEST_ENABLE_BT:
//                if(resultCode == RESULT_OK){
//                    setupPresenter();
//                }else{
//                    Log.d(TAG, "BT not enabled");
//                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
//                            Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
//                }
//                break;
//            case REQUEST_CONNECT_DEVICE:
//                if(resultCode == RESULT_OK){
//                    connectDevice(data);
//                }
//            default:
//
//
//        }
//    }
//
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            MainActivity activity = (MainActivity) getActivity();
//            switch (msg.what) {
//                case Constants.MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case BluetoothPresenter.STATE_CONNECTED:
//                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
//                            mConversationArrayAdapter.clear();
//                            break;
//                        case BluetoothPresenter.STATE_CONNECTING:
//                            setStatus(R.string.title_connecting);
//                            break;
//                        case BluetoothPresenter.STATE_LISTEN:
//                        case BluetoothPresenter.STATE_NONE:
//                            setStatus(R.string.title_not_connected);
//                            break;
//                    }
//                    break;
//                case Constants.MESSAGE_WRITE:
//                    byte[] writeBuf = (byte[]) msg.obj;
//                    // construct a string from the buffer
//                    String writeMessage = new String(writeBuf);
//                    mConversationArrayAdapter.add("Me:  " + writeMessage);
//                    break;
//                case Constants.MESSAGE_READ:
//                    byte[] readBuf = (byte[]) msg.obj;
//                    // construct a string from the valid bytes in the buffer
//                    String readMessage = new String(readBuf, 0, msg.arg1);
//                    char temp = readMessage.charAt(0);
//                    if(temp == '\r')
//                    {
//                        readMessage = "OK";
//                    }
//                    else if((int) temp == 7 )
//                    {
//                        readMessage = "Error";
//                    }
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
//                    break;
//                case Constants.MESSAGE_DEVICE_NAME:
//                    // save the connected device's name
//                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
//                    Toast.makeText(activity, "Connected to "
//                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//                    break;
//                case Constants.MESSAGE_TOAST:
//                    Toast.makeText(activity, msg.getData().getString(Constants.TOAST),Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };
//
//    private void sendMessage(String message) {
//        // Check that we're actually connected before trying anything
//        if (mBluetoothPresenter.getState() != BluetoothPresenter.STATE_CONNECTED) {
//            MainActivity activity = (MainActivity) getActivity();
//            Toast.makeText(activity, R.string.not_connected, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Check that there's actually something to send
//        if (message.length() > 0) {
//            // Get the message bytes and tell the BluetoothChatService to write
//            message += "\r";
//            byte[] send = message.getBytes();
//            mBluetoothPresenter.write(send);
//
//            // Reset out string buffer to zero and clear the edit text field
//            mOutStringBuffer.setLength(0);
//            mOutEditText.setText(mOutStringBuffer);
//        }
//    }
//
//    private void setStatus(int resId) {
//        MainActivity activity = (MainActivity) getActivity();
//        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
//        toolbar.setSubtitle(resId);
//    }
//
//    private void setStatus(CharSequence subTitle) {
//        MainActivity activity = (MainActivity) getActivity();
//        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
//        toolbar.setSubtitle(subTitle);
//    }
//
//    private void connectDevice(Intent data) {
//        // Get the device MAC address
//        String address = data.getExtras()
//                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//        // Get the BluetoothDevice object
//        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//        // Attempt to connect to the device
//        mBluetoothPresenter.connect(device);
//    }
}
