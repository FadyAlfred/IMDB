package com.example.fady_.imdb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FADY_ on 8/6/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    public ArrayList<Movie> Images;


    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0,movies);
        mContext=context;
        Images=movies;
    }

  @Override
    public Movie getItem(int position) {
        return Images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movies = getItem(position);

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_movie,parent,false);
        }
            ImageView posterView = (ImageView) convertView.findViewById(R.id.movie_poster);
            Picasso.with(getContext()).load(movies.getMoviePoster()).into(posterView);


        return convertView;

    }




}
class TrailerAdapter extends ArrayAdapter<Trailer> {

    private Context mContext;
    public ArrayList<Trailer> Images;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailer) {
        super(context,0,trailer);
        mContext=context;
        Images=trailer;
    }
    @Override
    public Trailer getItem(int position) {
        return Images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trailer trailers = getItem(position);

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item,parent,false);
        }
        TextView posterView = (TextView) convertView.findViewById(R.id.name_of_trailer);
        posterView.setText(trailers.getName());

        ImageView video= (ImageView) convertView.findViewById(R.id.movie_trailer);
                video.setImageResource(R.drawable.play);
        return convertView;

    }
}

class ReviewAdapter extends ArrayAdapter<Review>{
    private Context mContext;
    public ArrayList<Review> Images;

    public ReviewAdapter(Context context, ArrayList<Review> review) {
        super(context,0,review);
        mContext=context;
        Images=review;
    }
    @Override
    public Review getItem(int position) {
        return Images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review reviews = getItem(position);

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item,parent,false);
        }

        TextView author = (TextView) convertView.findViewById(R.id.autor);
                 author.setTextColor(Color.BLACK);
                 author.setText(reviews.getAuthor());

        ((TextView) convertView.findViewById(R.id.review_content))
                .setText(reviews.getReview());

        return convertView;

    }
}