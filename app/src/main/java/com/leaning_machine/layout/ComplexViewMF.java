package com.leaning_machine.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.Announcement;

public class ComplexViewMF extends MarqueeFactory<LinearLayout, Announcement> {
    private LayoutInflater inflater;

    public ComplexViewMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public LinearLayout generateMarqueeItemView(Announcement data) {
        LinearLayout mView = (LinearLayout) inflater.inflate(R.layout.layout_notice, null);
        if (data.getTitle() == null || data.getTitle().isEmpty()) {
            ((TextView) mView.findViewById(R.id.title)).setVisibility(View.GONE);
        }
        ((TextView) mView.findViewById(R.id.title)).setText(data.getTitle());
        ((TextView) mView.findViewById(R.id.content)).setText(data.getContent());
        return mView;
    }
}