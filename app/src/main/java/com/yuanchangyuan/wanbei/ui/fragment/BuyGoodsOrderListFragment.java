package com.yuanchangyuan.wanbei.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseFragment;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.BuyOrderListAdapter;
import com.yuanchangyuan.wanbei.utils.LogUtils;

import butterknife.BindView;

/**
 * 买卖订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class BuyGoodsOrderListFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    PullToRefreshRecyclerView sf_listview;
    private BuyOrderListAdapter buyOrderListAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_buy_order_list;
    }

    @Override
    protected void initViewsAndEvents() {

        sf_listview.setSwipeEnable(true);//open swipe
        sf_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        sf_listview.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                LogUtils.e("onLoadMoreItems", "加载更多~");
//                loadMore(Interface.BEFORE + date);
            }
        });
        sf_listview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e("onRefresh", "刷新数据~");
//                loadLatest();
                sf_listview.setOnRefreshComplete();
            }
        });
        buyOrderListAdapter = new BuyOrderListAdapter(getActivity());
        sf_listview.setAdapter(buyOrderListAdapter);
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
