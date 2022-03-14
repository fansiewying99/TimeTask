package com.g3;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Notifications extends ContextWrapper{
    private NotificationManager manager;
    public static final String CHANNEL_ID = "com.g3";
    public static final String CHANNEL_NAME = "CHANNEL";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notifications(Context base) {
        super(base);
        createChannels();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(channel);
    }


    NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getAndroidChannelNotification(String title, String body, String topic) {
        Notification.Builder notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);
        switch (topic){
            case "timer":
                return notification.setSmallIcon(R.drawable.ic_timer);
            case "task":
                return notification.setSmallIcon(R.drawable.ic_tasks);
            default:
                return notification.setSmallIcon(android.R.drawable.stat_notify_more);
        }


    }
}
