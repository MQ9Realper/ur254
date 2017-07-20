package com.patriot.ur254.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.patriot.ur254.R;
import com.patriot.ur254.activities.CustomLightboxActivity;
import com.patriot.ur254.utils.DeveloperKey;

import java.util.HashMap;
import java.util.Map;

import youtubecontent.YouTubeContent;

/**
 * Created by dennismwebia on 09/11/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {
    private Context mContext;
    private Map<View, YouTubeThumbnailLoader> mLoaders;
    private View itemView;

    public class VideosViewHolder extends RecyclerView.ViewHolder {
        private YouTubeThumbnailView thumb;
        private TextView title;

        public VideosViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView_title);
            thumb = (YouTubeThumbnailView) view.findViewById(R.id.imageView_thumbnail);
        }

    }

    public VideosAdapter(Context context) {
        this.mContext = context;
        this.mLoaders = new HashMap<>();
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_videos, parent, false);

        return new VideosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideosViewHolder holder, final int position) {
        //The item at the current position
        final YouTubeContent.YouTubeVideo item = YouTubeContent.ITEMS.get(position);
        final YouTubeThumbnailLoader loader = mLoaders.get(holder.thumb);
        if (item != null) {
            //Set the title
            holder.title.setText(item.title);
            holder.thumb.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(item.id);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                            final String errorMessage = errorReason.toString();
                            Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    final String errorMessage = youTubeInitializationResult.toString();
                    Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                }
            });

        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DEVELOPER_KEY = DeveloperKey.DEVELOPER_KEY;
                final YouTubeContent.YouTubeVideo video = YouTubeContent.ITEMS.get(holder.getAdapterPosition());

                //Opens in the the custom Lightbox activity
                final Intent lightboxIntent = new Intent(mContext, CustomLightboxActivity.class);
                lightboxIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
                mContext.startActivity(lightboxIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return YouTubeContent.ITEMS.size();
    }
}
