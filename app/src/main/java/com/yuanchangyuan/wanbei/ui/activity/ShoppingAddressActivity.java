package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import com.yuanchangyuan.wanbei.bean.ErrorBean;
import com.yuanchangyuan.wanbei.ui.adapter.ShoppingAddressListAdapter;
import com.yuanchangyuan.wanbei.ui.bean.ShoppingAddressListItemBean;
import com.yuanchangyuan.wanbei.ui.listerner.ShoppingAddressItemOnClickListerner;
import com.yuanchangyuan.wanbei.utils.DialogUtils;
import com.yuanchangyuan.wanbei.utils.ErrorMessageUtils;
import com.yuanchangyuan.wanbei.utils.LogUtils;
import com.yuanchangyuan.wanbei.utils.UIUtil;
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

    private Call<ErrorBean> deleteCall;
    private String type;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_shopping_address_list;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        try {
            if (getIntent().getExtras() != null) {
                type = getIntent().getExtras().getString("type", "");
            }
        } catch (Exception e) {

        }

        sf_listview.setSwipeEnable(true);//open swipe
        sf_listview.setLayoutManager(new LinearLayoutManager(this));


        shoppingAddressListAdapter = new ShoppingAddressListAdapter(this);
        shoppingAddressListAdapter.setOnClickListerner(new ShoppingAddressItemOnClickListerner() {
            @Override
            public void onClick(ShoppingAddressListItemBean bean) {
                //编辑
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressBean", bean);
                intent.putExtras(bundle);
                setResult(Constants.ADD_REQUEST_CODE, intent);
                finish();
            }

            @Override
            public void onDeleteListerner(final ShoppingAddressListItemBean bean) {
                //删除
                DialogUtils.showOrderCancelMsg(ShoppingAddressActivity.this, "确定要删除吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bean != null && !TextUtils.isEmpty(bean.getId() + "")) {
                            deleteAddress(bean.getId() + "");
                        }
                    }

//            @Override
//            public void callBack() {//退出登录
//
//            }
                });
            }
        });
        sf_listview.setAdapter(shoppingAddressListAdapter);
        shoppingAddressListAdapter.setType(type);
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

    private void deleteAddress(String id) {
        deleteCall = RestAdapterManager.getApi().deleteAddress(id);
        deleteCall.enqueue(new JyCallBack<ErrorBean>() {
            @Override
            public void onSuccess(Call<ErrorBean> call, Response<ErrorBean> response) {

                if (response != null && response.body() != null) {
                    UIUtil.showToast(response.body().msg);
                    if (response.body().code == 1000) {
                        loadAddressData();
                    }
                }
            }

            @Override
            public void onError(Call<ErrorBean> call, Throwable t) {

            }

            @Override
            public void onError(Call<ErrorBean> call, Response<ErrorBean> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(ShoppingAddressActivity.this,response.errorBody().string(),"");
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
        if (deleteCall != null) {
            deleteCall.cancel();
        }
        super.onDestroy();
    }
}
