package com.example.orientation.Splash;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.orientation.Dbhelper.SchdDB;
import com.example.orientation.model.Event;
import com.example.orientation.model.Schedule;

import java.util.ArrayList;

public class SetRemainder {
    Context context;
    ArrayList<Long> calendars;
    ArrayList<String> eventName, stime, venue;

    public SetRemainder(Context context) {
        this.context = context;
        calendars = new ArrayList<>();
        eventName = new ArrayList<>();
        stime = new ArrayList<>();
        venue = new ArrayList<>();
        if (isMyServiceRunning(Myservice.class)) {
            context.stopService(new Intent(context, Myservice.class));
        }
        remainders();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private String[] getdate(Schedule schedule) {
        String date = schedule.getDate();
        String[] dmy = date.split(" ");
        return dmy;
    }

    private Calendar setdate(String time, String[] dmy) {
        int year = Integer.parseInt(dmy[2]);
        String month = dmy[1];
        Calendar ca = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("MMMMM").parse(month);
            ca.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int date = Integer.parseInt(dmy[0]);
        String[] hrmin = time.split(":");
        int hour = Integer.parseInt(hrmin[0]);
        int min = Integer.parseInt(hrmin[1]);
        Calendar calendar = null;
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        calendar.set(year, ca.get(Calendar.MONTH), date, hour - 1, min, 0);
        return calendar;
    }

    public void remainders() {
        SchdDB db = new SchdDB(context);
        ArrayList<Schedule> schedules = new ArrayList<>(db.showAllData());
        int count = -1;
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            String[] dmy = getdate(schedule);
            for (int j = 0; j < schedule.getEvents().size(); j++) {
                Event event = schedule.getEvents().get(j);
                String time = event.getStime();
                Calendar cal = setdate(time, dmy);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                calendar.setTimeInMillis(System.currentTimeMillis());
                cal.getTime();
                calendar.getTime();
                if (cal.compareTo(calendar) >= 0) {
                    count++;
                    calendars.add(count, cal.getTimeInMillis());
                    eventName.add(count, event.getName());
                    stime.add(count, event.getStime());
                    venue.add(count, event.getLocation());
                } else if (calendar.getTimeInMillis() - cal.getTimeInMillis() < 3600 * 1000) {
                    count++;
                    calendars.add(count, calendar.getTimeInMillis());
                    eventName.add(count, event.getName());
                    stime.add(count, event.getStime());
                    venue.add(count, event.getLocation());
                }
            }
        }
        if (calendars.size() != 0) {
            Intent intent = new Intent(context, Myservice.class);
            Bundle args = new Bundle();
            args.putSerializable("cal", (Serializable) calendars);
            args.putSerializable("event", (Serializable) eventName);
            args.putSerializable("stime", (Serializable) stime);
            args.putSerializable("venue", (Serializable) venue);
            intent.putExtra("BUNDLE", args);
            ContextCompat.startForegroundService(context, intent);
        }
    }
}
