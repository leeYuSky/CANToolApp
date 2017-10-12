package com.example.liyuze.cantoolapp.mvp.MyApplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by liyuze on 17/10/12.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
