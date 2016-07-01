package com.example.spring.exammanager.core.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.adapter.QuestionAdapter;
import com.example.spring.exammanager.model.QuestionsModel;
import com.example.spring.exammanager.model.TestPaperModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.utils.IDS;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPaperActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.title_add_pager)
    CommonTitile titleAddPager;
    @Bind(R.id.edit_pager_outline)
    EditText editPagerOutline;
    @Bind(R.id.lv_questions)
    ListView lvQuestions;
    @Bind(R.id.tv_save_all)
    TextView tvSaveAll;
    @Bind(R.id.tv_add_questions)
    TextView tvAddQuestions;
    static String teacherName;
    static String paperOutline;
    @Bind(android.R.id.empty)
    TextView empty;
    List<QuestionsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paper);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        titleAddPager.setTitle("添加试卷");

        teacherName = getIntent().getStringExtra("teacherName");
        paperOutline = getIntent().getStringExtra("paperOutline");
        lvQuestions.setOnItemClickListener(this);
        if (TextUtils.isEmpty(paperOutline)) {
            initLV();
        } else {
            initLV(paperOutline);
        }
    }

    //当点击添加试卷
    private void initLV() {
        list = DBUtil.getAllQuestionsOfTeacherName(teacherName);
        editPagerOutline.setEnabled(true);
        QuestionAdapter adapter = new QuestionAdapter(this, list, R.layout.item_questions);
        lvQuestions.setAdapter(adapter);
        lvQuestions.setEmptyView(empty);
    }

    //当点击某个试卷
    private void initLV(String paperOutline) {
        list.clear();
        editPagerOutline.setText(paperOutline);
        editPagerOutline.setEnabled(false);
        QuestionAdapter adapter = new QuestionAdapter(this, DBUtil.getAllQuestionsOfOutline(paperOutline), R.layout.item_questions);
        lvQuestions.setAdapter(adapter);
        lvQuestions.setEmptyView(empty);
    }

    //提交试卷
    @OnClick(R.id.tv_save_all)
    void btnSaveAll() {
        new AlertDialog.Builder(this).setMessage("确定提交？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveAll();
            }
        }).setNegativeButton("取消", null).create().show();
    }

    void saveAll() {
        paperOutline = editPagerOutline.getText().toString().trim();
        if (TextUtils.isEmpty(paperOutline)) {
            DisPlay("试卷大纲不能为空");
            return;
        }
        if (DBUtil.getQuestionCount(paperOutline) == 0) {
            DisPlay("请添加试题");
            return;
        }
        TestPaperModel model = new TestPaperModel();
        model.userNameOfTeacher = teacherName;
        model.testPaperOutline = paperOutline;
        model.date = System.currentTimeMillis() / 1000;
        model.questionScoreSum = DBUtil.getQuestionSumScore(paperOutline);
        model.questionsCount = DBUtil.getQuestionCount(paperOutline);
        DBUtil.isExitOfPaper(model);
        this.setResult(IDS.RESULTCODE_ADDPAPER);
        this.finish();
    }

    //添加试题
    @OnClick(R.id.tv_add_questions)
    void addQuestions() {
        paperOutline = editPagerOutline.getText().toString().trim();
        if (TextUtils.isEmpty(paperOutline)) {
            DisPlay("请先填写试卷大纲");
            return;
        }
        Intent intent = new Intent(this, AddQuestionsActivity.class);
        intent.putExtra("paperOutline", paperOutline);
        intent.putExtra("teacherName", teacherName);
        startActivityForResult(intent, IDS.REQUESTCODE_ADDQUESTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IDS.REQUESTCODE_ADDQUESTION) {
            if (resultCode == IDS.RESULTCODE_ADDQUESTION) {
                initLV(paperOutline);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (list.size() > 0) {
            editPagerOutline.setText(list.get(position).paperOutline);
        }
    }
}
