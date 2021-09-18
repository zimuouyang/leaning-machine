package com.leaning_machine.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.adapter.UsedTimeAdapter;
import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.receiver.UsedTimeReceiver;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author John
 * @date 2021/9/15
 */
public class PersonCenterFragment extends BaseFragment {
    RecyclerView recyclerView;
    private UsedTimeAdapter adapter;

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

        new UsedTimeTask(getActivity()).execute();
    }

    private void sendAlarmEveryday(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 23);

        calendar.set(Calendar.MINUTE, 58);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, getAlarmIntent(context), PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private Intent getAlarmIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(UsedTimeReceiver.EVENT_ACTION);
        intent.setClass(context, UsedTimeReceiver.class);
        return intent;
    }

    private class UsedTimeTask extends AsyncTask<Void, Integer, List<UsedTimeEntity>> {

        Context context;

        public UsedTimeTask(Context context) {
            this.context = context;

        }

        @Override
        protected List<UsedTimeEntity> doInBackground(Void... voids) {
            List<UsedTimeEntity> list = GlobalDatabase.getInstance(context).usedTimeDao().getAll();

            UsedTimeEntity current = Utils.getUsedTime(context);
            list.add(current);
            return list;
        }

        @Override
        protected void onPostExecute(List<UsedTimeEntity> usedTimeEntities) {
            super.onPostExecute(usedTimeEntities);
            adapter.setData(usedTimeEntities);
        }

    }
}