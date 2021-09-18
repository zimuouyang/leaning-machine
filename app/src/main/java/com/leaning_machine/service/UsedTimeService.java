package com.leaning_machine.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.utils.Utils;

import java.util.Calendar;
import java.util.Locale;

public class UsedTimeService extends IntentService {

    public UsedTimeService() {
        super("UsedTimeService");
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UsedTimeService.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsedTimeEntity usedTimeEntity = Utils.getUsedTime(this);
            if(usedTimeEntity != null) {
                GlobalDatabase.getInstance(this).usedTimeDao().insertUsedTime(usedTimeEntity);
            }
        }

    }


}
