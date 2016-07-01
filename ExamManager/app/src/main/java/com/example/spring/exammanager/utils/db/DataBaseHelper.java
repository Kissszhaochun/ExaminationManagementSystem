package com.example.spring.exammanager.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_EXAM = "db_exam";
    public static final String TABLE_USER = "CREATE TABLE " + DataBaseUtil.T_USER +
            "(" + DataBaseUtil.USER_NAME + " TEXT PRIMARY KEY," + DataBaseUtil.USER_JSON + " TEXT)";
    public static final String TABLE_PAPER = "CREATE TABLE " + DataBaseUtil.T_PAPER +
            "(" + DataBaseUtil.PAPER_OUTLINE + " TEXT PRIMARY KEY," + DataBaseUtil.PAPER_JSON + " TEXT," + DataBaseUtil.USER_NAME + " TEXT)";

    public static final String TABLE_QUESTION = "CREATE TABLE " + DataBaseUtil.T_QUESTION +
            "(" + DataBaseUtil.QUESTION_NAME + " TEXT PRIMARY KEY," + DataBaseUtil.PAPER_OUTLINE + " TEXT," + DataBaseUtil.USER_NAME + " TEXT," + DataBaseUtil.QUESTION_JSON + " TEXT)";


    public static final String TABLE_DONE_PAPER = "CREATE TABLE " + DataBaseUtil.T_DONE_PAPER +
            "(" + DataBaseUtil.TEST_TIME + " TEXT PRIMATY KEY," + DataBaseUtil.DONE_PAPER_OUTLINE + " TEXT," + DataBaseUtil.STUDENT_NAME + " TEXT," + DataBaseUtil.TEACHER_NAME + " TEXT," +
            DataBaseUtil.SCORE_SUM + " INTEGER)";
    public static final String TABLE_DONE_QUESTIONS = "CREATE TABLE " + DataBaseUtil.T_DONE_QUESTIONS +
            "(" + DataBaseUtil.DONE_QUESTION_NAME + " TEXT PRIMARY KEY," + DataBaseUtil.DONE_PAPER_OUTLINE + " TEXT," +
            DataBaseUtil.STUDENT_NAME + " TEXT," + DataBaseUtil.CORRECT + " VARCHAR," + DataBaseUtil.ANSWER + " VARCHAR," + DataBaseUtil.SCORE + " INTEGER)";

    public DataBaseHelper(Context context) {
        super(context, DB_EXAM, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_PAPER);
        db.execSQL(TABLE_QUESTION);
        db.execSQL(TABLE_DONE_PAPER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_PAPER);
        db.execSQL(TABLE_QUESTION);
        db.execSQL(TABLE_DONE_PAPER);
    }
}
