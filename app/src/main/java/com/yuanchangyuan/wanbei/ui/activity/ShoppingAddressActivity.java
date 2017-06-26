package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.api.JyCallBack;
import com.yuanchangyuan.wanbei.api.RestAdapterManager;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.ShoppingAddressListAdapter;
import com.yuanchangyuan.wanbei.ui.bean.ShoppingAddressListItemBean;
import com.yuanchangyuan.wanbei.utils.DialogUtils;
import com.yuanchangyuan.wanbei.utils.ErrorMessageUtils;
import com.yuanchangyuan.wanbei.utils.LogUtils;
import com.yuanchangyuan.wanbei.view.TitleBar;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class ShoppingAddressActivity extends BaseActivity {
    @BindView(R.id.sf_listview)
    PullToRefreshRecyclerView sf_listview;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ShoppingAddressListAdapter shoppingAddressListAdapter;
    private ImageView mCollectView;
    private Call<List<ShoppingAddressListItemBean>> call;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_shopping_address_list;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        sf_listview.setSwipeEnable(true);//open swipe
        sf_listview.setLayoutManager(new LinearLayoutManager(this));
        shoppingAddressListAdapter = new ShoppingAddressListAdapter(this);
        sf_listview.setAdapter(shoppingAddressListAdapter);
//        sf_listview.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
//            @Override
//            public void onLoadMoreItems() {
//                LogUtils.e("onLoadMoreItems", "加载更多~");
////                loadMore(Interface.BEFORE + date);
//            }
//        });
        sf_listview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e("onRefresh", "刷新数据~");
                loadAddressData();

            }
        });
    }

    @Override
    public void loadData() {
        loadAddressData();
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.AddressUpdateSuccess) {
                loadAddressData();
            }
        }

    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    /**
     * 获取数据
     */
    private void loadAddressData() {
        DialogUtils.showDialog(ShoppingAddressActivity.this, "加载中", false);
        call = RestAdapterManager.getApi().getAddressList(BaseContext.getInstance().getUserInfo().userId);
        call.enqueue(new JyCallBack<List<ShoppingAddressListItemBean>>() {
            @Override
            public void onSuccess(Call<List<ShoppingAddressListItemBean>> call, Response<List<ShoppingAddressListItemBean>> response) {
                DialogUtils.closeDialog();
                sf_listview.setOnRefreshComplete();
                if (response != null && response.body() != null) {
                    shoppingAddressListAdapter.ClearData();
                    shoppingAddressListAdapter.addList(response.body());
                }
            }

            @Override
            public void onError(Call<List<ShoppingAddressListItemBean>> call, Throwable t) {
                DialogUtils.closeDialog();
                if (sf_listview != null) {
                    sf_listview.setOnRefreshComplete();
                }
            }

            @Override
            public void onError(Call<List<ShoppingAddressListItemBean>> call, Response<List<ShoppingAddressListItemBean>> response) {
                DialogUtils.closeDialog();
                if (sf_listview != null) {
                    sf_listview.setOnRefreshComplete();
                }
                try {
                    ErrorMessageUtils.taostErrorMessage(ShoppingAddressActivity.this, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("收货地址");
        title_view.setTitleColor(Color.WHITE);
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftText("返回");
        title_view.setLeftTextColor(Color.WHITE);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_view.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        mCollectView = (ImageView) title_view.addAction(new TitleBar.ImageAction(R.mipmap.ic_add_new) {
            @Override
            public void performAction(View view) {
//                UIUtil.showToast("点击了新增");
                Intent intent = new Intent(ShoppingAddressActivity.this, ShoppingAddressEditActivity.class);
                startActivity(intent);
            }
        });


        title_view.setImmersive(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_ff6900);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        super.onDestroy();
    }
}
