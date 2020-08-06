package com.example.orientation.Dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.orientation.model.SchdTable;
import com.example.orientation.model.SchdTable.*;

public class SchdDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "schd.db";
    public static final int DATABASE_VERSION = 1;
    public SchdDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SCHDLIST_TABLE = "CREATE TABLE " +
                SchdEntry.TABLE_NAME + " (" +
                SchdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SchdEntry.COLUMN_EVENT + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_DESC + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_LOC + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_LURL + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_IMG + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_DATE + " TEXT NOT NULL"+
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_SCHDLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +SchdEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public Cursor showData(String date){
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from "+SchdEntry.TABLE_NAME+" where "+SchdEntry.COLUMN_DATE+" LIKE "+"'%"+date+"%'";
        Cursor cursor = dB.rawQuery(query,null);
        return cursor;
    }

}
