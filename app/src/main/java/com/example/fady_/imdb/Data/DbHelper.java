package com.example.fady_.imdb.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fady_.imdb.Data.Contract.MovieEntry;
import com.example.fady_.imdb.Data.Contract.TrailerEntry;
import com.example.fady_.imdb.Data.Contract.ReviewEntry;

import java.net.FileNameMap;

/**
 * Created by Fady on 9/10/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME ="Movies.db";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + "(" +
                                               MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL ,"+
                                               MovieEntry.COLUMN_TITLE + " TEXT NOT NULL ,"+
                                               MovieEntry.COLUMN_POSTER + " TEXT NOT NULL ,"+
                                               MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL ,"+
                                               MovieEntry.COLUMN_RATE + " TEXT NOT NULL ,"+
                                               MovieEntry.COLUMN_REALESEDATE + " TEXT NOT NULL "+");";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + "(" +
                                                TrailerEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL ," +
                                                TrailerEntry.COLUMN_KEY +" TEXT NOT NULL ," +
                                                TrailerEntry.COLUMN_NAME + " TEXT NOT NULL "+");";

        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + "(" +
                                                ReviewEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL ," +
                                                ReviewEntry.COLUMN_AUTHOR +" TEXT NOT NULL ," +
                                                ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL "+");";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_TRAILER_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+ReviewEntry.TABLE_NAME);
        onCreate(db);

    }
}
