package com.example.spring.exammanager.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.ui.widget.CommonTitile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//学生主页

public class MainOfStudentActivity extends BaseActivity {

    @Bind(R.id.title_main_student)
    CommonTitile titleMainStudent;
    @Bind(R.id.tv_mainshow_obstacle)
    TextView tvMainshowObstacle;
    @Bind(R.id.ll_start_the_test)
    LinearLayout llStartTheTest;
    @Bind(R.id.ll_score_search)
    LinearLayout llScoreSearch;
    static String studentName;
    @Bind(R.id.ll_user_manager_s)
    LinearLayout llUserManagerS;
    @Bind(R.id.ll_settings_s)
    LinearLayout llSettingsS;
    @Bind(R.id.tv_welcome_xx_sutdent)
    TextView tvWelcomeXxSutdent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        titleMainStudent.setTitle("学生主界面");
        titleMainStudent.hideBack();
        studentName = getIntent().getStringExtra("userName");
        tvWelcomeXxSutdent.setText("欢迎" + studentName + "登录");
    }

    @OnClick(R.id.ll_start_the_test)
    void startTheTest() {
        Intent intent = new Intent(this, ChoosePaperActivity.class);
        intent.putExtra("studentName", studentName);
        startActivity(intent);
    }

    @OnClick(R.id.ll_score_search)
    void scoreSearch() {
        Intent intent = new Intent(this, ScoreSearchActivity.class);
        intent.putExtra("studentName", studentName);
        startActivity(intent);
    }

    @OnClick({R.id.ll_settings_s, R.id.ll_user_manager_s})
    void showHelp() {
        showHelpDialog();
    }
}
