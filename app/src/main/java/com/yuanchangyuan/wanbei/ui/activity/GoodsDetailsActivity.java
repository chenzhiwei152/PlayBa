package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.MainListItemAdapter;
import com.yuanchangyuan.wanbei.ui.bean.HomeListBean;
import com.yuanchangyuan.wanbei.ui.bean.bannerBean;
import com.yuanchangyuan.wanbei.utils.ImageLoadedrManager;
import com.yuanchangyuan.wanbei.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-6-21.
 */

public class GoodsDetailsActivity extends BaseActivity {

    @BindView(R.id.sf_listview)
    PullToRefreshRecyclerView sf_listview;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ConvenientBanner kanner;
    List<bannerBean> list = new ArrayList<>();
    private MainListItemAdapter listAdapter;
    private Button bt_buy;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        sf_listview.setSwipeEnable(true);//open swipe
        sf_listview.setLayoutManager(new LinearLayoutManager(this));

        //死数据
        bannerBean bannerBean = new bannerBean();
        bannerBean.setImage("http://www.fondos7.net/recorte/eeb11484f517b67adf866b65c26a27d7/lindos-cachorros_800x600.jpg");
        list.add(bannerBean);
        list.add(bannerBean);
        list.add(bannerBean);
        //初始化广告栏
        initAD(list);
        //
        listAdapter = new MainListItemAdapter(this);

        sf_listview.setAdapter(listAdapter);


        List<HomeListBean> list = new ArrayList<>();

        HomeListBean bean = new HomeListBean();
        bean.setImageId("http://www.fondos7.net/recorte/eeb11484f517b67adf866b65c26a27d7/lindos-cachorros_800x600.jpg");
//        bean.setTitle("测试");
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        listAdapter.addList(list);
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
        title_view.setTitle("玩呗");
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

    /**
     * 广告栏定义图片地址
     */
    public class LocalImageHolderView implements Holder<bannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, bannerBean data) {
            ImageLoadedrManager.getInstance().display(GoodsDetailsActivity.this, data.getImage(), imageView);
        }
    }

    private void initAD(final List<bannerBean> list) {
        View header = LayoutInflater.from(this).inflate(R.layout.kanner_goods_detail, sf_listview, false);//headview,广告栏
        kanner = (ConvenientBanner) header.findViewById(R.id.convenientBanner);
        bt_buy = (Button) header.findViewById(R.id.bt_buy);
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsDetailsActivity.this, CommitOrderActivity.class));
            }
        });
//自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        kanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.dot_blur, R.mipmap.dot_focus})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).startTurning(3000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        convenientBanner.setManualPageable(false);//设置不能手动影响

        kanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                int[] startingLocation = new int[2];
//                v.getLocationOnScreen(startingLocation);
//                startingLocation[0] += v.getWidth() / 2;
            }
        });
//增加headview
        sf_listview.addHeaderView(header);
    }
}
