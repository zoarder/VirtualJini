package com.technologies.virtualjini.adapters;

/**
 * Created by ZOARDER on 10/28/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.jaazmultimedia.core.DisplayImageOptions;
import com.nostra13.jaazmultimedia.core.ImageLoader;
import com.nostra13.jaazmultimedia.core.assist.FailReason;
import com.nostra13.jaazmultimedia.core.listener.ImageLoadingProgressListener;
import com.nostra13.jaazmultimedia.core.listener.SimpleImageLoadingListener;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.models.ToletDetailImage;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    List<ToletDetailImage> mImageList;

    private LayoutInflater inflater;

    private DisplayImageOptions options;

    public CustomAdapter(Context context, List<ToletDetailImage> mImageList) {
        inflater = LayoutInflater.from(context);
        this.mImageList = mImageList;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.logo)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_slider, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.list_item_slider_image);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.list_item_slider_progress);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance()
                .displayImage(mImageList.get(position).getImgeUrl(), holder.imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                });

        return view;
    }
}

class ViewHolder {
    ImageView imageView;
    ProgressBar progressBar;
}