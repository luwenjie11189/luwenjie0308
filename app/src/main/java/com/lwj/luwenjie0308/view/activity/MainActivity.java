package com.lwj.luwenjie0308.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lwj.luwenjie0308.R;

public class MainActivity extends BaseActivity {

    private Button button;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected int bindlayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        button = bindView(R.id.button_sl);
        editText = bindView(R.id.edit_text);
        imageView = bindView(R.id.imageView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
