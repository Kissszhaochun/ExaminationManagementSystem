package com.example.spring.exammanager.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.QuestionsModel;

import java.util.List;

/**
 * 问题adpter
 */
public class QuestionAdapter extends CommonAdapter<QuestionsModel> {
    TextView tvQuestionName, tvCorrectAndScore;

    public QuestionAdapter(Context ctx, List<QuestionsModel> lists, int layoutId) {
        super(ctx, lists, layoutId);
    }

    @Override
    protected void convert(ViewHolder vh, QuestionsModel item) {
        tvQuestionName = vh.getView(R.id.tv_question_name);
        tvCorrectAndScore = vh.getView(R.id.tv_correct_and_score);
        tvQuestionName.setText(item.questionName);
        tvCorrectAndScore.setText("正确答案:" + item.correct + "   分数" + item.score);
    }
}
