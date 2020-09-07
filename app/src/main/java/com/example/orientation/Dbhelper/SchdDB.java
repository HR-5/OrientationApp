package com.example.orientation.Dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.orientation.model.Event;
import com.example.orientation.model.SchdTable;
import com.example.orientation.model.SchdTable.*;
import com.example.orientation.model.Schedule;

import java.util.ArrayList;

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
                SchdEntry.COLUMN_LAT + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_LONG + " TEXT NOT NULL, " +
                SchdEntry.COLUMN_DATE + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_SCHDLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SchdEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor showData(String date) {
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from " + SchdEntry.TABLE_NAME + " where " + SchdEntry.COLUMN_DATE + " LIKE " + "'%" + date + "%'";
        Cursor cursor = dB.rawQuery(query, null);
        return cursor;
    }

    public ArrayList<String> getDate() {
        ArrayList<String> dates = new ArrayList<>();
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select DISTINCT " + SchdEntry.COLUMN_DATE + " from " + SchdEntry.TABLE_NAME;
        Cursor cursor = dB.rawQuery(query, null);
        cursor.moveToFirst();
        int pos = 0;
        while (cursor.moveToPosition(pos)) {
            String date = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_DATE));
            dates.add(pos, date);
            pos++;
        }
        return dates;
    }

    public ArrayList<Schedule> showAllData() {
        ArrayList<Schedule> schedules = new ArrayList<>();
        ArrayList<Event> eventset = new ArrayList<>();
        SQLiteDatabase dB = this.getReadableDatabase();
        String query = "Select * from " + SchdEntry.TABLE_NAME + " Order by " + SchdEntry.COLUMN_DATE;;
        Cursor cursor = dB.rawQuery(query, null);
        cursor.moveToFirst();
        int pos = 0;
        int flag = 0;
        String da = null;
        while (cursor.moveToPosition(pos)) {
            String name = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_EVENT));;
            String stime = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_TIME)).split("-")[0];
            String etime = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_TIME)).split("-")[1];
            String description = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_DESC));
            String location = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_LOC));
            String locurl = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_LURL));
            String imgname = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_IMG));
            String lat = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_LAT));
            String longi = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_LONG));
            Event oevent = new Event(name,stime,etime,description,location,locurl,imgname,lat,longi);
            String date = cursor.getString(cursor.getColumnIndex(SchdEntry.COLUMN_DATE));
            if(pos == 0) {
                eventset.add(oevent);
                da = date;
                flag = 0;
            }
            else if (da.equals(date)){
                eventset.add(oevent);
                da = date;
            }
            else {
                    schedules.add(flag,new Schedule(da,eventset));
                    eventset = new ArrayList<>();
                    eventset.add(oevent);
                    da = date;
                    flag++;
            }
            pos++;
        }
        schedules.add(flag,new Schedule(da,eventset));
        return schedules;
    }

}
