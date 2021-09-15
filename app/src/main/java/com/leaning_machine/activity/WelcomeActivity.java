package com.leaning_machine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.leaning_machine.R;

/**
 * @author John
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.go).setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, MainActivity.class)));
    }
}