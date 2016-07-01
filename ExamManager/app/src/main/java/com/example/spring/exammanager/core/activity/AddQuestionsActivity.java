package com.example.spring.exammanager.core.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.QuestionOption;
import com.example.spring.exammanager.model.QuestionsModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.utils.IDS;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddQuestionsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_add_questions)
    CommonTitile titleAddQuestions;
    @Bind(R.id.edit_question_name)
    EditText editQuestionName;
    @Bind(R.id.edit_question_a)
    EditText editQuestionA;
    @Bind(R.id.edit_question_b)
    EditText editQuestionB;
    @Bind(R.id.edit_question_c)
    EditText editQuestionC;
    @Bind(R.id.edit_question_d)
    EditText editQuestionD;
    @Bind(R.id.edit_score)
    EditText editScore;
    @Bind(R.id.edit_correct_and_analysis)
    EditText editCorrectAndAnalysis;
    @Bind(R.id.btn_save_question)
    Button btnSaveQuestion;
    @Bind(R.id.tv_questionA)
    TextView tvQuestionA;
    @Bind(R.id.ll_questionA)
    LinearLayout llQuestionA;
    @Bind(R.id.tv_questionB)
    TextView tvQuestionB;
    @Bind(R.id.ll_questionB)
    LinearLayout llQuestionB;
    @Bind(R.id.tv_questionC)
    TextView tvQuestionC;
    @Bind(R.id.ll_questionC)
    LinearLayout llQuestionC;
    @Bind(R.id.tv_questionD)
    TextView tvQuestionD;
    @Bind(R.id.ll_questionD)
    LinearLayout llQuestionD;
    String paperOutline;
    static String teacherName;
    QuestionOption current, previous, optionA, optionB, optionC, optionD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        paperOutline = getIntent().getStringExtra("paperOutline");
        teacherName = getIntent().getStringExtra("teacherName");

        titleAddQuestions.setTitle("添加试题");
        optionA = new QuestionOption(llQuestionA, tvQuestionA, IDS.A);
        optionB = new QuestionOption(llQuestionB, tvQuestionB, IDS.B);
        optionC = new QuestionOption(llQuestionC, tvQuestionC, IDS.C);
        optionD = new QuestionOption(llQuestionD, tvQuestionD, IDS.D);
        current = optionA;
        current.changeToOnPressed();
    }

    @OnClick(R.id.btn_save_question)
    void saveQuestion() {
        String questionName = editQuestionName.getText().toString().trim();
        String questionA = editQuestionA.getText().toString().trim();
        String questionB = editQuestionB.getText().toString().trim();
        String questionC = editQuestionC.getText().toString().trim();
        String questionD = editQuestionD.getText().toString().trim();
        String analysis = editCorrectAndAnalysis.getText().toString().trim();
        String score = editScore.getText().toString().trim();
        if (TextUtils.isEmpty(questionName) || TextUtils.isEmpty(questionA)
                || TextUtils.isEmpty(questionB) || TextUtils.isEmpty(questionC)
                || TextUtils.isEmpty(questionD) || TextUtils.isEmpty(analysis)
                || TextUtils.isEmpty(score)) {
            DisPlay("请把试题填写完整。");
            return;
        }

        QuestionsModel model = new QuestionsModel();
        model.questionName = questionName;
        model.questionA = questionA;
        model.questionB = questionB;
        model.questionC = questionC;
        model.questionD = questionD;
        model.correct = current.correct;
        model.analysis = analysis;
        model.paperOutline = paperOutline;
        model.teacherName = teacherName;

        try {
            model.score = Integer.parseInt(score);
        } catch (Exception e) {
            DisPlay("请确定分数的格式");
        }
        DBUtil.addQuestions(model);
        DisPlay("试题添加成功");
        this.setResult(IDS.RESULTCODE_ADDQUESTION);
        this.finish();

    }

    @OnClick({R.id.tv_questionA, R.id.tv_questionB, R.id.tv_questionC, R.id.tv_questionD})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_questionA:
                previous = current;
                previous.changeToUnPressed();
                current = optionA;
                current.changeToOnPressed();
                break;
            case R.id.tv_questionB:
                previous = current;
                previous.changeToUnPressed();
                current = optionB;
                current.changeToOnPressed();
                break;
            case R.id.tv_questionC:
                previous = current;
                previous.changeToUnPressed();
                current = optionC;
                current.changeToOnPressed();
                break;
            case R.id.tv_questionD:
                previous = current;
                previous.changeToUnPressed();
                current = optionD;
                current.changeToOnPressed();
                break;
        }
    }
}
