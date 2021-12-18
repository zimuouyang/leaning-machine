///*
// * ******************************************************************************
// * COPYRIGHT
// *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
// *   This software is supplied under the terms of a license agreement or
// *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
// *   or disclosed except in accordance with the terms in that agreement.
// *
// *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
// * ******************************************************************************
// */
//
//package com.leaning_machine.layout;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.leaning_machine.R;
//
//public class AuthDialog extends Dialog {
//    private Context context;
//    private String email;
//    private String positiveButtonText;
//    private String negativeButtonText;
//    private OnDialogClick listener;
//    private TextView mTvError;
//    private Button btnConfirm;
//    private boolean needCode;
//    private RelativeLayout frameLayout;
//    private ProgressBar pb;
//
//    public AuthDialog(Builder builder, Context context) {
//        super(context, R.style.Dialog_Fullscreen);
//        setCanceledOnTouchOutside(true);
//        this.context = builder.context;
//        this.email = builder.email;
//        this.positiveButtonText = builder.positiveButtonText;
//        this.negativeButtonText = builder.negativeButtonText;
//        this.listener = builder.listener;
//        this.needCode = builder.needCode;
//        init();
//    }
//
//    public AuthDialog(Builder builder, Context context, int theme) {
//        super(context, theme);
//        setCanceledOnTouchOutside(true);
//        this.context = builder.context;
//        this.positiveButtonText = builder.positiveButtonText;
//        this.negativeButtonText = builder.negativeButtonText;
//        this.listener = builder.listener;
//        this.needCode = builder.needCode;
//        init();
//    }
//
//    public static Builder newBuilder() {
//        return new Builder();
//    }
//
//    public void showLoginDenied(String errMsg) {
//        mTvError.setVisibility(View.VISIBLE);
//        mTvError.setText(errMsg);
//        btnConfirm.setText(context.getString(R.string.btn_ok));
//        btnConfirm.setEnabled(true);
//        frameLayout.setVisibility(View.GONE);
//        pb.setVisibility(View.GONE);
//    }
//
//    public void showLogging() {
//        mTvError.setVisibility(View.GONE);
//        mTvError.setText("");
//        btnConfirm.setText(context.getString(R.string.btn_logging));
//        btnConfirm.setEnabled(false);
//        pb.setVisibility(View.VISIBLE);
//    }
//
//    public void showSuccess() {
//        pb.setVisibility(View.GONE);
//        frameLayout.setVisibility(View.VISIBLE);
//
//        Runnable a = new Runnable() {
//            @Override
//            public void run() {
//                dismiss();
//            }
//        };
//        Handler handler = new Handler();
//        handler.postDelayed(a, 300);
//
//    }
//
//    private final void init() {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        // instantiate the dialog with the custom Theme
//        View layout = inflater.inflate(R.layout.auth_dialog, null);
//        addContentView(layout, new LayoutParams(
//                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//
//        frameLayout = (RelativeLayout) findViewById(R.id.fl_progress);
//        pb = (ProgressBar) findViewById(R.id.pb_interminate);
//
//        final EditText tvUserName = (EditText) findViewById(R.id.tv_user_name);
//        final EditText tvPwd = (EditText) findViewById(R.id.tv_pwd);
//        final EditText tvCode = (EditText) findViewById(R.id.tv_two_factor);
//        final LinearLayout lvCode = (LinearLayout) findViewById(R.id.lv_code);
//        if (email != null) {
//            tvUserName.setText(email);
//        }
//        if (needCode) {
//            lvCode.setVisibility(View.VISIBLE);
//        } else {
//            lvCode.setVisibility(View.GONE);
//        }
//        mTvError = (TextView) findViewById(R.id.tv_error);
//        // set the confirm button
//        if (positiveButtonText != null) {
//            btnConfirm = ((Button) layout.findViewById(R.id.positiveButton));
//            btnConfirm.setText(positiveButtonText);
//            if (listener != null) {
//                layout.findViewById(R.id.positiveButton)
//                        .setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                                listener.onPositiveClicked(tvUserName.getText(), tvPwd.getText(), tvCode.getText());
//                            }
//                        });
//            }
//        } else {
//
//            // if no confirm button just set the visibility to GONE
//            layout.findViewById(R.id.positiveButton_mr).setVisibility(
//                    View.GONE);
//        }
//        // set the cancel button
//        if (negativeButtonText != null) {
//            ((Button) layout.findViewById(R.id.negativeButton))
//                    .setText(negativeButtonText);
//            if (listener != null) {
//                layout.findViewById(R.id.negativeButton)
//                        .setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                                listener.onNegativeClicked();
//                            }
//                        });
//            }
//        } else {
//            // if no confirm button just set the visibility to GONE
//            layout.findViewById(R.id.negativeButton_mr).setVisibility(
//                    View.GONE);
//        }
//
//
//        setContentView(layout);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tvPwd.setFocusable(true);
//                tvPwd.setFocusableInTouchMode(true);
//                tvPwd.requestFocus();
//                tvPwd.findFocus();
//            }
//        }, 100);
//
//    }
//
//
//    public interface OnDialogClick {
//        void onPositiveClicked(CharSequence userName, CharSequence password, CharSequence code);
//
//        void onNegativeClicked();
//    }
//
//    public static class Builder {
//        private Context context;
//        private String email;
//        private String positiveButtonText;
//        private String negativeButtonText;
//        private OnDialogClick listener;
//        private boolean needCode;
//
//
//        public Builder context(Context context) {
//            this.context = context;
//            return this;
//        }
//
//
//        public Builder email(String email) {
//            this.email = email;
//            return this;
//        }
//
//
//        public Builder positiveButton(String positiveButtonText) {
//            this.positiveButtonText = positiveButtonText;
//            return this;
//        }
//
//        public Builder negativeButton(String negativeButtonText) {
//            this.negativeButtonText = negativeButtonText;
//            return this;
//        }
//
//        public Builder ButtonClickListener(OnDialogClick listener) {
//            this.listener = listener;
//            return this;
//        }
//
//        public AuthDialog build() {
//            if (this.context == null) {
//                throw new NullPointerException("Activity can not be NULL!!");
//            }
//            return new AuthDialog(this, context);
//        }
//
//
//        public Builder needCode(boolean needCode) {
//            this.needCode = needCode;
//            return this;
//        }
//    }
//}