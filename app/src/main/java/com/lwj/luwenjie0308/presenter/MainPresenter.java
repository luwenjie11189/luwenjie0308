package com.lwj.luwenjie0308.presenter;

import android.app.Application;

import com.lwj.luwenjie0308.application.Contans;
import com.lwj.luwenjie0308.model.bean.ShopBean;
import com.lwj.luwenjie0308.model.util.HttpUtil;
import com.lwj.luwenjie0308.view.interfaces.IMainView;

/**
 * @Auther:
 * @Date: 2019/3/8 14:18
 * @Description:
 */
public class MainPresenter extends BasePresenter<IMainView<ShopBean>> {

    private final HttpUtil httpUtil;

    public MainPresenter() {
        httpUtil = HttpUtil.getInstance();
    }

    public void showData() {
        httpUtil.getData(Contans.SHOP, ShopBean.class, new HttpUtil.CallBackData<ShopBean>() {
            @Override
            public void onResponse(ShopBean shopBean) {
                if (shopBean != null) {
                    getView().onSuccess(shopBean);
                }
            }

            @Override
            public void onFail(String err) {
                getView().onErr(0, err);
            }
        });
    }
}
