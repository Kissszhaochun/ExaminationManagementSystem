package com.example.spring.exammanager.core.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.model.QuestionsModel;
import com.example.spring.exammanager.model.TestPaperModel;
import com.example.spring.exammanager.ui.widget.CommonTitile;
import com.example.spring.exammanager.utils.IDS;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.title_login)
    CommonTitile titleLogin;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_regist)
    TextView tvRegist;
    @Bind(R.id.edit_login_username)
    EditText editLoginUsername;
    @Bind(R.id.edit_login_password)
    EditText editLoginPassword;
    private String userName;
    String systemDefult="系统默认题";
    String outLine = "JAVA考试选择题";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = null;
            switch (msg.what) {
                case IDS.STUDENT:
                    bundle = new Bundle();
                    bundle.putString("userName", userName);
                    openActivity(MainOfStudentActivity.class, bundle);
                    LoginActivity.this.finish();
                    DisPlay("登录成功");
                    break;
                case IDS.TEACHER:
                    bundle = new Bundle();
                    bundle.putString("userName", userName);
                    openActivity(MainOfTeacherActivity.class, bundle);
                    LoginActivity.this.finish();
                    DisPlay("登录成功");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关于第一次启动，解决从安装管理器进入APP HOME键 再进入重新打开了一个APP
//        if (!this.isTaskRoot()) {
//            Intent mainIntent = getIntent();
//            String action = mainIntent.getAction();
//            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) || action.equals(Intent.ACTION_MAIN)) {
//                finish();
//                return;
//            }
//        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void initView() {
        titleLogin.setTitle("考试管理平台");
        titleLogin.hideBack();

    }

    @OnClick(R.id.tv_regist)
    void register() {
        openActivity(RegisterActivity.class);
        this.finish();
    }

    @OnClick(R.id.tv_login)
    void login() {
        userName = editLoginUsername.getText().toString().trim();
        String password = editLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            DisPlay("用户名和密码不得为空");
            return;
        }
        int status = DBUtil.login(userName, password);

        switch (status) {
            case IDS.STUDENT:
                waitDialogShow("请稍候...");
                handler.sendEmptyMessageDelayed(IDS.STUDENT, 1300);
                return;
            case IDS.TEACHER:
                waitDialogShow("请稍候...");
                addDefultQuestion();
                handler.sendEmptyMessageDelayed(IDS.TEACHER, 1300);
                return;
            case IDS.FAILURE:
                DisPlay("密码错误");
                return;
            case IDS.INEXISTENCE:
                DisPlay("用户不存在");
                return;
        }
    }

    public void addDefultQuestion() {
        QuestionsModel model = new QuestionsModel();
        model.questionName = "符合对象和类关系的是()";
        model.questionA = "人和老虎";
        model.questionB = "书和汽车";
        model.questionC = "楼和土地 ";
        model.questionD = "松树和植物";
        model.correct = IDS.D;
        model.analysis = "";
        model.paperOutline = outLine;
        model.teacherName = systemDefult;
        model.score = 20;
        DBUtil.addQuestions(model);

        model = new QuestionsModel();
        model.questionName = "关于选择结构下列哪个说法正确()";
        model.questionA = "if语句和 else语句必须成对出现";
        model.questionB = "if语句可以没有else语句对应";
        model.questionC = "一个if语句只能有一个else if语句与之对应";
        model.questionD = "else if结构中必须有default语句";
        model.correct = IDS.B;
        model.analysis = "";
        model.paperOutline = outLine;
        model.teacherName = systemDefult;
        model.score = 20;
        DBUtil.addQuestions(model);

        model = new QuestionsModel();
        model.questionName = "while循环和 do…while循环的区别是()";
        model.questionA = "没有区别，这两个结构任何情况下效果一样";
        model.questionB = "while循环比 do…while循环执行效率高";
        model.questionC = "do…while循环执行次数可能为0 ";
        model.questionD = "while循环执行次数可能为0";
        model.correct = IDS.D;
        model.analysis = "";
        model.paperOutline = outLine;
        model.teacherName = systemDefult;
        model.score = 20;
        DBUtil.addQuestions(model);

        model = new QuestionsModel();
        model.questionName = "下列说法哪个正确()";
        model.questionA = "一个程序可以包含多个源文件";
        model.questionB = "一个源文件中只能有一个类";
        model.questionC = "一个源文件中可以有多个公共类 ";
        model.questionD = "一个源文件只能供一个程序使用";
        model.correct = IDS.A;
        model.analysis = "";
        model.paperOutline = outLine;
        model.teacherName = systemDefult;
        model.score = 20;
        DBUtil.addQuestions(model);

        model = new QuestionsModel();
        model.questionName = "下列关于JAVA语言特点的叙述中，错误的是()";
        model.questionA = "Java是面向过程的编程语言";
        model.questionB = "Java支持分布式计算";
        model.questionC = "Java是跨平台的编程语言 ";
        model.questionD = "Java支持多线程";
        model.correct = IDS.A;
        model.analysis = "";
        model.paperOutline = outLine;
        model.teacherName = systemDefult;
        model.score = 20;
        DBUtil.addQuestions(model);

        TestPaperModel modelOfPaper = new TestPaperModel();
        modelOfPaper.userNameOfTeacher =systemDefult;
        modelOfPaper.testPaperOutline = outLine;
        modelOfPaper.date = System.currentTimeMillis() / 1000;
        modelOfPaper.questionScoreSum = 100;
        modelOfPaper.questionsCount = 5;
        DBUtil.addDefultPaper(modelOfPaper);
    }


}
