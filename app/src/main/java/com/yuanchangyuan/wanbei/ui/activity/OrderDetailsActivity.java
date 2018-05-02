package com.yuanchangyuan.wanbei.ui.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jpay.JPay;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.api.JyCallBack;
import com.yuanchangyuan.wanbei.api.RestAdapterManager;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.bean.OrderDetailBean;
import com.yuanchangyuan.wanbei.ui.bean.SuperBean;
import com.yuanchangyuan.wanbei.utils.DialogUtils;
import com.yuanchangyuan.wanbei.utils.ImageLoadedrManager;
import com.yuanchangyuan.wanbei.utils.LogUtils;
import com.yuanchangyuan.wanbei.utils.NetUtil;
import com.yuanchangyuan.wanbei.utils.UIUtil;
import com.yuanchangyuan.wanbei.view.MyDialog;
import com.yuanchangyuan.wanbei.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_address_name)
    TextView tv_address_name;
    @BindView(R.id.tv_address_phone)
    TextView tv_address_phone;
    @BindView(R.id.tv_address_detail)
    TextView tv_address_detail;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_real_pay)
    TextView tv_real_pay;
    @BindView(R.id.tv_order_number)
    TextView tv_order_number;
    @BindView(R.id.tv_order_create_time)
    TextView tv_order_create_time;
    @BindView(R.id.tv_order_pay_time)
    TextView tv_order_pay_time;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.rl_all_pay)
    RelativeLayout rl_all_pay;
    @BindView(R.id.rl_pay_state)
    RelativeLayout rl_pay_state;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_pay_state)
    TextView tv_pay_state;
    private int payChannel = 0;//支付通道0为支付宝1为微信

    private String orderId;
    private String type;
    Call<SuperBean<OrderDetailBean>> call;
    private Call<SuperBean<String>> getRsaOrderCall;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        try {
            orderId = getIntent().getExtras().getString("orderId");
            type = getIntent().getExtras().getString("type");
        } catch (Exception e) {
        }
    }

    @Override
    public void loadData() {
        getOrderDetail();
    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    /**
     * 配置信息接口
     */
    private void initDate(OrderDetailBean orderDetailBean) {
        if (orderDetailBean != null) {

            ImageLoadedrManager.getInstance().display(this, orderDetailBean.getGoodsImg(), iv_goods);
            tv_address_name.setText(orderDetailBean.getOrdername());
            tv_address_phone.setText(orderDetailBean.getOrderphone());
            tv_address_detail.setText(orderDetailBean.getOrderaddress());
            tv_goods_name.setText(orderDetailBean.getGoodsName());
            tv_price.setText("￥" + orderDetailBean.getGoodsprice() );
            tv_count.setText("x" + orderDetailBean.getCount());
            tv_real_pay.setText("￥" + orderDetailBean.getPayamount());
            tv_order_number.setText(orderDetailBean.getOrderpayno());
            tv_order_create_time.setText(UIUtil.timeStamp2Date(orderDetailBean.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
            tv_order_pay_time.setText(UIUtil.timeStamp2Date(orderDetailBean.getPaytime(), "yyyy-MM-dd HH:mm:ss"));
            tv_pay_state.setText(orderDetailBean.getFeiStatus() == 0 ? "未支付" : "已支付");
            if (orderDetailBean.getFeiStatus() == 0) {
                rl_pay_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payStyleDialog();
                    }
                });
            } else {
                rl_pay_state.setOnClickListener(null);
            }
            tv_pay.setText(orderDetailBean.getFeiYong()+"");
            if (type.equals("rent")) {
                if (orderDetailBean.getConfirm()==2){
                    rl_pay_state.setVisibility(View.VISIBLE);
                    rl_all_pay.setVisibility(View.VISIBLE);
                }else {
                    rl_pay_state.setVisibility(View.GONE);
                    rl_all_pay.setVisibility(View.GONE);
                }


            }
        }
    }

    /**
     * 获取数据接口
     */
    private void getOrderDetail() {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }
        if (TextUtils.isEmpty(type)) {
            UIUtil.showToast("dingdanid为空");
            finish();
            return;
        }
        DialogUtils.showDialog(this, "加载中", false);
        if (type.equals("rent")) {
            call = RestAdapterManager.getApi().getRentOrderDetails(orderId);
        } else {
            call = RestAdapterManager.getApi().getOrderDetails(orderId);
        }


        call.enqueue(new JyCallBack<SuperBean<OrderDetailBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<OrderDetailBean>> call, Response<SuperBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.successCode) {
                        try {
                            initDate(response.body().getData());
                        } catch (Exception e) {
                        }

                    } else {
                        UIUtil.showToast(response.body().getMsg());
                    }
                } else {
                    UIUtil.showToast("获取数据异常");
                }
            }

            @Override
            public void onError(Call<SuperBean<OrderDetailBean>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("获取数据异常");
            }

            @Override
            public void onError(Call<SuperBean<OrderDetailBean>> call, Response<SuperBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast("获取数据异常");
            }
        });

    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("订单详情");
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

    }


    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        if (getRsaOrderCall != null) {
            getRsaOrderCall.cancel();
        }
        super.onDestroy();
    }

    /**
     * 支付方式
     */
    public void payStyleDialog() {

        View view = View.inflate(OrderDetailsActivity.this, R.layout.dialog_pay_type, null);
        showdialog(view);


        final CheckBox aliCheck = (CheckBox) view.findViewById(R.id.cb_ali);
        final CheckBox wxCheck = (CheckBox) view.findViewById(R.id.cb_wx);
        final CheckBox offLineCheck = (CheckBox) view.findViewById(R.id.cb_off);
        final Button commit = (Button) view.findViewById(R.id.bt_commit);

        aliCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wxCheck.setChecked(false);
                    offLineCheck.setChecked(false);
                    payChannel = 0;
                } else {
                    aliCheck.setChecked(false);
                    payChannel = 3;
                }
            }
        });
        wxCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    aliCheck.setChecked(false);
                    offLineCheck.setChecked(false);
                    payChannel = 1;
                } else {
                    wxCheck.setChecked(false);
                    payChannel = 3;
                }
            }
        });
        offLineCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wxCheck.setChecked(false);
                    aliCheck.setChecked(false);
                    payChannel = 2;
                } else {
                    offLineCheck.setChecked(false);
                    payChannel = 3;
                }
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UIUtil.isFastDoubleClick()) {
                    if (payChannel == 3) {
                        UIUtil.showToast("请选择支付方式");
                    } else if (payChannel == 2) {
                        myDialog.dismiss();
                        goNext();
                        // 刷新数据
                    } else {
                        getRSAOrderInfo();
                        myDialog.dismiss();
                    }
                }

            }
        });

    }

    private MyDialog myDialog;

    /**
     * 弹出dialog
     *
     * @param view
     */
    private void showdialog(View view) {

        myDialog = new MyDialog(this, 0, 0, view, R.style.dialog);
        myDialog.show();
        myDialog.setCancelable(false);
    }

    /**
     * 获取加密订单信息
     */
    private void getRSAOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("userId", BaseContext.getInstance().getUserInfo().userId);
        map.put("payChannel", payChannel + "");
