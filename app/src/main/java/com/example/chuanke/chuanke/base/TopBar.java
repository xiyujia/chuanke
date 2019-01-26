package com.example.chuanke.chuanke.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;

/**

* 作者：张恺

* 时间：2019/1/26

* 类描述：TopBar,Activity顶部视图

*/


public class TopBar extends LinearLayout {
    private Context context;
    private TextView tv_title;
    private ImageView iv_back;
    private View line;

    public TopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setWillNotDraw(false);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        layoutInflater.inflate(R.layout.topbar, this);
        initView();
        initAttrs(attrs);
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.TopBar_Text) {
                    setText(a.getString(attr));
                } else if (attr == R.styleable.TopBar_backVisibility) {
                    setVisibility(iv_back, a.getInt(attr, 0));
                } else if (attr == R.styleable.TopBar_titleVisibility) {
                    setVisibility(tv_title, a.getInt(attr, 0));
                } else if (attr == R.styleable.TopBar_lineVisibility) {
                    setVisibility(line, a.getInt(attr, 0));
                }
            }
        } finally {
            if (null != a) {
                a.recycle();
            }
        }
    }
        private void initView(){
            tv_title = findViewById(R.id.tv_title);
            iv_back = findViewById(R.id.iv_back);
            line = findViewById(R.id.line);
        }


    public void setText(CharSequence text) {
        tv_title.setText(text);
    }

    private void setVisibility(View view, int visibility) {
        switch (visibility) {
            case 0:
                view.setVisibility(View.VISIBLE);
                break;
            case 1:
                view.setVisibility(View.INVISIBLE);
                break;
            case 2:
                view.setVisibility(View.GONE);
                break;
        }
    }


}