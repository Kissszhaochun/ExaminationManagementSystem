package com.example.spring.exammanager.core.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.adapter.ScoreListAdapter;
import com.example.spring.exammanager.adapter.ScoreListForTeacherAdapter;
import com.example.spring.exammanager.model.DonePaperModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.ui.widget.ElasticityListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScoreSearchActivity extends BaseActivity {

    @Bind(R.id.title_score_search)
    CommonTitile titleScoreSearch;
    @Bind(R.id.lv_score_search)
    ElasticityListView lvScoreSearch;
    @Bind(android.R.id.empty)
    TextView empty;
    static String studentName;
    static String teacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_search);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        List<DonePaperModel> listOfDonePaper = null;

        titleScoreSearch.setTitle("历史成绩查询");
        studentName = getIntent().getStringExtra("studentName");
        teacherName = getIntent().getStringExtra("teacherName");
        if (!TextUtils.isEmpty(studentName)) {
            listOfDonePaper = DBUtil.getAllDonePaperByStudentName(studentName);
            Collections.sort(listOfDonePaper, new Comparator<DonePaperModel>() {
                @Override
                public int compare(DonePaperModel lhs, DonePaperModel rhs) {
                    return (int) (Long.parseLong(rhs.testTime) - Long.parseLong(lhs.testTime));
                }
            });
            ScoreListAdapter adapter = new ScoreListAdapter(this, listOfDonePaper, R.layout.item_score_search);
            lvScoreSearch.setAdapter(adapter);
            empty.setText("暂无成绩（及时测试自己,才知道自己的水平）");
            lvScoreSearch.setEmptyView(empty);
        }
        if (!TextUtils.isEmpty(teacherName)) {
            listOfDonePaper = DBUtil.getAllDonePaperByTeacherName(teacherName);
            Collections.sort(listOfDonePaper, new Comparator<DonePaperModel>() {
                @Override
                public int compare(DonePaperModel lhs, DonePaperModel rhs) {
                    return (int) (Long.parseLong(rhs.testTime) - Long.parseLong(lhs.testTime));
                }
            });
            ScoreListForTeacherAdapter adapter = new ScoreListForTeacherAdapter(this, listOfDonePaper, R.layout.item_score_search_for_teacher);
            lvScoreSearch.setAdapter(adapter);
            empty.setText("暂无成绩");
            lvScoreSearch.setEmptyView(empty);
        }

    }
}
