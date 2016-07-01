package com.example.spring.exammanager.ui.widget.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.spring.exammanager.R;

/**
 */
public class SwitchButton extends View {

    private Bitmap switchBackgroundBitmap;//背景图
    private Bitmap switchForegroundBitmap;//前景图
    private Paint paint;
    private boolean isSwitchState = true;//开关状态
    private boolean isTouchState = false;//触摸状态
    private float currentPosition;
    private int maxPosition;
    private OnSwitchStateUpdateListener l;

    public SwitchButton(Context context) {
        super(context);
        initView();
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomSwitchView);
        int back = ta.getResourceId(R.styleable.CustomSwitchView_Switch_background, -1);
        int fore = ta.getResourceId(R.styleable.CustomSwitchView_Switch_foreground, -1);
        isSwitchState = ta.getBoolean(R.styleable.CustomSwitchView_Switch_state, false);
        ta.recycle();
//        String xmlns = "http://schemas.android.com/apk/res-auto";
//        int back = attrs.getAttributeResourceValue(xmlns, "Switch_background", -1);
//        int fore = attrs.getAttributeResourceValue(xmlns, "Switch_foreground", -1);
//        isSwitchState = attrs.getAttributeBooleanValue(xmlns, "Switch_state", false);
        setBackgroundPic(back);
        setForegraoundPic(fore);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //设置背景图
    public void setBackgroundPic(int swichBackground) {
        switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), swichBackground);
    }

    //设置前景图
    public void setForegraoundPic(int swichForeground) {
        switchForegroundBitmap = BitmapFactory.decodeResource(getResources(), swichForeground);
    }

    private void initView() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackgroundBitmap.getWidth(), switchBackgroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(switchBackgroundBitmap, 0, 0, paint);
        if (isTouchState) {
            float movePosition = currentPosition - switchForegroundBitmap.getWidth() / 2.0f;
            maxPosition = switchBackgroundBitmap.getWidth() - switchForegroundBitmap.getWidth();
            //限定开关范围
            if (movePosition < 0) {
                movePosition = 0;
            } else if (movePosition > maxPosition) {
                movePosition = maxPosition;
            }
            canvas.drawBitmap(switchForegroundBitmap, movePosition, 0, paint);
        } else {
            if (isSwitchState) {
                maxPosition = switchBackgroundBitmap.getWidth() - switchForegroundBitmap.getWidth();
                canvas.drawBitmap(switchForegroundBitmap, maxPosition, 0, paint);
            } else {
                canvas.drawBitmap(switchForegroundBitmap, 0, 0, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchState = true;
                currentPosition = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchState = false;
                currentPosition = event.getX();
                float centerPosition = switchBackgroundBitmap.getWidth() / 2.0f;
                boolean currentState = currentPosition > centerPosition;
                if (currentState != isSwitchState && this.l != null) {
                    this.l.onStateUpdate(currentState);
                }
                isSwitchState = currentState;
                break;
        }
        invalidate();//重新调用onDraw方法，不断重绘界面
        return true;
    }

    public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener l) {
        this.l = l;
    }

    public interface OnSwitchStateUpdateListener {
        void onStateUpdate(boolean state);
    }


}
