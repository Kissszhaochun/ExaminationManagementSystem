package com.example.spring.exammanager.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.adapter.PaperAdapter;
import com.example.spring.exammanager.model.TestPaperModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.utils.IDS;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//试卷管理

public class TestPaperManagerActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.title_testmanager_pager)
    CommonTitile titleTestmanagerPager;
    @Bind(R.id.lv_p_manager)
    ListView lvPManager;
    @Bind(android.R.id.empty)
    TextView empty;
    @Bind(R.id.tv_add_paper)
    TextView tvAddPaper;
    static String teacherName;
    List<TestPaperModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_paper);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        teacherName = getIntent().getExtras().getString("userName");
        titleTestmanagerPager.setTitle("试卷列表");
        lvPManager.setOnItemClickListener(this);
        initLV();

    }


    @OnClick(R.id.tv_add_paper)
    void addPaper() {
        Intent i = new Intent(this, AddPaperActivity.class);
        i.putExtra("teacherName", teacherName);
        startActivityForResult(i, IDS.REQUESTCODE_ADDPAPER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IDS.REQUESTCODE_ADDPAPER) {
            if (resultCode == IDS.RESULTCODE_ADDPAPER) {
                initLV();
            }
        }
    }

    private void initLV() {
        list = DBUtil.getAllPaper(teacherName);
        PaperAdapter adapter = new PaperAdapter(this, list, R.layout.item_testpager);
        lvPManager.setAdapter(adapter);
        lvPManager.setEmptyView(empty);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AddPaperActivity.class);
        intent.putExtra("paperOutline", list.get(position).testPaperOutline);
        intent.putExtra("teacherName", list.get(position).userNameOfTeacher);
        startActivityForResult(intent, IDS.REQUESTCODE_ADDPAPER);
    }
}
