package com.leaning_machine.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.base.dto.TerminalLoginDto;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.utils.SharedPreferencesUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TextView accountText;
    TextView passwordText;
    Button accountLoginText;
    Button messageLoginText;
    Button loginButton;
    boolean accountLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesUtils.getBoolean(this, Constant.LOGIN, false)) {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }


    @Override
    public void initView() {
        accountText = findViewById(R.id.account);
        passwordText = findViewById(R.id.password);
        accountLoginText = findViewById(R.id.account_login);
        messageLoginText = findViewById(R.id.message_login);
        loginButton = findViewById(R.id.login);
        accountLoginText.setOnClickListener(this);
        messageLoginText.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.account_login:
                Log.d("zzz", "account_login");
                accountLogin = true;
                switchLogin();
                break;
            case R.id.message_login:
                accountLogin = false;
                switchLogin();
                break;
        }
    }

    private void switchLogin() {
        if (accountLogin) {
            accountLoginText.setBackground(getDrawable(R.drawable.bg_corner_15_white));
            accountLoginText.setTextColor(getResources().getColor(R.color.select_login_text));
            messageLoginText.setTextColor(getResources().getColor(R.color.white));
            messageLoginText.setBackground(null);
        } else {
            messageLoginText.setBackground(getDrawable(R.drawable.bg_corner_15_white));
            messageLoginText.setTextColor(getResources().getColor(R.color.select_login_text));
            accountLoginText.setTextColor(getResources().getColor(R.color.white));
            accountLoginText.setBackground(null);
        }
    }

    private void login() {
        TerminalLoginDto terminalLoginDto = new TerminalLoginDto();
        terminalLoginDto.setName(accountText.getText().toString());
        terminalLoginDto.setPwd(passwordText.getText().toString());
        CommonApiService.instance.terminalLogin(terminalLoginDto).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TerminalAuthDto>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TerminalAuthDto terminalAuthDto) {
                if (terminalAuthDto.getBusinessCode() == 200) {
                    startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                    SharedPreferencesUtils.putString(LoginActivity.this, Constant.TOKEN, Constant.TOKEN_PREFIX + terminalAuthDto.getToken());
                    SharedPreferencesUtils.putBoolean(LoginActivity.this, Constant.LOGIN, true);
                    HttpClient.instance.addHeader(Constant.TOKEN, Constant.TOKEN_PREFIX + terminalAuthDto.getToken());
                } else {
                    //toto show 密码错误
                    Toast.makeText(LoginActivity.this, "账户名或密码错误", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.putBoolean(LoginActivity.this, Constant.LOGIN, false);
                    SharedPreferencesUtils.putString(LoginActivity.this, Constant.TOKEN, "");
                }
            }
        });

    }


    //todo 判断登录状态

}