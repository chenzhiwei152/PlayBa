package com.yuanchangyuan.wanbei.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.api.JyCallBack;
import com.yuanchangyuan.wanbei.api.RestAdapterManager;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.BaseFragment;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.BuyOrderListAdapter;
import com.yuanchangyuan.wanbei.ui.bean.BuyOrderListItemBean;
import com.yuanchangyuan.wanbei.ui.bean.SuperBean;
import com.yuanchangyuan.wanbei.utils.LogUtils;
import com.yuanchangyuan.wanbei.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 买卖订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class BuyGoodsOrderListFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    PullToRefreshRecyclerView sf_listview;
    private BuyOrderListAdapter buyOrderListAdapter;
    private Call<SuperBean<BuyOrderListItemBean>> orderListCall;
    private int pageNum = 1;
    private int pageSize = 10;

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
                getOrderList();
            }
        });

        sf_listview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e("onRefresh", "刷新数据~");
                pageNum = 1;
                getOrderList();

            }
        });
        sf_listview.setLoadMoreCount(pageSize);
        buyOrderListAdapter = new BuyOrderListAdapter(getActivity());
        sf_listview.setAdapter(buyOrderListAdapter);
        sf_listview.onFinishLoading(true, false);
    }

    @Override
    protected void loadData() {
        getOrderList();
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

    private void getOrderList() {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        map.put("userid", BaseContext.getInstance().getUserInfo().userId);
        orderListCall = RestAdapterManager.getApi().getBuyOrderList(map);
        orderListCall.enqueue(new JyCallBack<SuperBean<BuyOrderListItemBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<BuyOrderListItemBean>> call, Response<SuperBean<BuyOrderListItemBean>> response) {
                sf_listview.setOnRefreshComplete();
                sf_listview.onFinishLoading(true, false);
                if (response != null && response.body() != null) {
                    if (response.body().getData().getData().size() > 0) {
                        if (pageNum == 1) {
                            buyOrderListAdapter.ClearData();
                        }
                        buyOrderListAdapter.addList(response.body().getData().getData());
                        pageNum++;
                    } else {
                        if (pageNum == 1) {
//无数据
                        } else {
                            UIUtil.showToast("已加载完全部数据");
                            sf_listview.onFinishLoading(false, false);
                        }

                    }
                }
            }

            @Override
            public void onError(Call<SuperBean<BuyOrderListItemBean>> call, Throwable t) {
                sf_listview.setOnRefreshComplete();
                sf_listview.onFinishLoading(true, false);
            }

            @Override
            public void onError(Call<SuperBean<BuyOrderListItemBean>> call, Response<SuperBean<BuyOrderListItemBean>> response) {
                sf_listview.setOnRefreshComplete();
                sf_listview.onFinishLoading(true, false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderListCall != null) {
            orderListCall.cancel();
        }
    }
}
