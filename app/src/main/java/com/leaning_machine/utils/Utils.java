package com.leaning_machine.utils;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ArrayRes;
import androidx.annotation.RequiresApi;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.activity.LoginActivity;
import com.leaning_machine.base.dto.LearnTime;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.model.UsingApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static String TAG = "Utils";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static UsedTimeEntity getUsedTime(Context context) {
        long startTime = -1;
        long endTime = -1;
        UsedTimeEntity usedTimeEntity = new UsedTimeEntity();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(startTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        usedTimeEntity.setDate(simpleDateFormat.format(calendar.getTime()));

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

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

            total = moErDuo + pinDu + liuLiShuo + aiYueDu + qinLianxi + beiDanCi + kanDianYing + quYiZhi + math + language + others;

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

    public static int getTimeDistance(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        long beginTime = beginCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));//先算出两时间的毫秒数之差大于一天的天数

        endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);//再使endCalendar减去1天
        if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))//比较两日期的DAY_OF_MONTH是否相等
            return betweenDays + 1; //相等说明确实跨天了
        else
            return betweenDays; //不相等说明确实未跨天
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

    public static Date getStartOfDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
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

    public static String parseSecondTime(long time) {
        long hours = time / 3600;
        long minutes = (time % 3600) / 60;
        long seconds = (time % 3600) % 60;

        if (hours > 0) {
            return hours + "时" + minutes + "分" + seconds + "秒";
        } else if (minutes > 0) {
            return minutes + "分" + seconds + "秒";
        } else {
            return seconds + "秒";
        }
    }

    public static long daysBetween(Date beginDate, Date endDate) {
        long between_days = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginDate = sdf.parse(sdf.format(beginDate));
            endDate = sdf.parse(sdf.format(endDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(beginDate);
            long beginTime = cal.getTimeInMillis();
            cal.setTime(endDate);
            long endTime = cal.getTimeInMillis();
            between_days = (endTime - beginTime) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return between_days;
    }

    public static void transparencyBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static boolean checkAppInstalled(Context context, String pkgName) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static String getDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static UsedTimeEntity addTime(UsedTimeEntity usedTimeEntity, UsingApp usingApp, Context context) {
        String packageName = usingApp.getPackageName();
        long time = (System.currentTimeMillis() - usingApp.getStartTime()) / 1000;

        LearnTime todayUse = SharedPreferencesUtils.getObject(context, Constant.SP_TODAY_USE_TIME, LearnTime.class, null);
        if (todayUse == null || !todayUse.getCreateDate().equals(getDateString())) {
            todayUse = new LearnTime();
            todayUse.setCreateDate(getDateString());
        }

        usedTimeEntity.setTotalLength(usedTimeEntity.getTotalLength() + time);
        todayUse.setTotal(todayUse.getTotal() + time);

        if (isExistPackageName(packageName, R.array.xue_pin_du_group, context)) {
            usedTimeEntity.setPinDuLength(usedTimeEntity.getPinDuLength() + time);
            todayUse.setSpelling(todayUse.getSpelling() + time);
        } else if (isExistPackageName(packageName, R.array.mo_er_duo_group, context)) {
            usedTimeEntity.setErDuoLength(usedTimeEntity.getErDuoLength() + time);
            todayUse.setGrindEars(todayUse.getGrindEars() + time);
        } else if (isExistPackageName(packageName, R.array.ai_yue_du_group, context)) {
            usedTimeEntity.setYueDuLength(usedTimeEntity.getYueDuLength() + time);
            todayUse.setLoveRead(todayUse.getLoveRead() + time);
        } else if (isExistPackageName(packageName, R.array.qin_lian_xi_group, context)) {
            usedTimeEntity.setLianXiLength(usedTimeEntity.getLianXiLength() + time);
            todayUse.setPracticeFrequently(todayUse.getPracticeFrequently() + time);
        } else if (isExistPackageName(packageName, R.array.bei_dan_ci_group, context)) {
            usedTimeEntity.setDanCiLength(usedTimeEntity.getDanCiLength() + time);
            todayUse.setReciteWords(todayUse.getReciteWords() + time);
        } else if (isExistPackageName(packageName, R.array.qu_yi_zhi_group, context)) {
            usedTimeEntity.setQuLeZhiLength(usedTimeEntity.getQuLeZhiLength() + time);
            todayUse.setFluent(todayUse.getFluent() + time);
        } else if (isExistPackageName(packageName, R.array.math_group, context)) {
            usedTimeEntity.setMathLength(usedTimeEntity.getMathLength() + time);
            todayUse.setMath(todayUse.getMath() + time);
        } else if (isExistPackageName(packageName, R.array.yu_wen_group, context)) {
            usedTimeEntity.setLanguageLength(usedTimeEntity.getLanguageLength() + time);
            todayUse.setLanguage(todayUse.getLanguage() + time);
        } else {
            usedTimeEntity.setOtherLength(usedTimeEntity.getOtherLength() + time);
            todayUse.setOthers(todayUse.getOthers() + time);
        }
        SharedPreferencesUtils.putObject(context, Constant.SP_TODAY_USE_TIME, todayUse);

        return usedTimeEntity;
    }

    public static void goToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        SharedPreferencesUtils.clear(context);
    }

}
