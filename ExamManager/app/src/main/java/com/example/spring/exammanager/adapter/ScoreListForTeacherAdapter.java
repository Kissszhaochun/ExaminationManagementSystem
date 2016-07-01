package com.example.spring.exammanager.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.DonePaperModel;
import com.example.spring.exammanager.utils.DateUtil;

import java.util.List;

/**
 * 教师端历史考试记录
 */
public class ScoreListForTeacherAdapter extends CommonAdapter<DonePaperModel> {
    TextView tvFirstChar, tvOutline, tvScore, tvStudentName, tvTestTime;

    public ScoreListForTeacherAdapter(Context ctx, List<DonePaperModel> lists, int layoutId) {
        super(ctx, lists, layoutId);
    }

    @Override
    protected void convert(ViewHolder vh, DonePaperModel item) {
        tvFirstChar = vh.getView(R.id.tv_first_char);
        tvOutline = vh.getView(R.id.tv_paper_outline);
        tvScore = vh.getView(R.id.tv_score);
        tvStudentName = vh.getView(R.id.tv_student_name);
        tvTestTime = vh.getView(R.id.tv_test_time);


        tvFirstChar.setText(item.outLine.substring(0, 1));
        tvOutline.setText(item.outLine);
        tvScore.setText(item.scoreSum + "分");
        tvStudentName.setText("考生: [" + item.studentName + "]");
        tvTestTime.setText(DateUtil.getDateString(Long.parseLong(item.testTime)));
    }
}
