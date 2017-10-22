package com.example.liyuze.cantoolapp.mvp.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuze.cantoolapp.R;
import com.example.liyuze.cantoolapp.mvp.adapter.MyExpandableListViewAdapter;
import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.signal;
import com.example.liyuze.cantoolapp.mvp.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link DataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {

    public static final String TAG = DataFragment.class.toString();

    private ExpandableListView listview;
    private MyExpandableListViewAdapter adapter;


//    private Map<String, List<String>> dataset = new HashMap<>();
//    private String[] parentList = new String[]{"first", "second", "third"};
//    private List<String> childrenList1 = new ArrayList<>();
//    private List<String> childrenList2 = new ArrayList<>();
//    private List<String> childrenList3 = new ArrayList<>();

    private Map<String, List<signal>> dataset;
    private String[] parentList = new String[Constants.DATATABLE.size()];

    public static DataFragment newInstance(String param1) {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putString("agrs2", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DataFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs2");
//        TextView tv = (TextView)view.findViewById(R.id.fg_data);
//        tv.setText(agrs1);

        listview =  view.findViewById(R.id.expandablelistview_data);
        initialData();
        adapter = new MyExpandableListViewAdapter(getActivity(),dataset,parentList);
        listview.setAdapter(adapter);
        // 表示不使用系统提供的展开和收起的图标
        listview.setGroupIndicator(null);
        // 表示默认打开第一列
//        listView.expandGroup(0);

        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), "第"+groupPosition+"组的第"+childPosition+"被点击了", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return view;
    }


    private void initialData() {
        dataset = Constants.DATATABLE;
        Map<String,List<signal>> temp = Constants.DATATABLE;
        int i =0;
        for(Map.Entry<String,List<signal>> entry : temp.entrySet()){
            parentList[i] = entry.getKey();
            i+=1;
        }
//        childrenList1.add(parentList[0] + "-" + "first");
//        childrenList1.add(parentList[0] + "-" + "second");
//        childrenList1.add(parentList[0] + "-" + "third");
//        childrenList2.add(parentList[1] + "-" + "first");
//        childrenList2.add(parentList[1] + "-" + "second");
//        childrenList2.add(parentList[1] + "-" + "third");
//        childrenList3.add(parentList[2] + "-" + "first");
//        childrenList3.add(parentList[2] + "-" + "second");
//        childrenList3.add(parentList[2] + "-" + "third");
//        dataset.put(parentList[0], childrenList1);
//        dataset.put(parentList[1], childrenList2);
//        dataset.put(parentList[2], childrenList3);
    }

}
