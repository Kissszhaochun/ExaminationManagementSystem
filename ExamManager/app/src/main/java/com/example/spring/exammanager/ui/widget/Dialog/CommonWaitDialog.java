package com.example.spring.exammanager.ui.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spring.exammanager.R;
import com.tt.whorlviewlibrary.WhorlView;

/**
 * 通用等待对话框
 */
public class CommonWaitDialog extends Dialog {


    public CommonWaitDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        Context context;
        String content;

        public Builder(Context context, String content) {
            this.content = content;
            this.context = context;
        }

        public CommonWaitDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CommonWaitDialog dialog = new CommonWaitDialog(context, R.style.WaitDialog);
            View P = inflater.inflate(R.layout.layout_wait_dialog, null);
            TextView loadText = (TextView) P.findViewById(R.id.tv_load_text);
            WhorlView whorlView = (WhorlView) P.findViewById(R.id.whorl);
            loadText.setText(content);
            whorlView.start();
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.addContentView(P, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(P);
            return dialog;
        }
    }

}
