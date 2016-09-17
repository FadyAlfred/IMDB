package com.example.fady_.imdb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by FADY_ on 8/6/2016.
 */
public class Movie implements Parcelable {
    private String title;
    private String moviePoster;
    private String overView;
    private String rate;
    private String releaseDate;
    private String id;

    protected Movie(Parcel in) {
        title = in.readString();
        moviePoster = in.readString();
        overView = in.readString();
        rate = in.readString();
        releaseDate = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public ArrayList<Trailer> getTrailersList() {
        return trailersList;
    }

    public void setTrailersList(ArrayList<Trailer> trailersList) {
        this.trailersList = trailersList;
    }

    private ArrayList<Trailer> trailersList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie(String oTitle, String oMoviePoster, String oOverView, String oRate, String oReleaseDate,String oID) {
        this.title = oTitle;
        this.moviePoster = oMoviePoster;
        this.overView = oOverView;
        this.rate = oRate;
        this.releaseDate = oReleaseDate;
        this.id=oID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(moviePoster);
        dest.writeString(overView);
        dest.writeString(rate);
        dest.writeString(releaseDate);
        dest.writeString(id);
    }
}
class Trailer {
    private String key;
    private String name;

    public Trailer(String oKey, String oName) {
        key=oKey;
        name=oName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
class Review {

    private String author;
    private String review;

    public Review (String oAuthor, String oReview){
        author = oAuthor;
        review = oReview;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}

