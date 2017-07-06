package com.yuanchangyuan.wanbei.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseFragment;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.RentOrderListAdapter;

import butterknife.BindView;

/**
 * 租赁订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class RentGoodsOrderListFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
    @BindView(R.id.swiperefreshlayout)
    TwinklingRefreshLayout swiperefreshlayout;
    private RentOrderListAdapter rentOrderListAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_rent_order_list;
    }

    @Override
    protected void initViewsAndEvents() {
        sf_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        swiperefreshlayout.setFloatRefresh(true);
        ProgressLayout headerView = new ProgressLayout(getActivity());
        swiperefreshlayout.setHeaderView(headerView);
        swiperefreshlayout.setEnableLoadmore(true);
//        BallPulseView footView = new BallPulseView(getActivity());
//        swiperefreshlayout.setBottomView(footView);
        rentOrderListAdapter = new RentOrderListAdapter(getActivity());
        sf_listview.setAdapter(rentOrderListAdapter);
        swiperefreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiperefreshlayout.setEnableLoadmore(true);
                    }
                }, 10);

//                pageNum = 1;
//                getOrderList();
//                UIUtil.showToast("刷新数据~");
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                getOrderList();
//                UIUtil.showToast("加载更多");
            }
        });
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
