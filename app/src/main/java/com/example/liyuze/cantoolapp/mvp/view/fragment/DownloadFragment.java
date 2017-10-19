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
 * {link DownloadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DownloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadFragment extends Fragment {

    public static final String TAG = DownloadFragment.class.toString();

    public static DownloadFragment newInstance(String param1) {
        DownloadFragment fragment = new DownloadFragment();
        Bundle args = new Bundle();
        args.putString("agrs4", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DownloadFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs4");
        TextView tv = (TextView)view.findViewById(R.id.fg_download);
        tv.setText(agrs1);
        return view;
    }
}
