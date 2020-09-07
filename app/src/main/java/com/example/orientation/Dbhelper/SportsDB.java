package com.example.orientation.Dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.orientation.model.FoodTable;
import com.example.orientation.model.SportsTable.*;

public class SportsDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sports.db";
    public static final int DATABASE_VERSION = 1;

    public SportsDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SPORTLIST_TABLE = "CREATE TABLE " +
                SportsEntry.TABLE_NAME + " (" +
                SportsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SportsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SportsEntry.COLUMN_LURL + " TEXT NOT NULL, " +
                SportsEntry.COLUMN_DESC + " TEXT NOT NULL, " +
                SportsEntry.COLUMN_LAT + " TEXT NOT NULL, " +
                SportsEntry.COLUMN_LONG + " TEXT NOT NULL, " +
                SportsEntry.COLUMN_IURL + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_SPORTLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SportsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor showData() {
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from " + SportsEntry.TABLE_NAME + " Order by " + SportsEntry.COLUMN_NAME;
        Cursor cursor = dB.rawQuery(query, null);
        return cursor;
    }
}