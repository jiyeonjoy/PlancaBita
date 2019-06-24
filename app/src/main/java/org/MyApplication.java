package org;

import android.app.Application;
import android.os.Build;

import org.NotificationManager;

public class MyApplication extends Application {

    @Override

    public void onCreate() {

        super.onCreate();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager.createChannel(this);

        }

    }

}