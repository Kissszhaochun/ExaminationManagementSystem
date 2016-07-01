package com.example.spring.exammanager.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.ui.widget.CommonTitile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//教师主页

public class MainOfTeacherActivity extends BaseActivity {

    @Bind(R.id.main_title)
    CommonTitile mainTitle;
    @Bind(R.id.img_teacher)
    ImageView imgTeacher;
    @Bind(R.id.tv_mainshow_obstacle)
    TextView tvMainshowObstacle;
    @Bind(R.id.ll_test_pager_manager)
    LinearLayout llTestPagerManager;
    @Bind(R.id.ll_score_search)
    LinearLayout llScoreSearch;
    @Bind(R.id.ll_user_manager)
    LinearLayout llUserManager;

    static String teacherName;
    @Bind(R.id.ll_settings)
    LinearLayout llSettings;
    @Bind(R.id.tv_welcome_xx_teacher)
    TextView tvWelcomeXxTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        teacherName = getIntent().getExtras().getString("userName");
        mainTitle.hideBack();
        mainTitle.setTitle("教师主界面");
        tvWelcomeXxTeacher.setText("欢迎" + teacherName + "登录");

    }

    @OnClick(R.id.ll_test_pager_manager)
    void testPaperManager() {
        Bundle b = new Bundle();
        b.putString("userName", teacherName);
        openActivity(TestPaperManagerActivity.class, b);
    }

    @OnClick(R.id.ll_score_search)
    void scoreSearch() {
        Intent intent = new Intent(this, ScoreSearchActivity.class);
        intent.putExtra("teacherName", teacherName);
        startActivity(intent);
    }

    @OnClick({R.id.ll_user_manager, R.id.ll_settings})
    void showHelp() {
        showHelpDialog();
    }
}
