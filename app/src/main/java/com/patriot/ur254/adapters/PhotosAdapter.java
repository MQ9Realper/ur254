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

import models.Photo;

/**
 * Created by dennismwebia on 7/20/17.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {
    private Context mContext;
    private List<Photo> photoList;
    private View itemView;

    public class PhotosViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;
        private TextView title;

        public PhotosViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView_title);
            thumb = (ImageView) view.findViewById(R.id.imageView_thumbnail);
        }

    }

    public PhotosAdapter(Context context, List<Photo> photoList) {
        this.mContext = context;
        this.photoList = photoList;
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_photos, parent, false);

        return new PhotosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotosViewHolder holder, final int position) {
        if (holder != null) {
            holder.title.setText(photoList.get(position).getPhoto_title());
            Glide.with(mContext).load(photoList.get(position).getPhoto()).into(holder.thumb);
        }

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
