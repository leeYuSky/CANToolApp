package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.adapter.PhoneAdapter;
import com.example.liyuze.cantoolapp.mvp.model.PhoneInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceListActivity extends AppCompatActivity {

    private static final String TAG = "DeviceListActivity";

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;

    Button scan_button;

    PhoneAdapter pairedAdapter;

    PhoneAdapter scannedAdapter;

    List<PhoneInfo> pairedList = new ArrayList<>();

    List<PhoneInfo> scannedList = new ArrayList<>();

    ListView pairedListView;

    ListView scannedListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        scan_button = (Button) findViewById(R.id.button_scan);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDiscovery();
                scannedAdapter.notifyDataSetChanged();
            }
        });


        // 设置 ListView 布局及数据
        pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedAdapter = new PhoneAdapter(
                DeviceListActivity.this, R.layout.phone, pairedList);
        pairedListView.setAdapter(pairedAdapter);
        // 已配对的点击事件
        pairedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBtAdapter.cancelDiscovery();
                PhoneInfo phone = pairedList.get(position);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, phone.getMAC());

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        scannedListView = (ListView) findViewById(R.id.new_devices);
        scannedAdapter = new PhoneAdapter(
                DeviceListActivity.this, R.layout.phone, scannedList);
        scannedListView.setAdapter(scannedAdapter);
        // 未配对的点击事件
        scannedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBtAdapter.cancelDiscovery();
                PhoneInfo phone = scannedList.get(position);

                BluetoothDevice device = mBtAdapter.getRemoteDevice(phone.getMAC());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    device.createBond();
                    Log.d(TAG,"与远程设备 " + phone.getName() + " 连接成功，其 MAC 地址为 :" + phone.getMAC());
                    pairedList.add(phone);
                    scannedList.remove(position);
                    scannedAdapter.notifyDataSetChanged();
                    pairedAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG,"与远程设备 " + phone.getName() + " 连接失败!!!");
                }
            }
        });

        // 设置广播信息过滤
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播

        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.setPriority(Integer.MAX_VALUE);//设置优先级
        // 注册蓝牙搜索广播接收者，接收并处理搜索结果
        this.registerReceiver(receiver, filter);


        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // 获得已经配对的蓝牙设备
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            for (BluetoothDevice device : pairedDevices){
                pairedList.add(new PhoneInfo(device.getName(),device.getAddress()));
            }
        } else {
                //TODO
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        unregisterReceiver(receiver);
    }


    /*
     * @Author : liyuze
     * @Time : 17/10/12 下午4:06
     * @Description : 开启蓝牙的扫描
     * */
    private void doDiscovery() {

        Log.d(TAG,"正在进行扫描, 方法 : doDiscovery()");

        if(mBtAdapter.isDiscovering()){
            mBtAdapter.cancelDiscovery();
            scan_button.setText("Scan for devices");
        }else{
            setTitle("Scanning for devices...");
            mBtAdapter.startDiscovery();
            scan_button.setText("Stop scan");
        }
    }


    /*
     * @Author : liyuze
     * @Time : 17/10/12 下午3:48
     * @Description : 广播
     * */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    scannedList.add(new PhoneInfo(device.getName(),device.getAddress()));
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜素完成
                setTitle(R.string.select_device_title);
                scan_button.setText("Scan for devices");
                // TODO
            } else if(action.equals("android.bluetooth.device.action.PAIRING_REQUEST")){

            }
        }
    };
}
