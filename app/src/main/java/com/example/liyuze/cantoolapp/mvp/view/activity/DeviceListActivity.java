package com.example.liyuze.cantoolapp.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.adapter.PhoneAdapter;
import com.example.liyuze.cantoolapp.mvp.model.PhoneInfo;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private String[] data = {"apple","Banana","Orange","apple","Banana","Orange","apple","Banana","Orange"};

    private String[] data2 = {"apple","Banana","Orange","apple","Banana","Orange","apple","Banana","Orange"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        List<PhoneInfo> data3 = new ArrayList<>();
        data3.add(new PhoneInfo("apple","1"));
        data3.add(new PhoneInfo("Banana","1"));
        data3.add(new PhoneInfo("Orange","1"));
        data3.add(new PhoneInfo("apple","1"));
        data3.add(new PhoneInfo("Banana","1"));
        data3.add(new PhoneInfo("Orange","1"));
        data3.add(new PhoneInfo("apple","1"));
        data3.add(new PhoneInfo("Banana","1"));
        data3.add(new PhoneInfo("Orange","1"));

        ListView paired_list = (ListView) findViewById(R.id.paired_devices);
        PhoneAdapter adapter = new PhoneAdapter(
                DeviceListActivity.this, R.layout.phone, data3);
        paired_list.setAdapter(adapter);

        ListView scanned_list = (ListView) findViewById(R.id.new_devices);
        PhoneAdapter adapter2 = new PhoneAdapter(
                DeviceListActivity.this, R.layout.phone, data3);
        scanned_list.setAdapter(adapter2);
    }
}
