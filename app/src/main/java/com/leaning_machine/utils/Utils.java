package com.leaning_machine.utils;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ArrayRes;
import androidx.annotation.RequiresApi;

import com.leaning_machine.R;
import com.leaning_machine.db.entity.UsedTimeEntity;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {
    private static String TAG = "Utils";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static UsedTimeEntity getUsedTime(Context context) {
        UsedTimeEntity usedTimeEntity = new UsedTimeEntity();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        usedTimeEntity.setDate(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long startTime = calendar.getTimeInMillis();


        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, System.currentTimeMillis());

        long pinDu = 0;
        long moErDuo = 0;
        long liuLiShuo = 0;
        long aiYueDu = 0;
        long qinLianxi = 0;
        long beiDanCi = 0;
        long kanDianYing = 0;
        long quYiZhi = 0;
        long go = 0;
        long language = 0;
        long total = 0;
        long others = 0;
        long math = 0;

        String packageName;
        long timeInForeground;

        if (stats != null) {
            for (UsageStats usageStats : stats) {

                timeInForeground = usageStats.getTotalTimeInForeground();

                packageName = usageStats.getPackageName();


                if (isExistPackageName(packageName, R.array.go_class_group, context)) {
                    go += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.xue_pin_du_group, context)) {
                    pinDu += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.mo_er_duo_group, context)) {
                    moErDuo += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.ai_yue_du_group, context)) {
                    aiYueDu += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.qin_lian_xi_group, context)) {
                    qinLianxi += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.bei_dan_ci_group, context)) {
                    beiDanCi += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.liu_li_shuo_group, context)) {
                    liuLiShuo += timeInForeground;
                }

                if (isExistPackageName(packageName, R.array.see_movie_group, context)) {
                    kanDianYing += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.qu_yi_zhi_group, context)) {
                    qinLianxi += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.math_group, context)) {
                    math += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.yu_wen_group, context)) {
                    language += timeInForeground;
                    continue;
                }

                if (isExistPackageName(packageName, R.array.others_group, context)) {
                    others += timeInForeground;
                }

            }

            total = moErDuo + pinDu + liuLiShuo + aiYueDu + qinLianxi + beiDanCi + kanDianYing + quYiZhi + math + language + others + go;

            usedTimeEntity.setPinDuLength(pinDu);
            usedTimeEntity.setErDuoLength(moErDuo);
            usedTimeEntity.setLiuLiLength(liuLiShuo);
            usedTimeEntity.setYueDuLength(aiYueDu);
            usedTimeEntity.setLianXiLength(qinLianxi);
            usedTimeEntity.setDanCiLength(beiDanCi);
            usedTimeEntity.setKanDianYingLength(kanDianYing);
            usedTimeEntity.setQuLeZhiLength(quYiZhi);

            usedTimeEntity.setMathLength(math);

            usedTimeEntity.setLanguageLength(language);

            usedTimeEntity.setOtherLength(others);

            usedTimeEntity.setTotalLength(total);
            return usedTimeEntity;
        } else {
            Log.d(TAG, "NO DATA");
        }
        return null;
    }

    private static boolean isExistPackageName(String packageName, @ArrayRes int stringId, Context context) {
        String[] array = context.getResources().getStringArray(stringId);
        for (String string : array) {
            if (packageName.equals(string.trim())) {
                return true;
            }
        }
        return false;
    }

    public static String parseDate(long time) {
        long seconds = (int) (time / 1000) % 60;
        long minutes = (int) ((time / (1000 * 60)) % 60);
        long hours = (int) ((time / (1000 * 60 * 60)) % 24);

        if (hours > 0) {
            return hours + "时" + minutes + "分" + seconds + "秒";
        } else if (minutes > 0) {
            return minutes + "分" + seconds + "秒";
        } else {
            return seconds + "秒";
        }
    }

    public static void transparencyBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }
}
