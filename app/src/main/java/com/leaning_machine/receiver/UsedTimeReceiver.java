package com.leaning_machine.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.leaning_machine.service.UsedTimeService;

import java.util.Calendar;
import java.util.Locale;

public class UsedTimeReceiver extends BroadcastReceiver {
    public static final String EVENT_ACTION = "com.learning.USED_TIME_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("zzz", "receive");
        Intent eventServiceIntent = UsedTimeService.getCallingIntent(context);
        context.startService(eventServiceIntent);
//        sendAlarmEveryday(context);
    }

    private void sendAlarmEveryday(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 23);

        calendar.set(Calendar.MINUTE, 58);

        long triggerAtTime= SystemClock.elapsedRealtime()+10*1000;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, getAlarmIntent(context), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private Intent getAlarmIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(UsedTimeReceiver.EVENT_ACTION);
        intent.setClass(context, UsedTimeReceiver.class);
        return intent;
    }
}
