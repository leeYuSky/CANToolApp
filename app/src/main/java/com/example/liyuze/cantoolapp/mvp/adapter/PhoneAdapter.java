package com.example.liyuze.cantoolapp.mvp.adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.model.PhoneInfo;

import java.util.List;

/**
 * Created by liyuze on 17/10/12.
 */

public class PhoneAdapter extends ArrayAdapter<PhoneInfo> {
    private int resourceId;

    public PhoneAdapter( Context context, int textViewResourceId, List<PhoneInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        PhoneInfo phone = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView phoneName = view.findViewById(R.id.phone_name);
        phoneName.setText(phone.getName());
        return view;
    }
}
