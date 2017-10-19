package com.example.liyuze.cantoolapp.mvp.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liyuze.cantoolapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link DataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {
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
        TextView tv = (TextView)view.findViewById(R.id.fg_data);
        tv.setText(agrs1);
        return view;
    }
}