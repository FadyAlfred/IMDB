package com.example.fady_.imdb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fady_.imdb.Data.DbHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import com.example.fady_.imdb.Data.Contract.MovieEntry;
import com.example.fady_.imdb.Data.Contract.TrailerEntry;
import com.example.fady_.imdb.Data.Contract.ReviewEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    ListView list;
    ArrayList<Trailer> trail = new ArrayList<>();
    Trailer[] dbtrail ;
    Review[] dbreview ;
    ArrayList<Review> review = new ArrayList<>();
    String id = null;
    String Title;
    String Rate ;
    String poster ;
    String overView ;
    String releaseDate;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        Bundle arguments = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        final LinearLayout linerView = (LinearLayout) rootView.findViewById(R.id.linerVertical);

        trailerAdapter = new TrailerAdapter(getActivity() , trail);
        list= (ListView) rootView.findViewById(R.id.trailer_list);
        list.setAdapter(trailerAdapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = trailerAdapter.getItem(position).getKey();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+key)));
            }
        });


        reviewAdapter = new ReviewAdapter(getActivity(),review);
        ListView reviewss = (ListView) rootView.findViewById(R.id.review_list);
        reviewss.setAdapter(reviewAdapter);
        reviewss.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        SharedPreferences type = getActivity().getSharedPreferences("my Bool", Context.MODE_PRIVATE);
        final boolean twoPane = type.getBoolean("twoPane",true);

        DbHelper save = new DbHelper(getContext());
        final SQLiteDatabase db = save.getWritableDatabase();
        final SQLiteDatabase read = save.getReadableDatabase();


        if(twoPane == false){
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            Title = intent.getStringExtra(Intent.EXTRA_TEXT);
            Rate = intent.getStringExtra("Rate");
            poster =  intent.getStringExtra("Poster");
            overView =  intent.getStringExtra("OverView");
            releaseDate =  intent.getStringExtra("ReleaseDate");
            id = intent.getStringExtra("ID");



            TextView title = (TextView) linerView.findViewById(R.id.movie_title);
                     title.setText(Title);
                     title.setBackgroundColor(Color.rgb(0,150,136));
                     title.setTextColor(Color.WHITE);

            ((TextView) rootView.findViewById(R.id.movie_release_date))
                    .setText(""+releaseDate.charAt(0)+releaseDate.charAt(1)+releaseDate.charAt(2)+releaseDate.charAt(3));

            TextView movieRate= (TextView) rootView.findViewById(R.id.movie_rate);
                     movieRate.setTextColor(Color.BLACK);
                     movieRate.setText(Rate);

            ((TextView) rootView.findViewById(R.id.movie_overview))
                    .setText(overView);

            ImageView posterView = (ImageView) rootView.findViewById(R.id.movie_poster_detail);
            Picasso.with(getContext()).load(poster).into(posterView);


            String [] titles = {MovieEntry.COLUMN_MOVIE_ID};
            String selection = MovieEntry.COLUMN_MOVIE_ID + " = ?";
            String[] selectionArgs = { id };
            final Cursor name = read.query(MovieEntry.TABLE_NAME,titles,selection,selectionArgs,null,null,null);

            final Button mark = (Button) rootView.findViewById(R.id.mark_button);
                   mark.setBackgroundColor(Color.rgb(0,202,182));

            if(name.getCount() == 0){mark.setText("MARK AS"+"\n"+"FAVORITE");
                                     mark.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mark.setText("Remove");
                                                ContentValues values = new ContentValues();
                                                ContentValues trailervalues = new ContentValues();
                                                ContentValues reviewvalues = new ContentValues();
                                                values.put(MovieEntry.COLUMN_TITLE,Title);
                                                values.put(MovieEntry.COLUMN_MOVIE_ID,id);
                                                values.put(MovieEntry.COLUMN_OVERVIEW,overView);
                                                values.put(MovieEntry.COLUMN_POSTER,poster);
                                                values.put(MovieEntry.COLUMN_RATE,Rate);
                                                values.put(MovieEntry.COLUMN_REALESEDATE,releaseDate);
                                                long ch  =  db.insert(MovieEntry.TABLE_NAME,null,values);
                                                Log.e("save", String.valueOf(ch));


                                                if (name.getCount()!=0){
                                                mark.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        mark.setText("MARK AS"+"\n"+"FAVORITE");
                                                        int delete = db.delete(MovieEntry.TABLE_NAME,"movie_id =?",new String[]{id});
                                                        Log.e("dlete", String.valueOf(delete));
                                                    }
                                                });}

                                            }
                                        });}

            else {mark.setText("Remove");
                  mark.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mark.setText("MARK AS"+"\n"+"FAVORITE");
                          int delete = db.delete(MovieEntry.TABLE_NAME,"movie_id =?",new String[]{id});
                          int deletetrailer = db.delete(TrailerEntry.TABLE_NAME,"id =?",new String[]{id});
                          int deletereview = db.delete(ReviewEntry.TABLE_NAME,"id =?",new String[]{id});
                      }
                  });}


        }}else {
            if(arguments != null){
                Title = arguments.getString("title");
                Rate= arguments.getString("Rate");
                poster = arguments.getString("Poster");
                overView = arguments.getString("OverView");
                releaseDate = arguments.getString("ReleaseDate");
                id = arguments.getString("ID");



            TextView title = (TextView) linerView.findViewById(R.id.movie_title);
            title.setText(Title);
            title.setBackgroundColor(Color.rgb(0,150,136));
            title.setTextColor(Color.WHITE);

            ((TextView) rootView.findViewById(R.id.movie_release_date))
                    .setText(""+releaseDate.charAt(0)+releaseDate.charAt(1)+releaseDate.charAt(2)+releaseDate.charAt(3));

            TextView movieRate= (TextView) rootView.findViewById(R.id.movie_rate);
            movieRate.setTextColor(Color.BLACK);
            movieRate.setText(Rate);

            ((TextView) rootView.findViewById(R.id.movie_overview))
                    .setText(overView);

            ImageView posterView = (ImageView) rootView.findViewById(R.id.movie_poster_detail);
            Picasso.with(getContext()).load(poster).into(posterView);

            String [] titles = {MovieEntry.COLUMN_MOVIE_ID};
            String selection = MovieEntry.COLUMN_MOVIE_ID + " = ?";
            String[] selectionArgs = { id };
            final Cursor name = read.query(MovieEntry.TABLE_NAME,titles,selection,selectionArgs,null,null,null);

            final Button mark = (Button) rootView.findViewById(R.id.mark_button);
            mark.setBackgroundColor(Color.rgb(0,202,182));

            if(name.getCount() == 0){mark.setText("MARK AS"+"\n"+"FAVORITE");
                mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mark.setText("Remove");
                        ContentValues values = new ContentValues();
                        ContentValues trailervalues = new ContentValues();
                        ContentValues reviewvalues = new ContentValues();
                        values.put(MovieEntry.COLUMN_TITLE,Title);
                        values.put(MovieEntry.COLUMN_MOVIE_ID,id);
                        values.put(MovieEntry.COLUMN_OVERVIEW,overView);
                        values.put(MovieEntry.COLUMN_POSTER,poster);
                        values.put(MovieEntry.COLUMN_RATE,Rate);
                        values.put(MovieEntry.COLUMN_REALESEDATE,releaseDate);
                        long ch  =  db.insert(MovieEntry.TABLE_NAME,null,values);
                        Log.e("save", String.valueOf(ch));



                        if (name.getCount()!=0){
                            mark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mark.setText("MARK AS"+"\n"+"FAVORITE");
                                    int delete = db.delete(MovieEntry.TABLE_NAME,"movie_id =?",new String[]{id});
                                    Log.e("dlete", String.valueOf(delete));
                                }
                            });}

                    }
                });}

            else {mark.setText("Remove");
                mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mark.setText("MARK AS"+"\n"+"FAVORITE");
                        int delete = db.delete(MovieEntry.TABLE_NAME,"movie_id =?",new String[]{id});
                        int deletetrailer = db.delete(TrailerEntry.TABLE_NAME,"id =?",new String[]{id});
                        int deletereview = db.delete(ReviewEntry.TABLE_NAME,"id =?",new String[]{id});
                        Log.e("dlete", String.valueOf(delete));
                        Log.e("dlete2", String.valueOf(deletetrailer));
                        Log.e("dlete3", String.valueOf(deletereview));
                    }
                });}


        }}

        TailerTask trailer = new TailerTask();
        trailer.execute(id);





        ReviewTask review_task = new ReviewTask();
        review_task.execute(id);

        return rootView;

    }
    public class TailerTask extends AsyncTask<String, Void, Trailer[]> {
        private  final String LOG_TAG = TailerTask.class.getSimpleName();
        public int number;



        private Trailer[] getMovieTrailerFromJson(String trailerJsonStr) throws JSONException {
            final String RESULT = "results";
            final String TRAILER_KEY ="key";
            final String TRAILER_NAME="name";


            JSONObject dataFromJson = new JSONObject(trailerJsonStr);
            JSONArray trailerArray = dataFromJson.getJSONArray(RESULT);

           number =trailerArray.length();
            Trailer[] resultTrailer = new Trailer[trailerArray.length()];


            for (int i=0;i < trailerArray.length() ;i++){

                String key;
                String name;

                JSONObject singleTrailer =trailerArray.getJSONObject(i);
                key=singleTrailer.getString(TRAILER_KEY);
                name = singleTrailer.getString(TRAILER_NAME);
                resultTrailer[i]= new Trailer(key,name);


            }


            return resultTrailer;

        }


        @Override
        protected Trailer[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String trailerJsonStr = null;


            try {
                    URL url = new URL("http://api.themoviedb.org/3/movie/"+params[0]+"/videos?api_key=8a0100c85250306e20b957e0078de716");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();




                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                    if (buffer.length() == 0) {
                        return null;
                    }
                    trailerJsonStr = buffer.toString();
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            try{
                return getMovieTrailerFromJson(trailerJsonStr);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Trailer[] resultTrailer){
            if(resultTrailer!=null) {
                if(resultTrailer!=null) {
                    trailerAdapter.clear();
                    dbtrail=resultTrailer;
                    list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,number*275));
                    for(Trailer singleTrailer : resultTrailer)
                    { trailerAdapter.add(singleTrailer);}
                }

            }

    }


}
    public class ReviewTask extends AsyncTask<String, Void, Review[]> {
        private  final String LOG_TAG = ReviewTask.class.getSimpleName();
        private Review[] getMovieTrailerFromJson(String reviewJsonStr) throws JSONException {
            final String RESULT = "results";
            final String REVIEW_AUTHOR ="author";
            final String REVIEW_CONTENT="content";

            JSONObject dataFromJson = new JSONObject(reviewJsonStr);
            JSONArray reviewArray = dataFromJson.getJSONArray(RESULT);


            Review[] resultTrailer = new Review[reviewArray.length()];

            for (int i=0;i < reviewArray.length() ;i++){

                String author;
                String content;

                JSONObject singleReview =reviewArray.getJSONObject(i);
                author = singleReview.getString(REVIEW_AUTHOR);
                content = singleReview.getString(REVIEW_CONTENT);
                resultTrailer[i]= new Review(author,content);


            }


            return resultTrailer;

        }

        @Override
        protected Review[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String reviewJsonStr = null;

            try {
                URL url = new URL("http://api.themoviedb.org/3/movie/"+params[0]+"/reviews?api_key=8a0100c85250306e20b957e0078de716");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();




                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                    if (buffer.length() == 0) {

                        return null;
                    }
                    reviewJsonStr = buffer.toString();
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            try{
                return getMovieTrailerFromJson(reviewJsonStr);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }
            return null;

        }



        protected  void onPostExecute(Review[] resultReview){
            if(resultReview!=null) {
                if(resultReview!=null) {
                    dbreview=resultReview;
                    reviewAdapter.clear();
                    for(Review singleReview : resultReview)
                    {reviewAdapter.add(singleReview);}
                }
            }

        }
    }}
