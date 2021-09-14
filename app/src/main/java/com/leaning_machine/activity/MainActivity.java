package com.leaning_machine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.leaning_machine.R;
import com.leaning_machine.fragment.LearnPhonicsFragment;
import com.leaning_machine.fragment.MainFragment;

/**
 * @author John
 */
public class MainActivity extends AppCompatActivity {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            fragment = MainFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_layout, fragment, "tag");
            transaction.commit();
        }else{
            fragment = getSupportFragmentManager().findFragmentByTag("tag");
        }

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchContent(fragment, LearnPhonicsFragment.newInstance());
            }
        });
    }

    /**
     * 防止Fragement数据重复加载，采用显示和隐藏的方式
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (fragment != to) {
            fragment = to;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            if (!to.isAdded()) {
                ft.hide(from).add(R.id.content_layout, to).commitAllowingStateLoss();
            } else {
                ft.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }
}