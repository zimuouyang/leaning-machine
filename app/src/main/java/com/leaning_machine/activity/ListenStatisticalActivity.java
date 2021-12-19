package com.leaning_machine.activity;


import android.widget.TextView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.utils.SharedPreferencesUtils;

public class ListenStatisticalActivity extends BaseActivity {
    TextView totalText;
    TextView maxText;
    TextView currentText;

    @Override
    public void initView() {
        findViewById(R.id.play).setOnClickListener(view -> ListenStatisticalActivity.this.finish());
        totalText = findViewById(R.id.statistical_total_text);
        currentText = findViewById(R.id.statistical_continuous_text);
        maxText = findViewById(R.id.statistical_max_continuous_text);

    }

    @Override
    protected void onResume() {
        super.onResume();
        totalText.setText(SharedPreferencesUtils.getLong(this, Constant.TOTAL_DAY, 0) + "天啦");
        currentText.setText(SharedPreferencesUtils.getLong(this, Constant.CURRENT_DAY, 0) + "天啦");
        maxText.setText(SharedPreferencesUtils.getLong(this, Constant.MAX_DAY, 0) + "天啦");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_statis;
    }
}