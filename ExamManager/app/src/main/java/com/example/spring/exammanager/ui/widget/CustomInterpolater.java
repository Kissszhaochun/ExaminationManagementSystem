package com.example.spring.exammanager.ui.widget;

import android.view.animation.Interpolator;

/**
 * 改变动画的播放速度
 */
public class CustomInterpolater implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return input;
    }
}
