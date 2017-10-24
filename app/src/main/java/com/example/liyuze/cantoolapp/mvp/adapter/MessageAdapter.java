package com.example.liyuze.cantoolapp.mvp.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;

import java.util.List;

/**
 * Created by liyuze on 17/10/23.
 */

public class MessageAdapter extends ArrayAdapter<String> {

    private int resourceId;
    public MessageAdapter( Context context,   int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position);

        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }else{
            view = convertView;
        }
        TextView messagename = view.findViewById(R.id.upload_message_item);
        messagename.setText(name);
        return view;
    }
}
