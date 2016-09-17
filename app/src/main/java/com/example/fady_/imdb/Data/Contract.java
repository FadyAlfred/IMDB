package com.example.fady_.imdb.Data;

import android.provider.BaseColumns;

/**
 * Created by Fady on 9/10/2016.
 */
public class Contract {
    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_REALESEDATE = "reasledate";
    }

    public static final class TrailerEntry implements BaseColumns {
        public static final String TABLE_NAME = "trailer";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
    }

    public static final class ReviewEntry implements BaseColumns {
        public static final String TABLE_NAME = "review";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
    }
}
