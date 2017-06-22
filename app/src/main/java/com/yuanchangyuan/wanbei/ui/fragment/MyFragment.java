package com.yuanchangyuan.wanbei.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseFragment;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.activity.AccountSafetyActivity;
import com.yuanchangyuan.wanbei.ui.activity.CommitRealNameActivity;
import com.yuanchangyuan.wanbei.ui.activity.MyMemberRankActivity;
import com.yuanchangyuan.wanbei.ui.activity.OrderListActivity;
import com.yuanchangyuan.wanbei.ui.activity.PersonInformationActivity;
import com.yuanchangyuan.wanbei.ui.activity.ShoppingAddressActivity;
import com.yuanchangyuan.wanbei.view.CircularImageView;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-6-19.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_head)
    CircularImageView ivHead;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.iv_image_order)
    ImageView ivImageOrder;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.iv_safe)
    ImageView ivSafe;
    @BindView(R.id.rl_safety)
    RelativeLayout rlSafety;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @BindView(R.id.iv_mumber)
    ImageView ivMumber;
    @BindView(R.id.rl_member)
    RelativeLayout rlMember;
    @BindView(R.id.iv_real_name)
    ImageView ivRealName;
    @BindView(R.id.rl_real_name)
    RelativeLayout rlRealName;
    @BindView(R.id.iv_abount)
    ImageView ivAbount;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.tv_setting)
    TextView tv_setting;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        rlOrder.setOnClickListener(this);
        rlSafety.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
        rlMember.setOnClickListener(this);
        rlRealName.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                //订单
                startActivity(new Intent(getActivity(), OrderListActivity.class));
                break;
            case R.id.rl_safety:
                //账户安全
                startActivity(new Intent(getActivity(), AccountSafetyActivity.class));
                break;
            case R.id.rl_location:
                startActivity(new Intent(getActivity(), ShoppingAddressActivity.class));
                //收货地址
                break;
            case R.id.rl_member:
                //会员
                startActivity(new Intent(getActivity(), MyMemberRankActivity.class));
                break;
            case R.id.rl_real_name:
                //实名制
                startActivity(new Intent(getActivity(), CommitRealNameActivity.class));
                break;
            case R.id.rl_about:
                //关于
                break;
            case R.id.tv_setting:
                //设置
                startActivity(new Intent(getActivity(), PersonInformationActivity.class));
                break;
        }
    }
}
