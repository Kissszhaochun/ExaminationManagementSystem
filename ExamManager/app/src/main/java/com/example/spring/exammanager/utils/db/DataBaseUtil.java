package com.example.spring.exammanager.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.spring.exammanager.model.DonePaperModel;
import com.example.spring.exammanager.model.QuestionsModel;
import com.example.spring.exammanager.model.TestPaperModel;
import com.example.spring.exammanager.model.UserModel;
import com.example.spring.exammanager.utils.IDS;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作工具类
 */
public class DataBaseUtil implements IDataBase {
    //以下为用户表的表名和列名
    public static final String T_USER = "t_user";
    public static final String USER_JSON = "user_json";
    public static final String USER_NAME = "user_name";
    //以下为试卷表的表名和列名
    public static final String T_PAPER = "t_paper";
    public static final String PAPER_OUTLINE = "paper_outline";
    public static final String PAPER_JSON = "paper_json";
    //以下为试题表的表名和列名
    public static final String T_QUESTION = "t_question";
    public static final String QUESTION_NAME = "question_name";
    public static final String QUESTION_JSON = "question_json";
    public static Gson gson = new Gson();
    //以下为考试后的试卷表的表名和列名
    public static final String T_DONE_PAPER = "t_done_paper";
    public static final String STUDENT_NAME = "student_name";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String DONE_PAPER_OUTLINE = "done_paper_outline";
    public static final String SCORE_SUM = "score_sum";
    public static final String TEST_TIME = "test_time";
    //一下为考试后的试题表的表名和列名 这里将引用上表中的 STUDENT_NAME and DONE_PAPER_OUTLINE
    public static final String T_DONE_QUESTIONS = "t_done_questions";
    public static final String DONE_QUESTION_NAME = "done_question_name";
    public static final String CORRECT = "correct";
    public static final String ANSWER = "answer";
    public static final String SCORE = "score";

    public static Gson getGsonInstance() {
        return gson;
    }

    public DataBaseHelper helper;

    public DataBaseUtil(Context context) {
        helper = new DataBaseHelper(context);
    }


    //登录
    @Override
    public int login(String userName, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
        UserModel userModel = new UserModel();
        Cursor cursor = db.rawQuery("select * from " + T_USER + " where user_name=? ", new String[]{userName});
        if (cursor.moveToNext()) {
            userModel.userName = cursor.getString(cursor.getColumnIndex(USER_NAME));
            userModel.userJson = cursor.getString(cursor.getColumnIndex(USER_JSON));
            userModel = gson.fromJson(userModel.userJson, UserModel.class);
            if (password.equals(userModel.passwrod)) {
                cursor.close();
                db.close();
                return userModel.userType;
            } else {
                cursor.close();
                db.close();
                return IDS.FAILURE;
            }

        } else {
            cursor.close();
            db.close();
            return IDS.INEXISTENCE;
        }
    }

