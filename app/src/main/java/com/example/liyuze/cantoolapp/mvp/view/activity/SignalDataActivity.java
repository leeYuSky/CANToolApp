package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;

public class SignalDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_data);

        setupWindow();

        TextView textView = (TextView) findViewById(R.id.my);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        int childPosition = intent.getIntExtra("childPosition",Integer.MIN_VALUE);
        if(childPosition != Integer.MIN_VALUE) {
            String signal = Constants.DATATABLE.get(groupName).get(childPosition).getName();
            textView.setText(groupName + " : " + signal);
        }else{
            textView.setText("有错误");
        }
        setTitle(Constants.MESSAGETABLE.get(groupName).getMessageName());
    }

    public void setupWindow(){
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        // d.getHeight()/d.getWidth() 已经被弃用
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        p.height = (int) (metrics.heightPixels * 0.9);   //高度设置为屏幕的1.0
        p.width = (int) (metrics.widthPixels * 1.0);    //宽度设置为屏幕的0.8

//        p.height = (int) (d.getHeight() * 0.9);   //高度设置为屏幕的1.0
//        p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕的0.8

        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);
    }
}
