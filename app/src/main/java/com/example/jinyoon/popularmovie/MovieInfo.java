package com.example.jinyoon.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jin Yoon on 4/25/2016.
 */
public class MovieInfo implements Parcelable {
    private String id;
    private String posterPath;
    private String title;
    private String synopsis;
    private String rating;
    private String releaseDate;

    public MovieInfo(Parcel source) {
        id=source.readString();
        posterPath=source.readString();
        title=source.readString();
        synopsis=source.readString();
        rating=source.readString();
        releaseDate=source.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(rating);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR
            = new Parcelable.Creator<MovieInfo>(){

        @Override
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
