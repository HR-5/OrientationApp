package com.example.orientation.Splash;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.orientation.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.orientation.Splash.App.CHANNEL_ID;

public class Myservice extends Service {
    ArrayList<Long> calendars;
    ArrayList<String> eventName, stime, venue;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        calendars = new ArrayList<>();
        eventName = new ArrayList<>();
        stime = new ArrayList<>();
        venue = new ArrayList<>();
        Bundle args = intent.getBundleExtra("BUNDLE");
        calendars = (ArrayList<Long>) args.getSerializable("cal");
        eventName = (ArrayList<String>) args.getSerializable("event");
        stime = (ArrayList<String>) args.getSerializable("stime");
        venue = (ArrayList<String>) args.getSerializable("venue");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Orientation Schedule")
                .setContentText("Schedules will be shown here")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        notifi();
        //do heavy work on a background thread
//        stopSelf();
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void notifi() {
        for (int i = 0; i < calendars.size(); i++) {
            long cal = calendars.get(i);
            String event = eventName.get(i);
            String time = stime.get(i);
            String ven = venue.get(i);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, Reciever.class);
            intent.putExtra("event",event);
            intent.putExtra("time",time);
            intent.putExtra("venue",ven);
            intent.putExtra("count",i);
            intent.putExtra("total",calendars.size());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal, pendingIntent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
