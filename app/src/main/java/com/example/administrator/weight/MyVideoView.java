package com.example.administrator.weight;

import android.content.Context;
import android.util.AttributeSet;

import com.example.administrator.until.NewsUntil;

import io.vov.vitamio.widget.VideoView;

/**
 * @author: kira
 * @date: 2017/5/10.
 */

public class MyVideoView extends VideoView {
    private Context mcontext;

    public MyVideoView(Context context) {
        super(context);
        mcontext = context;

    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;

    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mcontext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (width != 0 && height != 0) {
//            setMeasuredDimension(width, height);
//        } else {
//            setMeasuredDimension(512, 288);
//        }
        setMeasuredDimension(widthMeasureSpec, NewsUntil.dp2px(mcontext, 200));
    }


}
