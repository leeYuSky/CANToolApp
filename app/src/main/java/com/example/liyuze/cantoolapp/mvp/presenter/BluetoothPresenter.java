package com.example.liyuze.cantoolapp.mvp.presenter;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.example.liyuze.cantoolapp.mvp.MyApplication.MyApplication;
import com.example.liyuze.cantoolapp.mvp.view.activity.MainActivity;
import com.example.liyuze.cantoolapp.mvp.view.mvpView.MvpMainView;

import java.util.UUID;

/**
 * Created by liyuze on 17/10/11.
 */

public class BluetoothPresenter {

    private static final String TAG = "BluetoothPresenter";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    MvpMainView mvpMainView;

    BluetoothAdapter mBluetoothAdapter;

    public BluetoothPresenter(MvpMainView mvpMainView){
        this.mvpMainView = mvpMainView;
    }

    private void setUp(){



    }



}
