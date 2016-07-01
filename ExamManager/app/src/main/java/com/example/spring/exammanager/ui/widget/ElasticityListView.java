package com.example.spring.exammanager.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/6/11.
 */

public class ElasticityListView extends ListView {

    public ElasticityListView(Context context) {
        super(context);
    }

    public ElasticityListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, 50, isTouchEvent);
    }
}
