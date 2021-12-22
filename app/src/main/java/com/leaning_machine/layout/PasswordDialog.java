/*
 * ******************************************************************************
 * COPYRIGHT
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *
 *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
 * ******************************************************************************
 */

package com.leaning_machine.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.model.VoiceType;
import com.leaning_machine.utils.SharedPreferencesUtils;

public class PasswordDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private ImageView voiceImg;
    private EditText pwdText;
    private ImageView cancelImg;
    private ImageView confirmImg;
    private PasswordClick passwordClick;
    private Context context;
    private VoiceType voiceType;

    public PasswordDialog(Builder builder, Context context) {
        super(context, R.style.LoadDialog);
        this.context = builder.context;
        this.voiceType = builder.voiceType;
        mContext = context;
    }

    public void setPasswordClick(PasswordClick passwordClick) {
        this.passwordClick = passwordClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password_layout);

        //点击imageview外侧区域，动画不会消失
        setCanceledOnTouchOutside(false);

        voiceImg = findViewById(R.id.voice);
        pwdText = findViewById(R.id.password_text);
        cancelImg = findViewById(R.id.cancel);
        confirmImg = findViewById(R.id.confirm);
        if (voiceType == VoiceType.PASSWORD) {
            pwdText.setVisibility(View.VISIBLE);
            cancelImg.setVisibility(View.VISIBLE);
        }

        voiceImg.setOnClickListener(this);
        confirmImg.setOnClickListener(this);
        cancelImg.setOnClickListener(this);

        pwdText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    //dialog弹出软键盘
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                }
            }
        });
    }

    public static PasswordDialog.Builder newBuilder() {
        return new PasswordDialog.Builder();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voice:
                playVoice();
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                if (voiceType == VoiceType.PASSWORD) {
                    if (confirmPwd()) {
                        if (this.passwordClick != null) {
                            passwordClick.onSuccess();
                        }
                        dismiss();
                    } else {
                        Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dismiss();
                }
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        playVoice();
    }

    private void playVoice() {
        MediaPlayer mMediaPlayer;
        switch (voiceType) {
            case TODAY:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.today);
                break;
            case MINUTE:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.minute);
                break;
            default:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.password);
                break;
        }
        mMediaPlayer.start();
    }

    private boolean confirmPwd() {
        if (pwdText.getText().toString().isEmpty()) {
            return false;
        }
        if (pwdText.getText().toString().equals(SharedPreferencesUtils.getString(mContext, Constant.TERMINAL_PWD, ""))) {
            return true;
        }
        return false;
    }

    public static class Builder {
        private String des;
        private Context context;
        private VoiceType voiceType;


        public PasswordDialog.Builder context(Context context) {
            this.context = context;
            return this;
        }


        public PasswordDialog.Builder des(String des) {
            this.des = des;
            return this;
        }

        public PasswordDialog.Builder type(VoiceType voiceType) {
            this.voiceType = voiceType;
            return this;
        }


        public PasswordDialog build() {
            if (this.context == null) {
                throw new NullPointerException("Activity can not be NULL!!");
            }
            return new PasswordDialog(this, context);
        }
    }

    public interface PasswordClick {
        void onSuccess();
    }
}