package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.model.signal;
import com.example.liyuze.cantoolapp.mvp.utils.CANMessageUtil;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jaygoo.widget.R.id.number;
import static com.jaygoo.widget.R.id.single;

public class UploadDataActivity extends AppCompatActivity {

    private static final String TAG = UploadDataActivity.class.toString();

    LinearLayout linearLayout;

    List<signal> list;
    canmessage canmessage;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        setupWindow();


        Intent intent = getIntent();
        final String messageId = intent.getStringExtra("messageId");

        list = Constants.DATATABLE.get(messageId);
        canmessage = Constants.MESSAGETABLE.get(messageId);
        setTitle(canmessage.getMessageName() +":"+ canmessage.getMessageId());

        linearLayout = (LinearLayout) findViewById(R.id.ll_addView);

        for(int i = 0;i < list.size();i++){
            addViewItem(i);
        }

        Button bt = (Button) findViewById(R.id.btn_getData);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Double> map = new HashMap<String, Double>();
                for(int i = 0;i < linearLayout.getChildCount();i++){
                    View childAt = linearLayout.getChildAt(i);
                    com.jaygoo.widget.RangeSeekBar seekBar = childAt.findViewById(R.id.rangeSeekbar);
                    TextView textView = childAt.findViewById(R.id.rangeSeekbar_text);
                    float[] list = seekBar.getCurrentRange();
//                    Log.e(TAG,textView.getText()+"  ------   "+String.valueOf(list[0]));
                    map.put((String) textView.getText(),(double)list[0]);
                }
                String result = CANMessageUtil.MessageStringify(canmessage.getMessageId(),map);
                EditText editText = (EditText) findViewById(R.id.speed_edit);
                StringBuilder speed = new StringBuilder("0400"); //1024
                try{
                    int speedInt = Integer.parseInt(editText.getText().toString());
                    if(speedInt >= 0 && speedInt <= 65535) {
                        String temp = Integer.toHexString(speedInt);
                        speed.replace(4-temp.length(),4,temp);
                    }

                }catch (Exception e){
                    Log.e(TAG,"速率解析错误！");
                }

                String messageSend = result + speed;
                Log.e(TAG,"------------messageSend-----------------" + messageSend);



//                Map<String, Double> data = new HashMap<>();
//                data.put("HVAC_AirCompressorSt", (double) 0);
//                data.put("HVAC_CorrectedExterTempVD", (double) 0);
//                data.put("HVAC_RawExterTempVD", (double) 0);
//                data.put("HVAC_EngIdleStopProhibitReq", (double) 0);
//                data.put("HVAC_ACSt", (double) 0);
//                data.put("HVAC_ACmaxSt", (double) 1);
//                data.put("HVAC_CorrectedExterTemp",-4.5);
//                data.put("HVAC_RawExterTemp", (double) 30);
//                data.put("HVAC_TempSelect",25.5);
//                data.put("HVAC_DualSt", (double) 1);
//                data.put("HVAC_AutoSt", (double) 0);
//                data.put("HVAC_Type", (double) 0);
//                data.put("HVAC_WindExitMode", (double) 5);
//                data.put("HVAC_SpdFanReq", (double) 0);
//                data.put("HVAC_TelematicsSt", (double) 1);
//                data.put("HVAC_AirCirculationSt", (double) 1);
//                data.put("HVAC_PopUpDisplayReq", (double) 1);
//                data.put("HVAC_DriverTempSelect",22.5);
//                data.put("HVAC_IonMode", (double) 3);
//                data.put("HVAC_WindExitSpd", (double) 10);
//                data.put("HVAC_PsnTempSelect",22.5);
//
//                CANMessageUtil.MessageStringify("800",data);
            }
        });




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

        if(list.get(index).getMin() < list.get(index).getMax()) {

            View rangeSliderView = View.inflate(this, R.layout.range_slider_item, null);

            LinearLayout linearLayout_item = rangeSliderView.findViewById(R.id.slider_item_lee);

            TextView textView = rangeSliderView.findViewById(R.id.rangeSeekbar_text);
            textView.setText(list.get(index).getName());


            com.jaygoo.widget.RangeSeekBar rangeSeekBar = new com.jaygoo.widget.RangeSeekBar(this);
            rangeSeekBar.setId(R.id.rangeSeekbar);
            rangeSeekBar.setSeekBarMode(single);
            rangeSeekBar.setRules((float) list.get(index).getMin(), (float) list.get(index).getMax(), 0, 1);
            rangeSeekBar.setCellMode(number);
            rangeSeekBar.setThumbResId(R.drawable.seekbar_thumb);

            if(current % 2 == 0){
                textView.setTextColor(Color.WHITE);
                linearLayout_item.setBackgroundColor(getResources().getColor(R.color.darkgray));
            }

            linearLayout_item.addView(rangeSeekBar);


            linearLayout.addView(rangeSliderView);
            current++;
        }

    }

    private void printData(){
        for(int i = 0;i < linearLayout.getChildCount();i++){
            View childAt = linearLayout.getChildAt(i);
            com.jaygoo.widget.RangeSeekBar seekBar = childAt.findViewById(R.id.rangeSeekbar);
            TextView textView = childAt.findViewById(R.id.rangeSeekbar_text);
            float[] list = seekBar.getCurrentRange();
            Log.e(TAG,textView.getText()+"  ------   "+String.valueOf(list[0]));
        }

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
