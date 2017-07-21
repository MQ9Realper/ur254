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
import com.patriot.ur254.adapters.ProjectsAdapter;
import com.patriot.ur254.adapters.VideosAdapter;

import java.util.ArrayList;
import java.util.List;

import models.Photo;
import models.Project;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {
    private View viewProjects;

    public ProjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewProjects = inflater.inflate(R.layout.fragment_projects, container, false);
        setUpRecyclerView();
        return viewProjects;
    }

    private void setUpRecyclerView() {
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project("Standard Gauge Railway", "The 472 kilometre Mombasa-Nairobi phase of the project will be completed in three years. The next phase will be the 505 kilometre Nairobi-Malaba route to be followed by the 132 kilometre Kisumu branch line.", R.drawable.sgr));
        projectList.add(new Project("Universal Access to ICT Services", "The Government has continuously implemented the National ICT infrastructure to improve connectivity and availability of broadband in all the 47 counties in the country. This has been achieved through the implementation of the following three initiatives.", R.drawable.ict));
        projectList.add(new Project("The National Police Service", "The Government has undertaken various initiatives to improve the efficiency and coordination of the police. This has also seem the general wellness of the police well being also addressed.", R.drawable.police));
        projectList.add(new Project("Power Generation Capacity", "The installed capacity has increased from 1,768 MW in March 2013 to the current 2,211 MW, representing a growth of 25%. A total 528.5 of new generation capacity has been added to the national grid.", R.drawable.power));

        RecyclerView recyclerView = (RecyclerView) viewProjects.findViewById(R.id.listView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new ProjectsAdapter(getActivity(), projectList));
    }

}
