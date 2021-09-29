package com.leaning_machine.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.Spanned;
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
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<UsedTimeEntity> savedList = GlobalDatabase.getInstance(context).usedTimeDao().getAll();

            List<UsedTimeEntity> currentList = new ArrayList<>();
            UsedTimeEntity current;

            UsedTimeEntity lastTimeEntity;
            if (!savedList.isEmpty()) {
                //获取最后一个
                lastTimeEntity = savedList.get(savedList.size() - 1);
                try {
                    Date lastDate = simpleDateFormat.parse(lastTimeEntity.getDate());
                    Date nowDate = new Date();

                    //在这之前
                    if (lastDate.before(nowDate)) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(lastDate);
                        //在之前的
                        for (int i = 0; i < Utils.getTimeDistance(lastDate, nowDate); i++) {
                            c.add(Calendar.DATE, 1);
                            long startTime = Utils.getStartOfDay(c.getTime()).getTime();
                            long endTime = Utils.getEndOfDay(c.getTime()).getTime();
                            UsedTimeEntity usedTimeEntity =  Utils.getUsedTime(context, startTime, endTime);
                            GlobalDatabase.getInstance(context).usedTimeDao().insertUsedTime(usedTimeEntity);
                            currentList.add(usedTimeEntity);
                        }
                        //同一天
                    } else if (simpleDateFormat.format(lastDate).equals(simpleDateFormat.format(nowDate))) {
                        current = Utils.getUsedTime(context, Utils.getStartOfDay(new Date()).getTime(), System.currentTimeMillis());
                        if (current != null) {
                            current.setId(lastTimeEntity.getId());
                        }
                        GlobalDatabase.getInstance(context).usedTimeDao().insertUsedTime(current);

                        //更新之后添加进去
                        currentList.add(current);
                        savedList.remove(savedList.size() - 1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                //之前未保存
                UsedTimeEntity usedTimeEntity =  Utils.getUsedTime(context, Utils.getStartOfDay(new Date()).getTime(), System.currentTimeMillis());
                GlobalDatabase.getInstance(context).usedTimeDao().insertUsedTime(usedTimeEntity);
                currentList.add(usedTimeEntity);
            }

            savedList.addAll(currentList);
            Collections.reverse(savedList);
            return savedList;
        }

        @Override
        protected void onPostExecute(List<UsedTimeEntity> usedTimeEntities) {
            super.onPostExecute(usedTimeEntities);
            adapter.setData(usedTimeEntities);
        }
    }
}