package com.yuanchangyuan.wanbei.ui.fragment;

import android.view.View;

import com.yuanchangyuan.wanbei.base.BaseFragment;
import com.yuanchangyuan.wanbei.base.EventBusCenter;

/**
 * Created by chen.zhiwei on 2017-6-21.
 */

public class RentGoodsFragment extends BaseFragment{
    @Override
    protected int getContentViewLayoutId() {
        return 0;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }
}
