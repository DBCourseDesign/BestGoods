package com.example.feedme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class InboxFragment extends Fragment {

    private TextView mtv_inbox;
    private ImageView miv_explore;
    private  ImageView miv_box;
    private  TextView mtv_msg1;
    private  TextView mtv_msg2;
    private  TextView mtv_msg3;
    private  TextView mtv_msg4;
    private  TextView mtv_notification;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtv_inbox = getActivity().findViewById(R.id.inbox);
        miv_explore = getActivity().findViewById(R.id.explore);
        miv_box = getActivity().findViewById(R.id.box);
        mtv_msg1 = getActivity().findViewById(R.id.msg1);
        mtv_msg2 = getActivity().findViewById(R.id.msg2);
        mtv_msg3 = getActivity().findViewById(R.id.msg3);
        mtv_msg4 = getActivity().findViewById(R.id.msg4);
        mtv_notification = getActivity().findViewById(R.id.notification);
    }
}

