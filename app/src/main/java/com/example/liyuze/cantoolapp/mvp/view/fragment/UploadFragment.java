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
 * {link UploadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    public static final String TAG = UploadFragment.class.toString();

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
        String agrs1 = bundle.getString("agrs3");
        TextView tv = (TextView)view.findViewById(R.id.fg_upload);
        tv.setText(agrs1);
        return view;
    }
}
