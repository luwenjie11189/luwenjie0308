package com.lwj.luwenjie0308.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lwj.luwenjie0308.R;
import com.lwj.luwenjie0308.model.bean.ShopBean;
import com.lwj.luwenjie0308.presenter.MainPresenter;
import com.lwj.luwenjie0308.view.adapter.MyAdapter;
import com.lwj.luwenjie0308.view.interfaces.IMainView;

public class SecondActivity extends BaseActivity implements IMainView<ShopBean> {

    private ExpandableListView expandableListView;
    private CheckBox checkBox;
    private MainPresenter mainPresenter;
    private MyAdapter myAdapter;
    private TextView price;

    @Override
    protected int bindlayout() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView() {
        expandableListView = bindView(R.id.expandedListView);
        checkBox = bindView(R.id.select_all);
        price = bindView(R.id.price_all);
        expandableListView.setIndicatorBounds(expandableListView.getWidth() + 1000, expandableListView.getHeight());
    }

    @Override
    protected void initData() {
        mainPresenter = new MainPresenter();
        mainPresenter.setView(this);
        mainPresenter.showData();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDetachView();
    }

    @Override
    public void onSuccess(ShopBean shopBean) {
        Log.e("myMeeage",""+shopBean);
        if (shopBean!=null){
            myAdapter = new MyAdapter(this);
            myAdapter.setData(shopBean);
            myAdapter.setCheckBox(checkBox);
            expandableListView.setAdapter(myAdapter);
            for (int i = 0; i < shopBean.getData().size() ; i++) {
                expandableListView.expandGroup(i);
            }
        }

    }

    @Override
    public void onErr(int code, String message) {

    }
}
