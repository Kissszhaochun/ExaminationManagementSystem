package com.example.spring.exammanager.core.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.UserModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.ui.widget.PopupWindw.ChoosUserType;
import com.example.spring.exammanager.utils.IDS;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.title_registe)
    CommonTitile titleRegiste;
    @Bind(R.id.tv_registed_user)
    TextView tvRegistedUser;
    @Bind(R.id.edit_regist_user_name)
    EditText editRegistUserName;
    @Bind(R.id.edit_regist_user_password)
    EditText editRegistUserPassword;
    @Bind(R.id.edit_redo_password)
    EditText editRedoPassword;

    //    DataBaseUtil DBUtil;
    int userType;
    @Bind(R.id.tv_choose_user_type)
    TextView tvChooseUserType;

    ChoosUserType chooseUserTypeWindow;
    @Bind(R.id.edit_teacher_credentials)
    EditText editTeacherCredentials;
    @Bind(R.id.edit_student_number)
    EditText editStudentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
//        DBUtil = new DataBaseUtil(this);
        titleRegiste.setTitle("注册新用户");
        chooseUserTypeWindow = new ChoosUserType(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        chooseUserTypeWindow.setOnChooseUserTypeListener(new ChoosUserType.OnChooseUserTypeListener() {
            @Override
            public void clickTeacher() {
                chooseUserTypeWindow.dismiss();
                tvChooseUserType.setText("教师");
                userType = IDS.TEACHER;
                editStudentNumber.setVisibility(View.GONE);
                editTeacherCredentials.setVisibility(View.VISIBLE);
            }

            @Override
            public void clickStudent() {
                chooseUserTypeWindow.dismiss();
                tvChooseUserType.setText("学生");
                userType = IDS.STUDENT;
                editTeacherCredentials.setVisibility(View.GONE);
                editStudentNumber.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.tv_choose_user_type)
    void chooseUserType() {
        chooseUserTypeWindow.showAsDropDown(tvChooseUserType, 0, 0);
    }

    @OnClick(R.id.tv_registed_user)
    void registeNewUser() {
        String userName = editRegistUserName.getText().toString().trim();
        String password = editRegistUserPassword.getText().toString().trim();
        String rePassword = editRedoPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            DisPlay("用户名或密码不可为空！");
            return;
        }
        if (password.length() < 3 || userName.length() > 12) {
            DisPlay("用户名必须在3至12位之间");
            return;
        }
        if (!rePassword.equals(password)) {
            DisPlay("两次输入密码不一致！");
            return;
        }
        if (TextUtils.isEmpty(tvChooseUserType.getText().toString().trim())) {
            DisPlay("请选择用户类型！");
            return;
        }
        UserModel userModel = new UserModel();
        userModel.userName = userName;
        userModel.passwrod = password;
        userModel.userType = userType;
        int status = DBUtil.register(userModel);
        Message msg = handler.obtainMessage();
        msg.what = IDS.DELAYED;
        msg.arg1 = status;
        waitDialogShow("正在审核...");
        handler.sendMessageDelayed(msg, 1300);
//        switch (status) {
//            case IDS.ALREADYEXIT:
//                DisPlay("用户名已存在,请更换用户名注册或直接登录");
//                return;
//            case IDS.REGISTER_SUCCESS:
//                openActivity(LoginActivity.class);
//                this.finish();
//                break;
//        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int status = msg.arg1;
            if (msg.what == IDS.DELAYED) {
                switch (status) {
                    case IDS.ALREADYEXIT:
                        DisPlay("用户名已存在,请更换用户名注册或直接登录");
                        return;
                    case IDS.REGISTER_SUCCESS:
                        openActivity(LoginActivity.class);
                        RegisterActivity.this.finish();
                        break;
                }
            }
        }
    };

}
