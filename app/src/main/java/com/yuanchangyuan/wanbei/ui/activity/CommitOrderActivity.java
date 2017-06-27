package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.bean.GoodsListBean;
import com.yuanchangyuan.wanbei.ui.bean.ShoppingAddressListItemBean;
import com.yuanchangyuan.wanbei.utils.ImageLoadedrManager;
import com.yuanchangyuan.wanbei.utils.UIUtil;
import com.yuanchangyuan.wanbei.view.TitleBar;

import butterknife.BindView;

import static com.yuanchangyuan.wanbei.base.Constants.ADD_REQUEST_CODE;

/**
 * 提交订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class CommitOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_view)
    TitleBar title_view;
    //    private BuyGoodsFragment buyGoodsFragment;
//    private RentGoodsFragment rentGoodsFragment;
    @BindView(R.id.tv_goods_num)
    TextView tvNum;
    @BindView(R.id.btn_des_goods_num)
    TextView tvDes;
    @BindView(R.id.btn_add_goods_num)
    TextView tvIns;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.ll_add_addresss)
    LinearLayout ll_add_addresss;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.tv_address_name)
    TextView tv_address_name;
    @BindView(R.id.tv_address_phone)
    TextView tv_address_phone;
    @BindView(R.id.tv_address_detail)
    TextView tv_address_detail;
    private String tag;
    private String id;
    private int price;
    final int mxCount = 200;
    private GoodsListBean goodsBean;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_commit_order;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goodsBean = (GoodsListBean) bundle.getSerializable("detail");
            tag = bundle.getString("type");
        }
        ll_add_addresss.setOnClickListener(this);
        ll_address.setOnClickListener(this);

        tvDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(tvNum.getText().toString());
                if (count == 1) {
                    tvDes.setEnabled(false);
                    return;

                }
                tvIns.setEnabled(true);

                if (count > 1) {

                    if (count == 2) {
                        tvDes.setEnabled(false);
                    }
                    count--;
                    tvNum.setText(count + "");
                }
            }
        });
        tvIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDes.setEnabled(true);
                int count = Integer.valueOf(tvNum.getText().toString());
                if (count < mxCount) {

                    if (count == mxCount - 1) {
                        tvIns.setEnabled(false);
                    }
                    count++;
                    tvNum.setText(count + "");
                }


            }
        });
//        if (tag.equals("rent")) {
//
//            getSupportFragmentManager().beginTransaction().
//                    replace(R.id.container, new RentGoodsFragment()).
//                    commit();
//
//        } else {
//            getSupportFragmentManager().beginTransaction().
//                    replace(R.id.container, new BuyGoodsFragment()).
//                    commit();
//        }
    }

    @Override
    public void loadData() {
        setValueDefault();
    }

    private void setValueDefault() {
        ImageLoadedrManager.getInstance().display(this, goodsBean.getGoodsImg(), iv_goods);
        tv_title.setText(goodsBean.getName());
        if (tag.equals("rent")) {
            tv_price.setText(goodsBean.getVipprice() / 100.00 + "");
        } else {
            tv_price.setText(goodsBean.getPrice() / 100.00 + "");
        }


    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (resultCode == Constants.ADD_REQUEST_CODE) {
//获取收货地址
                setAddress((ShoppingAddressListItemBean) data.getExtras().getSerializable("addressBean"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected View isNeedLec() {
        return null;
    }


    private void setAddress(ShoppingAddressListItemBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getName()) && !TextUtils.isEmpty(bean.getPhone()) && !TextUtils.isEmpty(bean.getDetail())) {
            tv_address_name.setText(bean.getName());
            tv_address_phone.setText(bean.getPhone());
            tv_address_detail.setText(bean.getDetail());
            ll_address.setVisibility(View.VISIBLE);
            ll_add_addresss.setVisibility(View.GONE);
        } else {
            //
            ll_address.setVisibility(View.GONE);
            ll_add_addresss.setVisibility(View.VISIBLE);
            UIUtil.showToast("地址获取失败");
        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("提交订单");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_addresss:
            case R.id.ll_address:
                Intent intent = new Intent(this, ShoppingAddressActivity.class);
                intent.putExtra("type", "getAddress");
                startActivityForResult(intent, ADD_REQUEST_CODE);
                break;
        }
    }
}
