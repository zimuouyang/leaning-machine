package com.leaning_machine.layout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.activity.PersonCenterActivity;
import com.leaning_machine.adapter.ContentAppAdapter;
import com.leaning_machine.model.App;

import java.util.List;

public class TopWithContentLayout extends LinearLayout implements View.OnClickListener {
    private LinearLayout topTitleLayout;
    //放置标题的集合
    private List<String> titleList;
    private String title;
    private TextView titleText;
    private ImageView personCenterImg;
    private TextView topContentText;
    private ImageView topImg;
    private RecyclerView contentView;
    private List<App> apps;
    private ContentAppAdapter contentAppAdapter;
    private Context context;
    private int currentIndex;
    private int topImgId;
    private String topContent;


    public TopWithContentLayout(Context context) {
        super(context);
        initView(context);
        this.context = context;
    }

    public TopWithContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
        this.context = context;
    }

    public TopWithContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initView(context);
        this.context = context;
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TopWithContentLayout);
        topImgId = mTypedArray.getResourceId(R.styleable.TopWithContentLayout_top_image, 0);
        topContent = mTypedArray.getString(R.styleable.TopWithContentLayout_top_text);
        title = mTypedArray.getString(R.styleable.TopWithContentLayout_title);
        //获取资源后要及时回收
        mTypedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_top_with_content, this, true);
        titleText = findViewById(R.id.title);
        personCenterImg = findViewById(R.id.person_center);
        topContentText = findViewById(R.id.top_content);
        topImg = findViewById(R.id.top_img);
        contentView = findViewById(R.id.top_app_content);
        topTitleLayout = findViewById(R.id.top_title_layout);

        titleText.setText(title);
        topImg.setImageDrawable(getResources().getDrawable(topImgId));
        topContentText.setText(topContent);

        initAdapter(context);
        initClick();
    }

    private void initAdapter(Context context) {
        contentAppAdapter = new ContentAppAdapter(context);
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        contentView.setLayoutManager(manager);
        contentView.setAdapter(contentAppAdapter);
        contentView.addItemDecoration(new SpaceItemDecoration(15));
    }

    public void setAppList(List<App> apps) {
        this.apps = apps;
        contentAppAdapter.setData(apps);
    }

    private void initClick() {
        personCenterImg.setOnClickListener(this);
    }

    public void addTitle(List<String> titles, TitleClick titleClick) {
        this.titleList = titles;
        for (int i = 0; i < titleList.size(); i++) {
            View view = View.inflate(context, R.layout.item_top_title, null);
            TextView titleText = view.findViewById(R.id.item_title);
            titleText.setText(titles.get(i));
            final int index = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleClick.onClick(index, titles.get(index));
                    refreshTitle(currentIndex, index);
                    currentIndex = index;
                }
            });
            topTitleLayout.addView(view);
            setSelectTitle(0);
        }
    }

    private void refreshTitle(int originIndex, int index) {
        View originView = topTitleLayout.getChildAt(originIndex);
        TextView originText = originView.findViewById(R.id.item_title);
        originText.setTextColor(getResources().getColor(R.color.white));
        originView.findViewById(R.id.item_line).setVisibility(INVISIBLE);

       setSelectTitle(index);
    }

    private void setSelectTitle(int index) {
        View view = topTitleLayout.getChildAt(index);
        TextView currentText = view.findViewById(R.id.item_title);
        currentText.setTextColor(getResources().getColor(R.color.top_select_text_color));
        view.findViewById(R.id.item_line).setVisibility(VISIBLE);
    }

    public void setTopContent(String content, Drawable drawable) {
        topContentText.setText(content);
        topImg.setImageDrawable(drawable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_center:
                context.startActivity(new Intent(context, PersonCenterActivity.class));
                break;
        }
    }

    public interface TitleClick {
        void onClick(int index, String title);
    }
}
