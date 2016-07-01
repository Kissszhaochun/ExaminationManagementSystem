package com.example.spring.exammanager.ui.widget.PopupWindw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.spring.exammanager.R;

/**
 * 选择用户类型popupwindow
 */
public class ChoosUserType extends BasePopupWindow implements View.OnClickListener {
    private TextView tvTeacher, tvStudent;
    private OnChooseUserTypeListener l;

    public ChoosUserType(Context context, int width, int height) {
        super(LayoutInflater.from(context).inflate(R.layout.layout_popupwidow_usertype, null), width, height);
    }

    @Override
    public void initView() {
        tvTeacher = (TextView) findViewById(R.id.tv_teacher);
        tvStudent = (TextView) findViewById(R.id.tv_student);
    }

    @Override
    public void initEvents() {
        tvTeacher.setOnClickListener(this);
        tvStudent.setOnClickListener(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_teacher:
                if (this.l != null) {
                    this.l.clickTeacher();
                }
                break;
            case R.id.tv_student:
                if (this.l != null) {
                    this.l.clickStudent();
                }
                break;
        }
    }


    public void setOnChooseUserTypeListener(OnChooseUserTypeListener l) {
        this.l = l;
    }

    public interface OnChooseUserTypeListener {
        void clickTeacher();

        void clickStudent();
    }

}
