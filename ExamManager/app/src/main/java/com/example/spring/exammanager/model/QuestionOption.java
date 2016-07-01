package com.example.spring.exammanager.model;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spring.exammanager.R;

/**
 * 选项选中选中状态
 */
public class QuestionOption {
    LinearLayout layout;
    TextView singleOption;
    public String correct;

    public QuestionOption(LinearLayout layout, TextView singleOption, String correct) {
        this.layout = layout;
        this.singleOption = singleOption;
        this.correct = correct;
    }

    public void changeToUnPressed() {
        this.layout.setBackgroundResource(R.drawable.border_blue);
        this.singleOption.setBackgroundResource(R.color.blue);
    }

    public void changeToOnPressed() {
        this.layout.setBackgroundResource(R.drawable.border_correct);
        this.singleOption.setBackgroundResource(R.color.font_undercard);
    }
}
