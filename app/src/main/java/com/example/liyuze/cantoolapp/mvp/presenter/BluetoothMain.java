package com.example.liyuze.cantoolapp.mvp.presenter;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;

/**
 * Created by magic on 2017/10/11.
 */

public class BluetoothMain extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int REQUEST_OPEN_BT = 0X01; // 请求打开蓝牙

    Button mButtonOpenBt;

    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
