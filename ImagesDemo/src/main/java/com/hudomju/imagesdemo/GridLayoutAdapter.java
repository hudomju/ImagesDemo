package com.hudomju.imagesdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hudomju.imagesdemo.io.Data;
import com.hudomju.imagesdemo.widget.SquaredImageView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class GridLayoutAdapter extends BaseAdapter {

    @Inject Context mContext;

    @Override public int getCount() {
        return Data.URLS.length;
    }

    @Override public String getItem(int position) {
        return Data.URLS[position];
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(mContext);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext) //
                .load(url) //
                .placeholder(android.R.color.black) //
                .error(android.R.color.black) //
                .fit() //
                .into(view);

        return view;
    }
}
