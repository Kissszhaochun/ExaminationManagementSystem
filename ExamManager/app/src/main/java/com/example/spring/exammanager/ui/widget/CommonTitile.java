package com.example.spring.exammanager.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spring.exammanager.R;

/**
 * 通用的顶部栏
 */
public class CommonTitile extends RelativeLayout {

    public TextView tvTitle;
    public ImageView imgBack;

    public CommonTitile(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_common_titile, this);
        tvTitle = (TextView) this.findViewById(R.id.ct_title_name);
        imgBack = (ImageView) this.findViewById(R.id.ct_back);
        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setTitle(String content) {
        tvTitle.setText(content);
    }

    public void setBackListtener(OnClickListener l) {
        imgBack.setOnClickListener(l);
    }

    public void hideBack() {
        imgBack.setVisibility(View.GONE);
    }
}
