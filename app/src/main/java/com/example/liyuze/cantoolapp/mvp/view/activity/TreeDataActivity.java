package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.model.realSignal;
import com.example.liyuze.cantoolapp.mvp.utils.DBUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TreeDataActivity extends AppCompatActivity {

    public ArrayAdapter<String> arrayAdapter;

    ListView listView;
    TextView messageName;
    TextView messageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_data);

        setupWindow();
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        String msg = intent.getStringExtra("msg");

        setTitle(msg.split(":  ")[1]);

        messageName = (TextView) findViewById(R.id.messageName_lee);
        messageTime = (TextView) findViewById(R.id.messageTime_lee);


        List<realSignal> realSignalList = DBUtil.getRealSignalByUUID(uuid);
        List<String> list = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        canmessage canmessage = Constants.MESSAGETABLE.get(String.valueOf(realSignalList.get(0).getMessageId()));
        messageName.setText(canmessage.getMessageName() + " : " + canmessage.getByteCount() +"bytes");

        messageTime.setText(format.format(realSignalList.get(0).getDate()));
//        list.add(canmessage.getMessageName() + " : " + canmessage.getByteCount() +"bytes");
//        list.add(format.format(realSignalList.get(0).getDate()));
        for(realSignal signal:realSignalList){
            list.add(signal.getSignalName() +" : " +signal.getRealValue() );
        }


        listView = (ListView) findViewById(R.id.current_signal);
        arrayAdapter = new ArrayAdapter<String>(TreeDataActivity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

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
