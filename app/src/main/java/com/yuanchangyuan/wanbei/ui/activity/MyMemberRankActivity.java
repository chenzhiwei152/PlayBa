package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.api.JyCallBack;
import com.yuanchangyuan.wanbei.api.RestAdapterManager;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.MemberRankItemAdapter;
import com.yuanchangyuan.wanbei.ui.bean.MemberRankBean;
import com.yuanchangyuan.wanbei.utils.DialogUtils;
import com.yuanchangyuan.wanbei.utils.UIUtil;
import com.yuanchangyuan.wanbei.view.TitleBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class MyMemberRankActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_payed_deposit)
    TextView tv_payed_deposit;
    @BindView(R.id.tv_commit_time)
    TextView tv_commit_time;
    @BindView(R.id.tv_effective_time)
    TextView tv_effective_time;
    @BindView(R.id.rb_rank)
    RatingBar rb_rank;
    @BindView(R.id.bt_go_commit)
    Button bt_go_commit;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private Call<List<MemberRankBean>> call;
    private MemberRankItemAdapter adapter;
    private List<MemberRankBean> list = new ArrayList<>();

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_member_rank;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();

        rv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemberRankItemAdapter(this);
        rv_list.setAdapter(adapter);
        rv_list.setNestedScrollingEnabled(false);


        bt_go_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    Intent intent = new Intent(MyMemberRankActivity.this, PayDepositActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("RankList", (Serializable) list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void loadData() {
        getMenberRankInfo();
        if (BaseContext.getInstance().getUserInfo() != null) {

            tv_payed_deposit.setText("￥" + BaseContext.getInstance().getUserInfo().deposit);
            if (BaseContext.getInstance().getUserInfo().vipgrade > 0) {
                rb_rank.setNumStars(BaseContext.getInstance().getUserInfo().vipgrade);
                rb_rank.setVisibility(View.VISIBLE);
            } else {
                rb_rank.setVisibility(View.GONE);
            }
            tv_commit_time.setText(BaseContext.getInstance().getUserInfo().paytime);
            tv_effective_time.setText(BaseContext.getInstance().getUserInfo().endtime);
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
    protected View isNeedLec() {
        return null;
    }

    private void getMenberRankInfo() {
        DialogUtils.showDialog(MyMemberRankActivity.this, "加载中", false);
        call = RestAdapterManager.getApi().getMemberRank();
        call.enqueue(new JyCallBack<List<MemberRankBean>>() {
            @Override
            public void onSuccess(Call<List<MemberRankBean>> call, Response<List<MemberRankBean>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    adapter.ClearData();
                    adapter.addList(response.body());
                    list.addAll(response.body());
                } else {
                    UIUtil.showToast("获取会员信息失败");
                }
            }

            @Override
            public void onError(Call<List<MemberRankBean>> call, Throwable t) {
                UIUtil.showToast("获取会员信息失败");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<List<MemberRankBean>> call, Response<List<MemberRankBean>> response) {
                UIUtil.showToast("获取会员信息失败");
                DialogUtils.closeDialog();
            }
        });
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("我的会员");
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
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        super.onDestroy();
    }
}
