package com.example.orientation.Dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.orientation.model.DepartTable.*;
import com.example.orientation.model.FoodTable;

public class DepartDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "depart.db";
    public static final int DATABASE_VERSION = 1;

    public DepartDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_DEPARTLIST_TABLE = "CREATE TABLE " +
                DepartEntry.TABLE_NAME + " (" +
                DepartEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DepartEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                DepartEntry.COLUMN_LURL + " TEXT NOT NULL, " +
                DepartEntry.COLUMN_DESC + " TEXT NOT NULL, " +
                DepartEntry.COLUMN_IURL + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_DEPARTLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DepartEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor showData() {
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from " + DepartEntry.TABLE_NAME + " Order by " + DepartEntry.COLUMN_NAME;
        Cursor cursor = dB.rawQuery(query, null);
        return cursor;
    }

    public Cursor showDatabyName(String name) {
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from " + DepartEntry.TABLE_NAME + " where " + DepartEntry.COLUMN_NAME + " LIKE " + "'%" + name + "%'";
        Cursor cursor = dB.rawQuery(query, null);
        return cursor;
    }
}