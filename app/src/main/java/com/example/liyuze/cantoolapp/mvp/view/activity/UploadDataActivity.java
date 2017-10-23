package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.model.signal;

import org.w3c.dom.Text;

import java.util.List;

import static com.jaygoo.widget.R.id.number;
import static com.jaygoo.widget.R.id.single;

public class UploadDataActivity extends AppCompatActivity {

    private static final String TAG = UploadDataActivity.class.toString();

    LinearLayout linearLayout;

    List<signal> list;
    canmessage canmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        setupWindow();


        Intent intent = getIntent();
        String messageId = intent.getStringExtra("messageId");

        list = Constants.DATATABLE.get(messageId);
        canmessage = Constants.MESSAGETABLE.get(messageId);
        setTitle(canmessage.getMessageName() +":"+ canmessage.getMessageId());

        linearLayout = (LinearLayout) findViewById(R.id.ll_addView);

        for(int i = 0;i < list.size();i++){
            addViewItem(i);
        }




//        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        TextView tv1 = new TextView(this);
//        tv1.setId(R.id.leemy_text);
//        tv1.setGravity(Gravity.CENTER);
//        tv1.setText("hahaha");
//
//
//        Button bt = new Button(this);
//        LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
////        btn_params.setMargins(50,10,50,10);
//        btn_params.gravity = Gravity.CENTER;
//        bt.setText("提交");
//        bt.setBackgroundColor(getResources().getColor(R.color.button_background_color));
//        bt.setLayoutParams(btn_params);
//
//        final SeekBar seekBar = new SeekBar(this);
//        seekBar.setId(R.id.leemy_view);
//        seekBar.setLayoutParams(vlp);
//        seekBar.setMax(20);
//        seekBar.setMin(-20);
//        seekBar.setProgress(10);
//
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SeekBar seekBar1 = (SeekBar) findViewById(R.id.leemy_view);
//                Log.e(TAG,String.valueOf(seekBar1.getProgress()));
//                TextView textView = (TextView) findViewById(R.id.leemy_text);
//                textView.setText(String.valueOf(seekBar1.getProgress()));
////                Toast.makeText(UploadDataActivity.this,seekBar1.getProgress(),Toast.LENGTH_LONG);
//            }
//        });
//
//
//        linearLayout.addView(tv1);
//        linearLayout.addView(bt);
//        linearLayout.addView(seekBar);
    }

    private void addViewItem(int index) {

        View rangeSliderView = View.inflate(this, R.layout.range_slider_item, null);

        LinearLayout linearLayout_item = rangeSliderView.findViewById(R.id.slider_item_lee);

        TextView textView = rangeSliderView.findViewById(R.id.rangeSeekbar_text);
        textView.setText(list.get(index).getName());

        com.jaygoo.widget.RangeSeekBar rangeSeekBar = new com.jaygoo.widget.RangeSeekBar(this);
        rangeSeekBar.setId(R.id.rangeSeekbar);
        rangeSeekBar.setSeekBarMode(single);
        rangeSeekBar.setRules((float) list.get(index).getMin(), (float) list.get(index).getMax(),0,1);
        rangeSeekBar.setCellMode(number);

        linearLayout_item.addView(rangeSeekBar);

        linearLayout.addView(rangeSliderView);

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
