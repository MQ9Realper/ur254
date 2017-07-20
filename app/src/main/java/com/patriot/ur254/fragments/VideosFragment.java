package com.patriot.ur254.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patriot.ur254.R;
import com.patriot.ur254.adapters.VideosAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {

    private View viewVideos;


    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewVideos = inflater.inflate(R.layout.fragment_videos, container, false);
        //setVideoList();
        setUpRecyclerView();
        return viewVideos;
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) viewVideos.findViewById(R.id.listView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new VideosAdapter(getActivity()));
    }

}
