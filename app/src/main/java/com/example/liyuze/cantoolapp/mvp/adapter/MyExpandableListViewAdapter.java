package com.example.liyuze.cantoolapp.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.model.signal;
import com.example.liyuze.cantoolapp.mvp.view.activity.MainActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by liyuze on 17/10/22.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    Context context;
    Map<String, List<signal>> dataset;
    String[] parentList;

    public MyExpandableListViewAdapter(Context context,Map<String, List<signal>> dataset,String[] parentList){
        this.context = context;
        this.dataset = dataset;
        this.parentList = parentList;
    }

    @Override
    public int getGroupCount() {
        return dataset.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataset.get(parentList[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataset.get(parentList[groupPosition]);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataset.get(parentList[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_item, null);
//                convertView = (View) getActivity().getLayoutInflater().from(getActivity()).inflate(
//                        R.layout.parent_item, null);
        }
        convertView.setTag(R.layout.parent_item, groupPosition);
        convertView.setTag(R.layout.child_item, -1);
        ImageView image = convertView.findViewById(R.id.open_close_parent);
        ImageView message = convertView.findViewById(R.id.parent_message_icon);
        TextView text = convertView.findViewById(R.id.parent_title);
        canmessage mess = Constants.MESSAGETABLE.get(parentList[groupPosition]);
        text.setText(mess.getMessageName());
        message.setImageResource(R.mipmap.ic_message_menu);
        if(isExpanded){
            image.setImageResource(R.mipmap.ic_close_menu);
        }else{
            image.setImageResource(R.mipmap.ic_open_menu);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
//                convertView = (View) getActivity().getLayoutInflater().from(getActivity()).inflate(
//                        R.layout.child_item, null);
        }
        convertView.setTag(R.layout.parent_item, groupPosition);
        convertView.setTag(R.layout.child_item, childPosition);
        ImageView image = convertView.findViewById(R.id.signal_image);
        image.setImageResource(R.mipmap.ic_signal);
        TextView text = convertView.findViewById(R.id.child_title);
        text.setText(dataset.get(parentList[groupPosition]).get(childPosition).getName());
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "点到了内置的textview", Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
