package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.view.TitleBar;

import butterknife.BindView;

/**
 * 提交订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class CommitOrderActivity extends BaseActivity {
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
    private String tag;
    final int mxCount = 200;

    /**
     * 单击事件监听器
     */
//    private IClickListener mListener = null;
//
//    public interface IClickListener {
//        void onRightViewClick(View v);
//
//        void onDesButtonClick(View v);//减
//
//        void onInsButtonClick(View v);//加
//
//        void onCheckChange(View v, boolean isChecked);
//    }
//
//    public void setmListener(IClickListener mListener) {
//        this.mListener = mListener;
//    }
    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_commit_order;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();

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
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("提交订单");
        title_view.setTitleColor(Color.WHITE);
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftText("返回");
        title_view.setLeftTextColor(R.color.color_ffffff);
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
}
