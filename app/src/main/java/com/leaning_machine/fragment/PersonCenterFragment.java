package com.leaning_machine.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.SystemClock;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.UsedTimeAdapter;
import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.receiver.UsedTimeReceiver;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author John
 * @date 2021/9/15
 */
public class PersonCenterFragment extends BaseFragment implements View.OnClickListener {
    RecyclerView recyclerView;
    private UsedTimeAdapter adapter;
    private TextView textView;

    public PersonCenterFragment() {
        // Required empty public constructor
    }

    public static PersonCenterFragment newInstance() {
        return new PersonCenterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            sendAlarmEveryday(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_center, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.usedTimeRecyclerView);
        adapter = new UsedTimeAdapter(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        textView = view.findViewById(R.id.total_time);


        if (getActivity() == null) {
            return;
        }
        long dayMillis = System.currentTimeMillis() - SharedPreferencesUtils.getLong(getActivity(), Constant.TIME, -1);
        long day = dayMillis / 1000 / 60 / 60 / 24;

        SpannableString textSpanned = new SpannableString(String.format(getString(R.string.listen_read), day));
        textSpanned.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2961")),
                8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(textSpanned);

        view.findViewById(R.id.read_along).setOnClickListener(this);
        new UsedTimeTask(getActivity()).execute();
    }

    private void sendAlarmEveryday(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 23);

        calendar.set(Calendar.MINUTE, 58);

        long triggerAtTime= SystemClock.elapsedRealtime()+10*1000;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, getAlarmIntent(context), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime, 3 * 1000, pendingIntent);

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private Intent getAlarmIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(UsedTimeReceiver.EVENT_ACTION);
        intent.setClass(context, UsedTimeReceiver.class);
        return intent;
    }

    @Override
    public void onClick(View view) {
        if (getActivity() == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.read_along:
                openApp(getString(R.string.english_ai_yue_du_read_long), getActivity());
                break;
        }
    }

    private class UsedTimeTask extends AsyncTask<Void, Integer, List<UsedTimeEntity>> {

        Context context;

        public UsedTimeTask(Context context) {
            this.context = context;

        }

        @Override
        protected List<UsedTimeEntity> doInBackground(Void... voids) {
            List<UsedTimeEntity> list = GlobalDatabase.getInstance(context).usedTimeDao().getAll();


            //查询过去的记录，最多到那一天， 如果是当天，则将今天的记录设置为， 反之取过去的每一天
            //最新的一天是那一天
            //拿到过去n天的毫秒数

            UsedTimeEntity current = Utils.getUsedTime(context);

            Iterator<UsedTimeEntity> iterator;
            iterator = list.iterator();
            while (iterator.hasNext()) {
                if (current != null) {
                    UsedTimeEntity entity = iterator.next();
                    if (entity.getDate().equals(current.getDate())) {
                        current.setId(entity.getId());
                        iterator.remove();
                    }
                    GlobalDatabase.getInstance(context).usedTimeDao().insertUsedTime(current);
                }
            }

            if (current != null) {
                list.add(current);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<UsedTimeEntity> usedTimeEntities) {
            super.onPostExecute(usedTimeEntities);
            adapter.setData(usedTimeEntities);
        }
    }
}