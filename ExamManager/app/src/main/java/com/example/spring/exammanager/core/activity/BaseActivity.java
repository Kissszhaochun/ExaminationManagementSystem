package com.example.spring.exammanager.core.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.spring.exammanager.appmamager.AppManager;
import com.example.spring.exammanager.ui.widget.Dialog.CommonWaitDialog;
import com.example.spring.exammanager.utils.db.DataBaseUtil;

/**
 * \
 */
public abstract class BaseActivity extends Activity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    protected DataBaseUtil DBUtil;
    protected CommonWaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstence().addActivity(this);
        DBUtil = new DataBaseUtil(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    //初始化view
    protected abstract void initView();

    protected void waitDialogShow(String content) {
        waitDialog = new CommonWaitDialog.Builder(this, content).create();
        waitDialog.show();
    }

    protected void showHelpDialog() {
        new AlertDialog.Builder(this).setMessage("敬请期待").setNegativeButton("确定", null).create().show();
    }

    protected void openActivity(Class<?> cls) {
        openActivity(cls, null);
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void DisPlay(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

}
