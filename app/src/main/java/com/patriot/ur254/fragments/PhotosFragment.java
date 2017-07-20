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
import com.patriot.ur254.adapters.PhotosAdapter;
import com.patriot.ur254.adapters.VideosAdapter;

import java.util.ArrayList;
import java.util.List;

import models.Photo;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {
    private View viewPhotos;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewPhotos = inflater.inflate(R.layout.fragment_photos, container, false);
        setUpRecyclerView();
        return viewPhotos;
    }

    private void setUpRecyclerView() {
        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo("Jubilee SmartCard Launch", R.drawable.jubilee_smartcard));
        photoList.add(new Photo("Isiolo - Moyale Road", R.drawable.jubilee_isiolo_moyale));
        photoList.add(new Photo("SGR Mombasa West Station", R.drawable.jubilee_sgr));
        photoList.add(new Photo("Resumption of Mtongwe Ferry", R.drawable.jubilee_mtongwe_ferry));
        photoList.add(new Photo("Jubilee Offices in Mariakani", R.drawable.jubilee_offices_mariakani));
        photoList.add(new Photo("Coast Leaders Join Jubilee", R.drawable.jubilee_coast_leaders));

        RecyclerView recyclerView = (RecyclerView) viewPhotos.findViewById(R.id.listView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new PhotosAdapter(getActivity(), photoList));
    }
}
