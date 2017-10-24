package com.example.liyuze.cantoolapp.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.adapter.MessageAdapter;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.view.activity.UploadDataActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link UploadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    public static final String TAG = UploadFragment.class.toString();

    ListView listView;
    List<String> dataList;
    MessageAdapter adapter;
    String[] index = new String[Constants.MESSAGETABLE.size()];

    public static UploadFragment newInstance(String param1) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString("agrs3", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        Bundle bundle = getArguments();
        Log.e(TAG,"----------------------------------------onCreateView--------------------UploadFragment");
//        String agrs1 = bundle.getString("agrs3");
//        TextView tv = (TextView)view.findViewById(R.id.fg_upload);
//        tv.setText(agrs1);

        dataList = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,canmessage> entry : Constants.MESSAGETABLE.entrySet()){
            dataList.add(entry.getValue().getMessageName());
            index[i] = entry.getValue().getMessageId();
            i += 1;
        }


        listView = view.findViewById(R.id.upload_message);
        adapter = new MessageAdapter(getActivity(),R.layout.upload_item,dataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),UploadDataActivity.class);
                intent.putExtra("messageId",index[position]);
                startActivity(intent);
            }
        });


        return view;
    }
}
