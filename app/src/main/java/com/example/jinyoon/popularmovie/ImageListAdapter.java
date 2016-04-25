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
    private String[] imageUrls;

    public ImageListAdapter(Context mContext, String[] imageUrls) {
        super(mContext, R.layout.poster_item_list, imageUrls);

        this.mContext=mContext;
        this.imageUrls=imageUrls;

        inflater=LayoutInflater.from(mContext);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(R.layout.poster_item_list, parent, false);
        }
        Picasso
                .with(mContext)
                .load(imageUrls[position])
                .into((ImageView)convertView);
        return convertView;
    }

}
