package com.example.spring.exammanager.appmamager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * activity管理
 */
public class AppManager {
    private static final AppManager appManager = new AppManager();
    private static Stack<Activity> activityStack;

    private AppManager() {
    }

    public static AppManager getInstence() {
        return appManager;
    }

    //往栈中添加activity
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    //获取栈顶的activity
    public Activity getTopActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    //干掉栈顶的activity
    public void killTopActivity() {
        Activity activity = activityStack.lastElement();
        killActivity(activity);
    }


    //干掉一个activity
    public void killActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    //干掉一个activity
    public void killActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }

    //干掉所有的activity
    public void killAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    //退出app
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