//        map.put("totalMoney", price + "");
        map.put("orderType", "3");
        DialogUtils.showDialog(OrderDetailsActivity.this, "加载...", false);
        getRsaOrderCall = RestAdapterManager.getApi().getRsaOrderInfo(map);
        getRsaOrderCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    LogUtils.e(response.body().getMsg());
                    pay(response.body().getData());
                } else {
                    UIUtil.showToast("支付失败");
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("支付失败");
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast("支付失败");
            }
        });
    }

    /**
     * 支付
     *
     * @param string
     */
    private void pay(String string) {
        if (payChannel == 0) {
            JPay.getIntance(this).toPay(JPay.PayMode.ALIPAY, string, new JPay.JPayListener() {
                @Override
                public void onPaySuccess() {
                    DialogUtils.closeDialog();
                    goNext();
                    UIUtil.showToast("支付成功");
                }

                @Override
                public void onPayError(int error_code, String message) {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("支付失败>" + " " + message);
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("取消了支付");
                }
            });
        } else if (payChannel == 1) {
            JPay.getIntance(this).toPay(JPay.PayMode.WXPAY, string, new JPay.JPayListener() {
                @Override
                public void onPaySuccess() {
                    DialogUtils.closeDialog();
                    goNext();
                    UIUtil.showToast("支付成功");
                }

                @Override
                public void onPayError(int error_code, String message) {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("支付失败>" + " " + message);
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("取消了支付");
                }
            });
        }

    }

    private void goNext() {
        if (!TextUtils.isEmpty(orderId)) {
            getOrderDetail();
        } else {
            UIUtil.showToast("订单id为空");
        }
    }
}
