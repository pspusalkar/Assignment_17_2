package com.example.poojajoshi.assignment_17_2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Chronometer;
import android.os.Binder;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyService extends Service {
    private static String LOG_TAG = "BoundService";
    private IBinder mBinder = new MyBinder();
    private Chronometer mChronometer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(LOG_TAG, "in onCreate");
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        mChronometer.stop();
    }

    public String getTimestamp() {
        // get current date
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date = dateFormat.format(d);

        // get time
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);

        // return date + time string
        return date + " " + hours + ":" + minutes + ":" + seconds + ":" + millis;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
