package com.example.spring.exammanager.ui.widget.PopupWindw;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;

/**
 *
 */
public abstract class BasePopupWindow extends PopupWindow {

    private View mContentView;

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        mContentView = contentView;
        setFocusable(true);
        ColorDrawable cd = new ColorDrawable(00000000);
        setBackgroundDrawable(cd);
        setOutsideTouchable(true);
        setTouchable(true);
        initView();
        initEvents();
        init();
    }

    public abstract void initView();

    public abstract void initEvents();

    public abstract void init();

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }

}
