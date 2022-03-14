package com.g3;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TasksCountdownService extends Service {
    //https://stackoverflow.com/questions/22496863/how-to-run-countdowntimer-in-a-service-in-android
    SettingsDB settingsDB=MainActivity.getSettingsDB();

    /*public BroadcastService(SettingsDB db){
        this.settingsDB=db;
    }*/

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "com.g3.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    //CountDownTimer cdt = null;
    List<CountDownTimer> cdts=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");
        List<Task> tasks=settingsDB.getTasks();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date currentDateTime= Calendar.getInstance().getTime();
        for(int i=0; i<tasks.size(); i++) {
            Date endDateTime = null;
            try {
                endDateTime = sdf.parse(tasks.get(i).getEndDate() + " " + tasks.get(i).getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar endDate_cal = Calendar.getInstance();
            Calendar current_cal = Calendar.getInstance();
            endDate_cal.setTime(endDateTime);
            current_cal.setTime(currentDateTime);
            long endMillis=endDate_cal.getTimeInMillis();
            long currentMillis=current_cal.getTimeInMillis();

            if(currentMillis>endMillis){
                continue;
            }

            int finalI = i;
            CountDownTimer cdt = new CountDownTimer(endMillis-currentMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                    bi.putExtra("countdown", millisUntilFinished);
                    sendBroadcast(bi);
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFinish() {
                    //call alarm or notification
                    Log.i(TAG, "Timer finished");
                    Notifications notifications = new Notifications(MainActivity.getAppContext());
                    Notification.Builder nb = notifications.
                            getAndroidChannelNotification(tasks.get(finalI).getName(), "Your task is due", "task");
                    notifications.getManager().notify(1, nb.build());
                }
            };

            cdt.start();
            cdts.add(cdt);
        }
    }

    @Override
    public void onDestroy() {

        /*for(CountDownTimer cdt:cdts){
            cdt.cancel();
        }*/
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}

        /*List<Task> tasks=settingsDB.getTasks();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date currentDateTime= Calendar.getInstance().getTime();
        for(int i=0; i<tasks.size(); i++) {
            Date endDateTime = null;
            try {
                endDateTime = sdf.parse(end_date + " " + end_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar endDate_cal = Calendar.getInstance();
            Calendar current_cal = Calendar.getInstance();
            endDate_cal.setTime(endDateTime);
            current_cal.setTime(currentDateTime);
            endDate_cal.getTimeInMillis();
            if(startDateTime.compareTo(endDateTime)>0) {
                //startDate is after endDate

            }
        }*/
