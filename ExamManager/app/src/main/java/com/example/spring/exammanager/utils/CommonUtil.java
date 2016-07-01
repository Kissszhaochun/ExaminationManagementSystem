package com.example.spring.exammanager.utils;

import com.example.spring.exammanager.application.MyApplication;

/**
 * Created by Administrator on 2016/6/30.
 */
public class CommonUtil {
    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getApplication().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = MyApplication.getApplication().getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
