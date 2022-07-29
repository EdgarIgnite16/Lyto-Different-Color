package com.edgar.lytodifferentcolor.Object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class _ImgCustom extends ImageView {
    public _ImgCustom(Context context) {
        super(context);
    }

    public _ImgCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public _ImgCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int a = this.getMeasuredWidth();
        this.setMeasuredDimension(a, a);
    }
}
