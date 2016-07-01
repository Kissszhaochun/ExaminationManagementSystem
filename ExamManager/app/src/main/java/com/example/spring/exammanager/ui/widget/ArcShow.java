package com.example.spring.exammanager.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.example.spring.exammanager.R;

/**
 * 弧线展示
 */
public class ArcShow extends View {

    int mCircleXY;
    float mRadius;
    RectF mArcRectF;
    Paint mCirclePaint;
    Paint mArcPaint;
    String mText;
    Paint mTextPaint;

    public ArcShow(Context context) {
        super(context);
        int length = 500;
        mCircleXY = length / 2;
        mCirclePaint = new Paint();
        mCirclePaint.setColor(getResources().getColor(R.color.blue));
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);                       //设置画笔为无锯齿
        mArcPaint.setColor(Color.GRAY);
        mArcPaint.setStrokeWidth((float) 50.0);              //线宽
        mArcPaint.setStyle(Paint.Style.STROKE);
        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(android.R.color.white));
        mText = "端午节快乐";
        mRadius = (float) (length * 0.5 / 2);
        mArcRectF = new RectF((float) (length * 0.1), (float) (length * 0.1),
                (float) (length * 0.9),
                (float) (length * 0.9));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        //绘制弧线
        canvas.drawArc(mArcRectF, 90, 180, false, mArcPaint);
        //绘制文字
        canvas.drawText(mText, 0, mText.length(), mCircleXY, mCircleXY, mTextPaint);
    }
}
