package com.example.spring.exammanager.ui.widget;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.spring.exammanager.R;
import com.example.spring.exammanager.utils.CommonUtil;

public class BloodSugarTableArc extends View {
    Context context;
    RectF rectF;//外接圆
    Paint textPaint, arcPaint, arcPaint2;
    int border;//饼状图的边长
    float strokeWidth = CommonUtil.dip2px(8f);
    float textsize = CommonUtil.sp2px(17f);
    String modalityText = "99%";//根据不同情况居中
    float sweepAngle;
    String sweepText;

    int arcColor;


    public BloodSugarTableArc(Context context) {
        super(context);
    }

    public BloodSugarTableArc(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        arcColor = ta.getResourceId(R.styleable.CircleProgress_CircleProgress_arcColor, getResources().getColor(R.color.background_blue));
        textsize = ta.getDimension(R.styleable.CircleProgress_CircleProgress_centerTextSize, 17);
        strokeWidth = ta.getDimension(R.styleable.CircleProgress_CircleProgress_arcStrokeWidth, 8);
        ta.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    //new paint
    void init() {
        textPaint = new Paint();
        textPaint.setTextSize(textsize);
        textPaint.setAntiAlias(true);

        textPaint.setColor(getResources().getColor(R.color.text_black));
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        arcPaint.setColor(getResources().getColor(R.color.arc_grey));

        arcPaint2 = new Paint();
        arcPaint2.setAntiAlias(true);
        arcPaint2.setStyle(Paint.Style.STROKE);
        arcPaint2.setStrokeCap(Paint.Cap.ROUND);
        arcPaint2.setStrokeWidth(strokeWidth);
        arcPaint2.setColor(getResources().getColor(arcColor));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        border = getHeight();
        rectF = new RectF((float) (border * 0.1), (float) (border * 0.1),
                (float) (border * 0.9),
                (float) (border * 0.9));
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        int textX = (int) ((((float) (border * 0.1) + (float) (border * 0.9)) / 2) - (textPaint.measureText(modalityText) / 2));
        int textY = (int) ((((float) (border * 0.1) + (float) (border * 0.9)) / 2) - (Math.ceil(fm.descent + fm.ascent) / 2));
        canvas.drawText(sweepText + "%", textX, textY, textPaint);
        canvas.drawArc(rectF, 0f, 360f, false, arcPaint);
        canvas.drawArc(rectF, 270f, sweepAngle, false, arcPaint2);
    }

    public void start(float target) {
        int tempTarget = (int) (target * 100);
        if (tempTarget == 0) {
            modalityText = "0%";
            sweepAngle = tempTarget;
            sweepText = String.valueOf(tempTarget);
            return;
        } else if (tempTarget < 10) {
            modalityText = "0%";
        } else if (tempTarget == 100) {
            modalityText = "100%";
        } else {
            modalityText = "99%";
        }
        ValueAnimator animator = ValueAnimator.ofInt(0, tempTarget);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                sweepText = String.valueOf(val);
                sweepAngle = 360f * (Float.valueOf(val) / 100f);
                postInvalidate();
            }
        });
        animator.start();
    }
}
