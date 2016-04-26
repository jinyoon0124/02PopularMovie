package com.example.jinyoon.popularmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageListAdapter extends ArrayAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private MovieInfo[] movieInfo;

    public ImageListAdapter(Context mContext, MovieInfo[] movieInfo) {
        super(mContext, R.layout.poster_item_list, movieInfo);

        this.mContext=mContext;
        this.movieInfo=movieInfo;

        inflater=LayoutInflater.from(mContext);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(R.layout.poster_item_list, parent, false);
        }
        Picasso
                .with(mContext)
                .load(movieInfo[position].getPosterPath())
                .into((ImageView)convertView);
        return convertView;
    }

}
