package com.example.liyuze.cantoolapp.mvp.view.activity;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.presenter.BluetoothPresenter;
import com.example.liyuze.cantoolapp.mvp.view.mvpView.MvpMainView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int REQUEST_ENABLE_BT = 1; // 请求打开蓝牙

    public static final int REQUEST_CONNECT_DEVICE = 2;

    Button mButtonOpenBt;

    BluetoothAdapter mBluetoothAdapter;

    TextView mResult_text;

    Button mButtonStartSearch;

    BluetoothPresenter mBluetoothPresenter;

    private static final int ACCESS_LOCATION = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle("hahaha");
        setSupportActionBar(toolbar);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            showToast("This device's bluetooth is not available");
            finish();
        }

        getLocationPermissons();


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

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void setupPresenter(){
        mBluetoothPresenter = new BluetoothPresenter(new MvpMainView(){

            @Override
            public void showToast(String msg) {

            }

            @Override
            public void updateView() {

            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }
        });
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
//        if(REQUEST_OPEN_BT == requestCode){
//            if(resultCode == RESULT_CANCELED){
//                showToast("请求失败");
//            }else{
//                showToast("请求成功");
//            }
//        }
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


}
