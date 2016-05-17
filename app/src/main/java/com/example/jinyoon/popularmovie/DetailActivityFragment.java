package com.example.jinyoon.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private MovieInfo movieInfo;
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        //ButterKnife.bind(this, rootView);

        ImageView posterImageView = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.detail_movie_title);
        TextView synopsisTextView = (TextView) rootView.findViewById(R.id.detail_movie_synopsis);
        TextView ratingTextView = (TextView) rootView.findViewById(R.id.detail_movie_rating);
        TextView dateTextView = (TextView) rootView.findViewById(R.id.detail_movie_date);

        Intent intent = getActivity().getIntent();
        if(intent!=null && intent.hasExtra("OBJECT_EXTRA")){
            movieInfo = intent.getParcelableExtra("OBJECT_EXTRA");


            Picasso.with(getContext())
                    .load(movieInfo.getPosterPath())
                    .into(posterImageView);
            titleTextView.setText(movieInfo.getTitle());
            synopsisTextView.setText(movieInfo.getSynopsis());
            ratingTextView.setText(String.format(getString(R.string.user_rating),movieInfo.getRating()));
            dateTextView.setText(String.format(getString(R.string.release_date),movieInfo.getReleaseDate()));

        }



        return rootView;
    }
}
