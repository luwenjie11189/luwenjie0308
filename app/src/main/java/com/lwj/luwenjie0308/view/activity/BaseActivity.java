package com.lwj.luwenjie0308.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @Auther:
 * @Date: 2019/3/8 13:38
 * @Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindlayout());
        initView();
        initData();
        bindEvent();
    }

    protected abstract int bindlayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void bindEvent();

    protected <T extends View> T bindView(int resId) {
        return (T) findViewById(resId);
    }

}
