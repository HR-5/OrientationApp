package com.example.orientation.Settings;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.example.orientation.Splash.Myservice;
import com.example.orientation.Splash.SetRemainder;

public class Notificationsetter {
    Context context;

    public Notificationsetter(Context context) {
        this.context = context;
    }

    public Notificationsetter() {
    }

    public boolean checkNotification(String notify){
        if(notify==null)
            return true;
        else if(notify.equals("On"))
            return true;
        else if (notify.equals("Off"))
            return false;
        return true;
    }

    public void stopNotification(){
        if(isMyServiceRunning(Myservice.class)){
            context.stopService(new Intent(context, Myservice.class));
        }
    }
    public void setNotification(){
        SetRemainder setRemainder = new SetRemainder(context);
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
}
