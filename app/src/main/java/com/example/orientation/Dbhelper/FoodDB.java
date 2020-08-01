package com.example.orientation.Dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.orientation.model.FoodTable.*;

public class FoodDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "food.db";
    public static final int DATABASE_VERSION = 1;

    public FoodDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FOODLIST_TABLE = "CREATE TABLE " +
                FoodEntry.TABLE_NAME + " (" +
                FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FoodEntry.COLUMN_LURL + " TEXT NOT NULL, " +
                FoodEntry.COLUMN_IURL + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_FOODLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FoodEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor showData() {
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from " + FoodEntry.TABLE_NAME + " Order by " + FoodEntry.COLUMN_NAME;
        Cursor cursor = dB.rawQuery(query, null);
        return cursor;
    }
}
