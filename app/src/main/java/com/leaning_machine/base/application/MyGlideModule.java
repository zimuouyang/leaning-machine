package com.leaning_machine.base.application;

import com.bumptech.glide.annotation.GlideModule;

import java.lang.annotation.Annotation;

public class MyGlideModule implements GlideModule {
    @Override
    public String glideName() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
