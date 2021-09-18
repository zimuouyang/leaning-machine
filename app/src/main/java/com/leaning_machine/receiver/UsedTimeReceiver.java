package com.leaning_machine.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.leaning_machine.service.UsedTimeService;

public class UsedTimeReceiver extends BroadcastReceiver {
    public static final String EVENT_ACTION = "com.learning.USED_TIME_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent eventServiceIntent = UsedTimeService.getCallingIntent(context);
        context.startService(eventServiceIntent);
    }
}
