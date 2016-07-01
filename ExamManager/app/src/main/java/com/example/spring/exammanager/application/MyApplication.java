package com.example.spring.exammanager.application;

import android.app.Application;

/**
 * Created by Administrator on 2016/5/9.
 */
public class MyApplication extends Application {
    public static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }

}
