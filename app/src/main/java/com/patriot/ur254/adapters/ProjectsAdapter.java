package com.patriot.ur254.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patriot.ur254.R;

import java.util.List;

import models.Project;

/**
 * Created by dennismwebia on 7/21/17.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder> {
    private Context mContext;
    private List<Project> projectList;
    private View itemView;

    public class ProjectsViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;
        private TextView title, description;

        public ProjectsViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView_title);
            description = (TextView) view.findViewById(R.id.textViewProjectDescription);
            thumb = (ImageView) view.findViewById(R.id.imageView_thumbnail);
        }

    }

    public ProjectsAdapter(Context context, List<Project> projectList) {
        this.mContext = context;
        this.projectList = projectList;
    }

    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_project_item, parent, false);
        return new ProjectsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProjectsViewHolder holder, final int position) {
        if (holder != null) {
            holder.title.setText(projectList.get(position).getProject_name());
            holder.description.setText(projectList.get(position).getProject_description());
            Glide.with(mContext).load(projectList.get(position).getProject_banner()).into(holder.thumb);
        }

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }
}
