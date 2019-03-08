package com.lwj.luwenjie0308.view.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Auther:
 * @Date: 2019/3/8 15:53
 * @Description:
 */
public class SouLayout extends ViewGroup {
    public SouLayout(Context context) {
        super(context);
    }

    public SouLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int heghtmode = MeasureSpec.getMode(widthMeasureSpec);

        int width = 0;
        int height = 0;
        int childwidth = 0;
        int childheight = 0;
        int linwidth = 0;
        int linheight = 0;
        int totaheight = 0;
        View viewChild;
        for (int i = 0; i < getChildCount(); i++) {
            viewChild = getChildAt(i);
            childwidth = viewChild.getMeasuredWidth();
            childheight = viewChild.getMeasuredHeight();
            if (linwidth + childwidth > widthsize) {
                width = widthsize;
                totaheight += linheight;
                linwidth = childheight;
                linheight = childheight;

            } else {
                linheight += childheight;
                linheight = Math.max(linheight, childheight);
                width = Math.max(linwidth, width);
            }
            if (i == getChildCount() - 1) {
                totaheight = totaheight + linheight;
                height = totaheight;
            }
        }
        width = widthmode == MeasureSpec.EXACTLY ? widthsize : width;
        height = heghtmode == MeasureSpec.EXACTLY ? heightsize : height;

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childwidth = 0;
        int childheight = 0;
        int linwidth = 0;
        int linheight = 0;
        int totaheight = 0;
        View viewChild;
        for (int i = 0; i < getChildCount(); i++) {
            viewChild = getChildAt(i);
            childwidth = viewChild.getMeasuredWidth();
            childheight = viewChild.getMeasuredHeight();
            childheight = viewChild.getMeasuredHeight();
            if (linwidth + childwidth > getMeasuredWidth()) {
                linheight = 0;
                totaheight += linheight;
                layoutView(viewChild, linwidth, totaheight, linwidth + childwidth, linheight + childheight);
                linwidth = childheight;
                linheight = childheight;

            } else {
                layoutView(viewChild, linwidth, totaheight, linwidth + childwidth, linheight + childheight);
                linheight += childheight;
                linheight = Math.max(linheight, childheight);
            }
        }
    }

    private void layoutView(View viewChild, int linwidth, int totaheight, int i, int i1) {
        linwidth += getPaddingLeft();
        linwidth += getPaddingTop();
        viewChild.layout(linwidth, totaheight, i, i1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
