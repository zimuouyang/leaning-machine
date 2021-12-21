package com.leaning_machine.domain;

import android.content.Context;
import android.util.Log;

import rx.Observer;

public class DefaultObserver<T> implements Observer<T> {

    private static final String TAG = DefaultObserver.class.getSimpleName();

    private Context context;

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        // no-op by default.
        if (e == null || e.getMessage() == null) {
            Log.e(TAG, "Throwable e: " + e);
        }
    }

    @Override
    public void onNext(T t) {
        // no-op by default.
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getSubscriberContext() {
        return this.context;
    }
}
