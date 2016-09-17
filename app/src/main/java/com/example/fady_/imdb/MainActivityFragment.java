package com.example.fady_.imdb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

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


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
      MovieAdapter movieAdapter;


    Movie[] result;

    int counter=1;
    ArrayList<Movie> movies = new ArrayList<>() ;

    private void updateMovie (){
        MovieTask newOne =  new MovieTask();
        newOne.execute();
    }



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Intent fav = getActivity().getIntent();
        boolean test = fav.getBooleanExtra("test",false);
        int favCounter = fav.getIntExtra("counter",0);


        Movie [] favList = new  Movie[favCounter] ;


        View rootView= inflater.inflate(R.layout.fragment_main, container, false);
        movieAdapter = new MovieAdapter(getActivity() , movies);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener(){

             public void onScrollStateChanged(AbsListView view, int scrollState) {}

             public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){

                }
            }
        });



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SharedPreferences type = getActivity().getSharedPreferences("my Bool", Context.MODE_PRIVATE);
                final boolean twoPane = type.getBoolean("twoPane",true);
                if(twoPane==false){
                String movieTitle = movieAdapter.getItem(position).getTitle();
                String rate = movieAdapter.getItem(position).getRate();
                String poster = movieAdapter.getItem(position).getMoviePoster();
                String overView = movieAdapter.getItem(position).getOverView();
                String releaseDate = movieAdapter.getItem(position).getReleaseDate();
                String id = movieAdapter.getItem(position).getId();
                Intent intent = new Intent(getActivity() , DetailActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT,movieTitle);
                        intent.putExtra("Poster",poster);
                        intent.putExtra("Rate",rate);
                        intent.putExtra("OverView",overView);
                        intent.putExtra("ReleaseDate",releaseDate);
                        intent.putExtra("ID",id);


                startActivity(intent);
                }else {
                    String movieTitle = movieAdapter.getItem(position).getTitle();
                    String rate = movieAdapter.getItem(position).getRate();
                    String poster = movieAdapter.getItem(position).getMoviePoster();
                    String overView = movieAdapter.getItem(position).getOverView();
                    String releaseDate = movieAdapter.getItem(position).getReleaseDate();
                    String id = movieAdapter.getItem(position).getId();

                    Bundle args = new Bundle();

                    args.putString("title",movieTitle);
                    args.putString("Poster",poster);
                    args.putString("Rate",rate);
                    args.putString("OverView",overView);
                    args.putString("ReleaseDate",releaseDate);
                    args.putString("ID",id);

                    DetailActivityFragment fragment = new DetailActivityFragment();
                    fragment.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container,fragment).commit();
                }}





        });

        gridView.setAdapter(movieAdapter);

        if(test==true){
                int x=0 ;
                MovieAdapter movieAdapter2 = null;
            if (fav != null) {
                do{
                favList [x] = new Movie(fav.getStringExtra("title"+String.valueOf(x)), fav.getStringExtra("poster"+String.valueOf(x)), fav.getStringExtra("overview"+String.valueOf(x)), fav.getStringExtra("rate"+String.valueOf(x)), fav.getStringExtra("realsedate"+String.valueOf(x)), fav.getStringExtra("id"+String.valueOf(x)));
                x++; } while (x<favCounter);
                ArrayList<Movie> mf = new ArrayList<Movie>();
                if(favList!=null) {
                    movieAdapter2 = new MovieAdapter(getActivity() , mf);
                    GridView grid = (GridView) rootView.findViewById(R.id.movie_grid);
                    grid.setOnScrollListener(new AbsListView.OnScrollListener(){

                        public void onScrollStateChanged(AbsListView view, int scrollState) {}

                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
                        {
                            if(firstVisibleItem + visibleItemCount >= totalItemCount){

                            }
                        }
                    });
                    final MovieAdapter finalMovieAdapter = movieAdapter2;
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SharedPreferences type = getActivity().getSharedPreferences("my Bool", Context.MODE_PRIVATE);
                            final boolean twoPane = type.getBoolean("twoPane",true);
                            if(twoPane==false){
                            String movieTitle = finalMovieAdapter.getItem(position).getTitle();
                            String rate = finalMovieAdapter.getItem(position).getRate();
                            String poster = finalMovieAdapter.getItem(position).getMoviePoster();
                            String overView = finalMovieAdapter.getItem(position).getOverView();
                            String releaseDate = finalMovieAdapter.getItem(position).getReleaseDate();
                            String idf = finalMovieAdapter.getItem(position).getId();
                            Intent intent = new Intent(getActivity() , DetailActivity.class);
                            intent.putExtra(Intent.EXTRA_TEXT,movieTitle);
                            intent.putExtra("Poster",poster);
                            intent.putExtra("Rate",rate);
                            intent.putExtra("OverView",overView);
                            intent.putExtra("ReleaseDate",releaseDate);
                            intent.putExtra("ID",idf);


                            startActivity(intent);

                        }else { String movieTitle = finalMovieAdapter.getItem(position).getTitle();
                                String rate = finalMovieAdapter.getItem(position).getRate();
                                String poster = finalMovieAdapter.getItem(position).getMoviePoster();
                                String overView = finalMovieAdapter.getItem(position).getOverView();
                                String releaseDate = finalMovieAdapter.getItem(position).getReleaseDate();
                                String idf = finalMovieAdapter.getItem(position).getId();

                                Bundle args = new Bundle();

                                args.putString("title",movieTitle);
                                args.putString("Poster",poster);
                                args.putString("Rate",rate);
                                args.putString("OverView",overView);
                                args.putString("ReleaseDate",releaseDate);
                                args.putString("ID",idf);

                                DetailActivityFragment fragment = new DetailActivityFragment();
                                fragment.setArguments(args);

                                getActivity().getSupportFragmentManager().beginTransaction()
                                             .replace(R.id.movie_detail_container,fragment,null).commit();
                                 }
                        }
                    });
                    grid.setAdapter(movieAdapter2);
                }
            }
            SharedPreferences type = getActivity().getSharedPreferences("my Bool", Context.MODE_PRIVATE);
            final boolean twoPane = type.getBoolean("twoPane",true);
            movieAdapter2.clear();
            for(Movie singleMoive : favList)
            { movieAdapter2.add(singleMoive);}
            if (twoPane){
                String movieTitle = movieAdapter2.getItem(0).getTitle();
                String rate = movieAdapter2.getItem(0).getRate();
                String poster = movieAdapter2.getItem(0).getMoviePoster();
                String overView = movieAdapter2.getItem(0).getOverView();
                String releaseDate = movieAdapter2.getItem(0).getReleaseDate();
                String idf = movieAdapter2.getItem(0).getId();

                Bundle args = new Bundle();

                args.putString("title",movieTitle);
                args.putString("Poster",poster);
                args.putString("Rate",rate);
                args.putString("OverView",overView);
                args.putString("ReleaseDate",releaseDate);
                args.putString("ID",idf);

                DetailActivityFragment fragment = new DetailActivityFragment();
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container,fragment,null).commit();

            }
        }

        updateMovie();
        return rootView;
    }


  public class MovieTask extends AsyncTask<Integer, Void, Movie[]> {
      private  final String LOG_TAG = MovieTask.class.getSimpleName();

      private Movie[] getMovieDataFromJson(String movieJsonStr) throws JSONException {

          final String RESULT = "results";
          final String MOVIE_TITLE ="original_title";
          final String MOVIE_POSTER = "poster_path";
          final String MOVIE_OVERVIEW = "overview";
          final String MOVIE_RATE = "vote_average";
          final String MOVIE_RELEASEDATE = "release_date";
          final String MOVIE_ID = "id";

          JSONObject dataFromJson = new JSONObject(movieJsonStr);
          JSONArray movieArray = dataFromJson.getJSONArray(RESULT);

          Movie[] result = new Movie[movieArray.length()];

          for (int i=0;i < movieArray.length();i++){

              String name;
              String poster;
              String overView;
              String rate;
              String resleaseDate;
              String id;

              JSONObject singleMovie =movieArray.getJSONObject(i);
              name= singleMovie.getString(MOVIE_TITLE);
              poster = singleMovie.getString(MOVIE_POSTER);
              overView = singleMovie.getString(MOVIE_OVERVIEW);
              rate = singleMovie.getString(MOVIE_RATE);
              resleaseDate = singleMovie.getString(MOVIE_RELEASEDATE);
              id=singleMovie.getString(MOVIE_ID);

              result [i] = new Movie(name,"http://image.tmdb.org/t/p/w185"+poster,overView,rate+"/10",resleaseDate,id);

          }


          return result;
      }


      @Override
      protected Movie[] doInBackground(Integer... params) {


              HttpURLConnection urlConnection = null;
              BufferedReader reader = null;
              String movieJsonStr = null;

              try {
                  SharedPreferences sortType = PreferenceManager.getDefaultSharedPreferences(getActivity());
                  String type = sortType.getString(getString(R.string.pref_type_key), getString(R.string.pref_popular));


                  if (type.equals(getString(R.string.pref_popular))) {
                      URL url = new URL("http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=8a0100c85250306e20b957e0078de716");
                      urlConnection = (HttpURLConnection) url.openConnection();
                      urlConnection.setRequestMethod("GET");
                      urlConnection.connect();
                  } else {
                      URL url = new URL("http://api.themoviedb.org/3/movie/popular?page=" + params + "&api_key=8a0100c85250306e20b957e0078de716");
                      urlConnection = (HttpURLConnection) url.openConnection();
                      urlConnection.setRequestMethod("GET");
                      urlConnection.connect();
                  }





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
                  movieJsonStr = buffer.toString();
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
              return getMovieDataFromJson(movieJsonStr);
          }catch (JSONException e){
              Log.e(LOG_TAG,e.getMessage(),e);
              e.printStackTrace();
          }
          return null;
      }
     protected  void onPostExecute(Movie[] result){
          if(result!=null) {

                  movieAdapter.clear();
                  for(Movie singleMoive : result)
                  { movieAdapter.add(singleMoive);}
              SharedPreferences type = getActivity().getSharedPreferences("my Bool", Context.MODE_PRIVATE);
              SharedPreferences favo = getActivity().getSharedPreferences("my", Context.MODE_PRIVATE);
              final boolean twoPane = type.getBoolean("twoPane",true);
              final boolean fav = favo.getBoolean("fav",false);
              if(twoPane && fav==false){
                  String movieTitle = movieAdapter.getItem(0).getTitle();
                  String rate = movieAdapter.getItem(0).getRate();
                  String poster = movieAdapter.getItem(0).getMoviePoster();
                  String overView = movieAdapter.getItem(0).getOverView();
                  String releaseDate = movieAdapter.getItem(0).getReleaseDate();
                  String id = movieAdapter.getItem(0).getId();

                  Bundle args = new Bundle();

                  args.putString("title",movieTitle);
                  args.putString("Poster",poster);
                  args.putString("Rate",rate);
                  args.putString("OverView",overView);
                  args.putString("ReleaseDate",releaseDate);
                  args.putString("ID",id);

                  DetailActivityFragment fragment = new DetailActivityFragment();
                  fragment.setArguments(args);

                  getActivity().getSupportFragmentManager().beginTransaction()
                          .replace(R.id.movie_detail_container,fragment).commit();
          }
          }

      }}   @Override
    public void onStart() {
        super.onStart();
        updateMovie();

    }}








