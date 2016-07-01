package com.example.spring.exammanager.core.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.adapter.ChoosePaperAdapter;
import com.example.spring.exammanager.model.TestPaperModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.utils.IDS;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChoosePaperActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.title_choose_paper)
    CommonTitile titleChoosePaper;
    @Bind(R.id.lv_choose_paper)
    ListView lvChoosePaper;
    @Bind(android.R.id.empty)
    TextView empty;
    List<TestPaperModel> list;
    static String studentName;
    //当考试完成直接销毁选择试卷界面
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_paper_);
        ButterKnife.bind(this);
        initView();
        registerReceiver(broadcastReceiver, new IntentFilter(IDS.B_CHOOSE_PAPER));
    }

    @Override
    protected void initView() {
        studentName = getIntent().getStringExtra("studentName");
        list = new ArrayList<>();
        titleChoosePaper.setTitle("选择试卷");
        lvChoosePaper.setOnItemClickListener(this);
        initLV();
    }

    private void initLV() {
        list = DBUtil.getAllPaper();
        ChoosePaperAdapter adapter = new ChoosePaperAdapter(this, list, R.layout.item_choose_paper);
        lvChoosePaper.setAdapter(adapter);
        lvChoosePaper.setEmptyView(empty);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int p = position;
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage("确定考试吗？").setPositiveButton("取消", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ChoosePaperActivity.this, StartTheTestActivity.class);
                intent.putExtra("outline", list.get(p).testPaperOutline);
                intent.putExtra("studentName", studentName);
                dialog.dismiss();
                startActivity(intent);
            }
        }).create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
