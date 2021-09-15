package com.leaning_machine.fragment;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
/**
 *
 * @author John
 * @date 2021/9/15
 */
public abstract class BaseFragment extends Fragment {
    public abstract void initView(View view);

    public void openApp(String packageName, Context context) {

    }
}
