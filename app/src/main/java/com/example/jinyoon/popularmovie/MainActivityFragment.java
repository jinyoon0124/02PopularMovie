package com.example.jinyoon.popularmovie;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ImageListAdapter mPosterAdapter;
    GridView gridView;
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.movie_fragment, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                updatePoster();
                break;
        }
        return true;
    }

    public void updatePoster(){
        FetchMovieInfoTask fetchMovieInfoTask= new FetchMovieInfoTask();
        SharedPreferences spr = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String path = spr.getString(getString(R.string.pref_general_key), getString(R.string.pref_general_default));
        fetchMovieInfoTask.execute(path);

    }

    @Override
    public void onStart(){
        super.onStart();
        updatePoster();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.poster_view);

        updatePoster();

        return rootView;
    }

    public class FetchMovieInfoTask extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchMovieInfoTask.class.getSimpleName();
        public String[] getMovieInfoFromJson(String movieInfoJsonStr) throws Exception{
            ArrayList<String[]> movieInfoResultArray = new ArrayList<>();

            final String MI_RESULT="results";
            final String MI_ID="id";
            final String MI_POSTER_PATH = "poster_path";
            final String MI_ORIGINAL_TITLE="original_title";
            final String MI_SYNOPSIS="overview";
            final String MI_USER_RATING="vote_average";
            final String MI_RELEASE_DATE="release_date";
            final String POSTER_PATH="http://image.tmdb.org/t/p/w185";


            JSONObject movieInfoObject=new JSONObject(movieInfoJsonStr);
            JSONArray movieInfoJsonArray=movieInfoObject.getJSONArray(MI_RESULT);

            String[] posterArray = new String[movieInfoJsonArray.length()];
            String[] titleArray = new String[movieInfoJsonArray.length()];
            String[] overviewArray= new String[movieInfoJsonArray.length()];
            String[] ratingArray=new String[movieInfoJsonArray.length()];
            String[] releaseArray=new String[movieInfoJsonArray.length()];

            for(int i=0; i<movieInfoJsonArray.length();i++){

//                String id, poster_path, original_title, synopsis, user_rating, release_date;

                JSONObject movieInfo = movieInfoJsonArray.getJSONObject(i);

                posterArray[i]=POSTER_PATH+movieInfo.getString(MI_POSTER_PATH);
                titleArray[i]=movieInfo.getString(MI_ORIGINAL_TITLE);
                overviewArray[i]=movieInfo.getString(MI_SYNOPSIS);
                ratingArray[i]=movieInfo.getString(MI_USER_RATING);
                releaseArray[i]=movieInfo.getString(MI_RELEASE_DATE);

                movieInfoResultArray.add(posterArray);
                movieInfoResultArray.add(titleArray);
                movieInfoResultArray.add(overviewArray);
                movieInfoResultArray.add(ratingArray);
                movieInfoResultArray.add(releaseArray);


            }

            return posterArray;
        }
        @Override
        protected String[] doInBackground(String... params) {
//            if(params.length==0){
//                return null;
//            }
            HttpURLConnection httpURLConnection=null;
            BufferedReader reader=null;
            String movieInfoJsonStr=null;

            try {
                final String BASE_URL = "http://api.themoviedb.org/3/movie";
                final String PREF_PATH = params[0];
                final String APIKEY_PARAM="api_key";

                Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(PREF_PATH)
                        .appendQueryParameter(APIKEY_PARAM,BuildConfig.MOVIE_API_KEY)
                        .build();

                Log.v("BUILT URL", buildUri.toString());

                URL url = new URL(buildUri.toString());
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                if((line=reader.readLine())!=null){
                    buffer.append(line).append("\n");
                }

                if(buffer.length()==0){
                    return null;
                }

                movieInfoJsonStr=buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "error", e);

            }finally{
                if(httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "error closing stream",e);
                    }
                }

            }


            try {
                    return getMovieInfoFromJson(movieInfoJsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            return null;
        }
        @Override
        protected void onPostExecute(String[] posterPath) {
            mPosterAdapter=new ImageListAdapter(
                    getActivity(),
                    posterPath
                    );
            gridView.setAdapter(mPosterAdapter);
        }
        }

    }





