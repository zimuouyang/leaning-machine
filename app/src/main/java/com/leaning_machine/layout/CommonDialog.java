

package com.leaning_machine.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.leaning_machine.R;

public class CommonDialog extends Dialog {
    private Context context;
    private String des;
    private TextView desText;

    public CommonDialog(Builder builder, Context context) {
        super(context, R.style.Dialog_Fullscreen);
        setCanceledOnTouchOutside(true);
        this.context = builder.context;
        this.des = builder.des;
        init();
    }

    public CommonDialog(Builder builder, Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(true);
        this.context = builder.context;
        this.des = builder.des;
        init();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.common_dialog, null);
        addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        desText = findViewById(R.id.des);

        desText.setText(des);
        findViewById(R.id.close).setOnClickListener(view -> dismiss());
    }

    public static class Builder {
        private String des;
        private Context context;


        public Builder context(Context context) {
            this.context = context;
            return this;
        }


        public Builder des(String des) {
            this.des = des;
            return this;
        }


        public CommonDialog build() {
            if (this.context == null) {
                throw new NullPointerException("Activity can not be NULL!!");
            }
            return new CommonDialog(this, context);
        }
    }
}