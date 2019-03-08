package com.lwj.luwenjie0308.presenter;

import com.lwj.luwenjie0308.view.interfaces.IBaseView;

/**
 * @Auther:
 * @Date: 2019/3/8 14:11
 * @Description:
 */
public class BasePresenter<V> implements IBaseView {

    private V view;

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

    public void onDetachView() {
        this.view = null;
    }

}
