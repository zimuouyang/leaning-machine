package com.leaning_machine.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.leaning_machine.R;

/**
 * @author John
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    ImageView goClass;
    ImageView goExtension;
    ImageView goRecord;
    ImageView goEnglish;
    ImageView goMath;
    ImageView goLanguage;
    ImageView goTextBook;
    ImageView task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        goClass = findViewById(R.id.welcome_go_class);
        goExtension = findViewById(R.id.welcome_extension);
        goRecord = findViewById(R.id.welcome_check_record);
        goEnglish = findViewById(R.id.welcome_english);
        goMath = findViewById(R.id.welcome_math);
        goLanguage = findViewById(R.id.welcome_language);
        goTextBook = findViewById(R.id.welcome_textbook);
        task = findViewById(R.id.task);

        goClass.setOnClickListener(this);
        goExtension.setOnClickListener(this);
        goRecord.setOnClickListener(this);
        goEnglish.setOnClickListener(this);
        goMath.setOnClickListener(this);
        goLanguage.setOnClickListener(this);
        goTextBook.setOnClickListener(this);
        task.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcome_go_class:
                startActivity(new Intent(this, GoClassActivity.class));
                break;
            case R.id.welcome_extension:
                startActivity(new Intent(this, ExpandActivity.class));
                break;
            case R.id.welcome_check_record:
                break;
            case R.id.welcome_english:
                startActivity(new Intent(this, EnglishActivity.class));
                break;
            case R.id.welcome_math:
                startActivity(new Intent(this, MathActivity.class));
                break;
            case R.id.welcome_language:
                startActivity(new Intent(this, LanguageActivity.class));
                break;
            case R.id.welcome_textbook:
                startActivity(new Intent(this, TextBookActivity.class));
                break;
            case R.id.task:
                break;
        }
    }
}