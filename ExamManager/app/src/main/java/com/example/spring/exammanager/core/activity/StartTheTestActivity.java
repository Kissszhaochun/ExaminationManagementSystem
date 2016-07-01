package com.example.spring.exammanager.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.DonePaperModel;
import com.example.spring.exammanager.model.DoneQuestionModel;
import com.example.spring.exammanager.model.QuestionsModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.utils.IDS;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartTheTestActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.title_start_the_test)
    CommonTitile titleStartTheTest;
    @Bind(R.id.tv_question_name)
    TextView tvQuestionName;
    @Bind(R.id.rb_question_a)
    RadioButton rbQuestionA;
    @Bind(R.id.rb_question_b)
    RadioButton rbQuestionB;
    @Bind(R.id.rb_question_c)
    RadioButton rbQuestionC;
    @Bind(R.id.rb_question_d)
    RadioButton rbQuestionD;
    @Bind(R.id.rg_option)
    RadioGroup rgOption;
    @Bind(R.id.tv_next_question)
    TextView tvNextQuestion;
    List<QuestionsModel> listOfQuestions;
    static String outline;
    static String studentName;
    QuestionsModel current;
    DonePaperModel donePaperModel;
    DoneQuestionModel doneQuestion;
    List<DoneQuestionModel> listOfDoneQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_the_test);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        listOfQuestions = new ArrayList<>();
        donePaperModel = new DonePaperModel();
        listOfDoneQuestions = new ArrayList<>();
        titleStartTheTest.setTitle("正在考试");
        outline = getIntent().getStringExtra("outline");
        studentName = getIntent().getStringExtra("studentName");
        rgOption.setOnCheckedChangeListener(this);
        listOfQuestions = DBUtil.getAllQuestionsOfOutline(outline);
        if (listOfQuestions.size() == 1) {
            tvNextQuestion.setText("交卷");
        }
        for (int i = 0; i < listOfQuestions.size(); i++) {
            listOfQuestions.get(i).number = i + 1;
        }
        current = listOfQuestions.get(0);
        initCurrentQuestion(current);
    }


    private void initCurrentQuestion(QuestionsModel model) {
        rgOption.clearCheck();
        doneQuestion = new DoneQuestionModel();
        tvQuestionName.setText(model.number + ". " + model.questionName);
        rbQuestionA.setText("A." + model.questionA);
        rbQuestionB.setText("B." + model.questionB);
        rbQuestionC.setText("C." + model.questionC);
        rbQuestionD.setText("D." + model.questionD);
    }

    @OnClick(R.id.tv_next_question)
    void nextQuestion() {
        if (current.number == listOfQuestions.size() - 1) {
            tvNextQuestion.setText("交卷");
        }
        if (null == doneQuestion.answer) {
            doneQuestion.answer = "空";
        }
        doneQuestion.questionName = current.questionName;
        doneQuestion.correct = current.correct;
        doneQuestion.outLine = outline;
        doneQuestion.studentName = studentName;
        doneQuestion.score = current.score;
        listOfDoneQuestions.add(doneQuestion);
        if (current.number == listOfQuestions.size()) {
            int sumOfScore = 0;
            for (DoneQuestionModel model : listOfDoneQuestions) {
                if (model.answer.equals(model.correct)) {
                    sumOfScore += model.score;
                }
            }
            donePaperModel.scoreSum = sumOfScore;
            donePaperModel.teacherName = current.teacherName;
            donePaperModel.studentName = studentName;
            donePaperModel.outLine = current.paperOutline;
            donePaperModel.testTime = System.currentTimeMillis() / 1000 + "";

            DBUtil.addDonePaper(donePaperModel);
            sendBroadcast(new Intent(IDS.B_CHOOSE_PAPER));
            this.finish();
            return;
        }
        current = listOfQuestions.get(current.number);
        initCurrentQuestion(current);


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_question_a:
                doneQuestion.answer = IDS.A;
                break;
            case R.id.rb_question_b:
                doneQuestion.answer = IDS.B;
                break;
            case R.id.rb_question_c:
                doneQuestion.answer = IDS.C;
                break;
            case R.id.rb_question_d:
                doneQuestion.answer = IDS.D;
                break;

        }
    }
}
