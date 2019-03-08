package com.lwj.luwenjie0308.view.interfaces;

/**
 * @Auther:
 * @Date: 2019/3/8 14:03
 * @Description:
 */
public interface IMainView<T> extends IBaseView {
    void onSuccess(T t);

    void onErr(int code, String message);
}
