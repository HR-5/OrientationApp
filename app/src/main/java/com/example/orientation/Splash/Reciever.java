package com.example.orientation.Splash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.orientation.R;

import static com.example.orientation.Splash.App.CHANNEL_ID;

public class Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String event = intent.getStringExtra("event");
        String des = "Venue: " + intent.getStringExtra("venue") + " | Time: " + intent.getStringExtra("time");
        int count = intent.getIntExtra("count",0);
        int total = intent.getIntExtra("total",0);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle(event)
                .setContentText(des)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(1, notification.build());
        Intent serviceIntent = new Intent(context, Myservice.class);
        if(count == total-1)
            context.stopService(serviceIntent);
    }
}