    //注册
    @Override
    public int register(UserModel userModel) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (isExit(userModel.userName)) {
            return IDS.ALREADYEXIT;
        }
        userModel.userJson = gson.toJson(userModel);
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, userModel.userName);
        cv.put(USER_JSON, userModel.userJson);
        db.insert(T_USER, null, cv);
        cv.clear();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return IDS.REGISTER_SUCCESS;
    }

    //用户是否存在
    @Override
    public boolean isExit(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_USER + " where " + USER_NAME + "=?", new String[]{name});
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    //获取所有试卷
    @Override
    public List<TestPaperModel> getAllPaper(String teacherName) {
        List<TestPaperModel> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_PAPER + " where " + USER_NAME + "=?", new String[]{teacherName});
        while (cursor.moveToNext()) {
            TestPaperModel model = new TestPaperModel();
            model = gson.fromJson(cursor.getString(cursor.getColumnIndex(PAPER_JSON)), TestPaperModel.class);
            list.add(model);
        }
        db.close();
        return list;
    }


    //添加试卷
    @Override
    public void addPaper(TestPaperModel model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        model.paperJson = gson.toJson(model);
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(PAPER_OUTLINE, model.testPaperOutline);
        cv.put(USER_NAME, model.userNameOfTeacher);
        cv.put(PAPER_JSON, model.paperJson);
        db.insert(T_PAPER, null, cv);
        cv.clear();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }


    //获取试题的数目
    @Override
    public int getQuestionCount(String paperOutline) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from " + T_QUESTION + " where " + PAPER_OUTLINE + "=?", new String[]{paperOutline});
        cursor.moveToNext();
        db.close();
        return cursor.getInt(0);
    }

    //获取试卷中试题的总分数
    @Override
    public int getQuestionSumScore(String paperOutline) {
        int sum = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_QUESTION + " where " + PAPER_OUTLINE + "=?", new String[]{paperOutline});
        while (cursor.moveToNext()) {
            QuestionsModel model = new QuestionsModel();
            model.questionsJson = cursor.getString(cursor.getColumnIndex(QUESTION_JSON));
            model = gson.fromJson(model.questionsJson, QuestionsModel.class);
            sum = sum + model.score;
        }
        db.close();
        return sum;
    }

    //根据用户名获取全部的试题
    @Override
    public List<QuestionsModel> getAllQuestionsOfTeacherName(String teacherName) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<QuestionsModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + T_QUESTION + " where " + USER_NAME + "=?", new String[]{teacherName});
        while (cursor.moveToNext()) {
            QuestionsModel model = new QuestionsModel();
            model.questionsJson = cursor.getString(cursor.getColumnIndex(QUESTION_JSON));
            model = gson.fromJson(model.questionsJson, QuestionsModel.class);
            list.add(model);
        }
        return list;
    }

    //根据试题大纲获取到大纲下的试题
    @Override
    public List<QuestionsModel> getAllQuestionsOfOutline(String paperOutline) {
        List<QuestionsModel> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_QUESTION + " where " + PAPER_OUTLINE + "=?", new String[]{paperOutline});
        while (cursor.moveToNext()) {
            QuestionsModel model = new QuestionsModel();
            model.questionsJson = cursor.getString(cursor.getColumnIndex(QUESTION_JSON));
            model = gson.fromJson(model.questionsJson, QuestionsModel.class);
            list.add(model);
        }
        db.close();
        return list;
    }

    //添加试题
    @Override
    public void addQuestions(QuestionsModel model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        model.questionsJson = gson.toJson(model);
        ContentValues cv = new ContentValues();
        cv.put(QUESTION_NAME, model.questionName);
        cv.put(PAPER_OUTLINE, model.paperOutline);
        cv.put(USER_NAME, model.teacherName);
        cv.put(QUESTION_JSON, model.questionsJson);
        db.insert(T_QUESTION, null, cv);
        cv.clear();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    @Override
    public boolean isExitOfPaper(TestPaperModel model) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_PAPER + " where " + PAPER_OUTLINE + "=?", new String[]{model.testPaperOutline});
        if (cursor.moveToNext()) {
            updateExitPaper(model);
            return true;
        }
        addPaper(model);
        return false;
    }

    //添加默认试题 存在则不存在
    @Override
    public void addDefultPaper(TestPaperModel model) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_PAPER + " where " + PAPER_OUTLINE + "=?", new String[]{model.testPaperOutline});
        if (cursor.moveToNext()) {
            return;
        }
        addPaper(model);
    }

    @Override
    public void updateExitPaper(TestPaperModel model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        model.paperJson = gson.toJson(model);
        db.execSQL("update " + T_PAPER + " set " + PAPER_JSON + "=? where " + PAPER_OUTLINE + "=?", new Object[]{model.paperJson, model.testPaperOutline});
        db.close();
    }

    //获取到所有的试卷
    @Override
    public List<TestPaperModel> getAllPaper() {
        List<TestPaperModel> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_PAPER, null);
        while (cursor.moveToNext()) {
            TestPaperModel model = gson.fromJson(cursor.getString(cursor.getColumnIndex(PAPER_JSON)), TestPaperModel.class);
            list.add(model);
        }
        return list;
    }

    //添加答过的试卷 用于成绩查询
    @Override
    public void addDonePaper(DonePaperModel model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(DONE_PAPER_OUTLINE, model.outLine);
        cv.put(STUDENT_NAME, model.studentName);
        cv.put(SCORE_SUM, model.scoreSum);
        cv.put(TEACHER_NAME, model.teacherName);
        cv.put(TEST_TIME, model.testTime);
        db.insert(T_DONE_PAPER, null, cv);
        cv.clear();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //获取全部做过的试卷
    @Override
    public List<DonePaperModel> getAllDonePaperByStudentName(String studentName) {
        List<DonePaperModel> listForDonePaper = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_DONE_PAPER + " where " + STUDENT_NAME + "=?", new String[]{studentName});
        while (cursor.moveToNext()) {
            DonePaperModel model = new DonePaperModel();
            model.outLine = cursor.getString(cursor.getColumnIndex(DONE_PAPER_OUTLINE));
            model.studentName = cursor.getString(cursor.getColumnIndex(STUDENT_NAME));
            model.teacherName = cursor.getString(cursor.getColumnIndex(TEACHER_NAME));
            model.scoreSum = cursor.getInt(cursor.getColumnIndex(SCORE_SUM));
            model.testTime = cursor.getString(cursor.getColumnIndex(TEST_TIME));
            listForDonePaper.add(model);
        }
        return listForDonePaper;
    }

    @Override
    public List<DonePaperModel> getAllDonePaperByTeacherName(String teacherName) {
        List<DonePaperModel> listForDonePaper = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + T_DONE_PAPER + " where " + TEACHER_NAME + "=?", new String[]{teacherName});
        while (cursor.moveToNext()) {
            DonePaperModel model = new DonePaperModel();
            model.outLine = cursor.getString(cursor.getColumnIndex(DONE_PAPER_OUTLINE));
            model.studentName = cursor.getString(cursor.getColumnIndex(STUDENT_NAME));
            model.teacherName = cursor.getString(cursor.getColumnIndex(TEACHER_NAME));
            model.scoreSum = cursor.getInt(cursor.getColumnIndex(SCORE_SUM));
            model.testTime = cursor.getString(cursor.getColumnIndex(TEST_TIME));
            listForDonePaper.add(model);
        }
        return listForDonePaper;
    }


}
