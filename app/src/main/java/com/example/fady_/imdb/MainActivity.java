package com.example.fady_.imdb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.fady_.imdb.Data.Contract;
import com.example.fady_.imdb.Data.DbHelper;

import com.example.fady_.imdb.Data.Contract.MovieEntry;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public boolean fav=false;
    MovieAdapter movieAdapter;
    Movie[] result;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences type = getSharedPreferences("my Bool", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = type.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if (findViewById(R.id.movie_detail_container) != null)
       {
           edit.putBoolean("twoPane",true);
           edit.commit();

             }else {
              edit.putBoolean("twoPane",false);
              edit.commit();
           }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                ArrayList<Movie> movies = new ArrayList<>() ;
                View rootView= inflater.inflate(R.layout.fragment_main, container, false);
                movieAdapter = new MovieAdapter(this , movies);
                GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
                gridView.setAdapter(movieAdapter);}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences type = getSharedPreferences("my", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = type.edit();
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else {
            fav=true;
            edit.putBoolean("fav",fav);
            edit.commit();

            updateFavourite(fav);

            return super.onOptionsItemSelected(item);
        }
    }


    void updateFavourite(boolean x){
        DbHelper save = new DbHelper(this);
        final SQLiteDatabase read = save.getReadableDatabase();
        Cursor cursor = read.query(Contract.MovieEntry.TABLE_NAME,new String[] {MovieEntry.COLUMN_MOVIE_ID,MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_REALESEDATE,MovieEntry.COLUMN_OVERVIEW,
                MovieEntry.COLUMN_POSTER,MovieEntry.COLUMN_RATE},null,null,null,null,null);


        int counter=0;
        if (cursor != null && cursor.getCount() > 0)
        {

            result = new Movie[cursor.getCount()];
            if(cursor.moveToFirst()) {
                Intent check = new Intent(this, MainActivity.class);
                check.putExtra("test", x);
                do {
                    result[counter] = new Movie(cursor.getString(1), cursor.getString(4), cursor.getString(3), cursor.getString(5), cursor.getString(2), cursor.getString(0));

                    check.putExtra(("id" + String.valueOf(counter)), cursor.getString(0));
                    check.putExtra("title" + String.valueOf(counter), cursor.getString(1));
                    check.putExtra("realsedate" + String.valueOf(counter), cursor.getString(2));
                    check.putExtra("overview" + String.valueOf(counter), cursor.getString(3));
                    check.putExtra("poster" + String.valueOf(counter), cursor.getString(4));
                    check.putExtra("rate" + String.valueOf(counter), cursor.getString(5));
                    counter++;
                } while (cursor.moveToNext());

                check.putExtra("counter", counter);
              startActivity(check);
            }}}






}
