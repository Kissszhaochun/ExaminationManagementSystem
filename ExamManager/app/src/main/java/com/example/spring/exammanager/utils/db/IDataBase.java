package com.example.spring.exammanager.utils.db;

import com.example.spring.exammanager.model.DonePaperModel;
import com.example.spring.exammanager.model.QuestionsModel;
import com.example.spring.exammanager.model.TestPaperModel;
import com.example.spring.exammanager.model.UserModel;

import java.util.List;

/**
 * 数据库操作接口
 */
public interface IDataBase {
    int login(String userName, String password);

    int register(UserModel userModel);

    boolean isExit(String name);

    List<TestPaperModel> getAllPaper(String teacherName);

    void addPaper(TestPaperModel model);

    int getQuestionCount(String paperOutline);

    int getQuestionSumScore(String paperOutline);

    List<QuestionsModel> getAllQuestionsOfTeacherName(String teacherName);

    List<QuestionsModel> getAllQuestionsOfOutline(String paperOutline);

    void addQuestions(QuestionsModel model);

    boolean isExitOfPaper(TestPaperModel model);

    void addDefultPaper(TestPaperModel model);

    void updateExitPaper(TestPaperModel model);

    List<TestPaperModel> getAllPaper();

    void addDonePaper(DonePaperModel model);

    List<DonePaperModel> getAllDonePaperByStudentName(String studentName);

    List<DonePaperModel> getAllDonePaperByTeacherName(String teacherName);


}
