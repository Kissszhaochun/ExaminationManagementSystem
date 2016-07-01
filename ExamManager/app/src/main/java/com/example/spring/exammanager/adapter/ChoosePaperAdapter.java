package com.example.spring.exammanager.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.TestPaperModel;

import java.util.List;

/**
 * 选择试卷
 */
public class ChoosePaperAdapter extends CommonAdapter<TestPaperModel> {
    TextView tvFirstChar, tvPaperOutline, tvQuestionsCount, tvSumOfScore, tvNamePerson;

    public ChoosePaperAdapter(Context ctx, List<TestPaperModel> lists, int layoutId) {
        super(ctx, lists, layoutId);
    }

    @Override
    protected void convert(ViewHolder vh, TestPaperModel item) {
        tvFirstChar = vh.getView(R.id.tv_first_char);
        tvPaperOutline = vh.getView(R.id.tv_paper_outline);
        tvQuestionsCount = vh.getView(R.id.tv_questions_count);
        tvSumOfScore = vh.getView(R.id.tv_sum_of_score);
        tvNamePerson = vh.getView(R.id.tv_name_person);

        tvFirstChar.setText(item.testPaperOutline.substring(0, 1));
        tvPaperOutline.setText(item.testPaperOutline);
        tvQuestionsCount.setText(item.questionsCount + "道题");
        tvSumOfScore.setText("总分-" + item.questionScoreSum);
        tvNamePerson.setText("命题人:" + item.userNameOfTeacher);
    }
}
