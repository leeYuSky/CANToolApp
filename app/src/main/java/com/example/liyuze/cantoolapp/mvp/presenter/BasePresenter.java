package com.example.liyuze.cantoolapp.mvp.presenter;

import android.content.Context;

/**
 * Created by liyuze on 17/10/5.
 */

public abstract class BasePresenter {

    Context mContext;
    public void attach(Context context){
        mContext = context;
    }
    public void onPause(){}
    public void onResume(){}
    public void onDestroy(){
        // 防止引起内存泄漏
        mContext = null;
    }
}
