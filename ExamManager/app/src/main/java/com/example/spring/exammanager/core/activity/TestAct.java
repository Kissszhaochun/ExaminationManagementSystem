package com.example.spring.exammanager.core.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.ui.widget.BloodSugarTableArc;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestAct extends Activity {

    @Bind(R.id.bst)
    BloodSugarTableArc bst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        bst.start(0.56f);
    }
}
