package com.yuanchangyuan.wanbei.ui.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseFragment;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.adapter.MainListItemAdapter;
import com.yuanchangyuan.wanbei.ui.adapter.PoPuMenuListAdapter;
import com.yuanchangyuan.wanbei.ui.bean.HomeListBean;
import com.yuanchangyuan.wanbei.ui.bean.SelectedBean;
import com.yuanchangyuan.wanbei.ui.bean.bannerBean;
import com.yuanchangyuan.wanbei.utils.ImageLoadedrManager;
import com.yuanchangyuan.wanbei.utils.LogUtils;
import com.yuanchangyuan.wanbei.view.DropDownMenu;
import com.yuanchangyuan.wanbei.view.MaxHeighListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-6-19.
 */

public class IndexFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    PullToRefreshRecyclerView sf_listview;
    private ConvenientBanner kanner;
    List<bannerBean> list = new ArrayList<>();
    private DropDownMenu dropDownMenu;
    //筛选标题list
    private List<String> types = new ArrayList<>();
    //筛选view的list
    private List<View> popupViews = new ArrayList<>();
    private List<SelectedBean> demands = new ArrayList<>();
    private PoPuMenuListAdapter mMenuAdapter2;
    private MainListItemAdapter listAdapter;
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_index_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle();

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
        sf_listview.setLoadMoreCount(5);
        //死数据
        bannerBean bannerBean = new bannerBean();
        bannerBean.setImage("http://www.fondos7.net/recorte/eeb11484f517b67adf866b65c26a27d7/lindos-cachorros_800x600.jpg");
        list.add(bannerBean);
        list.add(bannerBean);
        list.add(bannerBean);
        //初始化广告栏
        initAD(list);
        //
        listAdapter=new MainListItemAdapter(getActivity());

        sf_listview.setAdapter(listAdapter);



        List<HomeListBean> list=new ArrayList<>();

        HomeListBean bean=new HomeListBean();
        bean.setImageId("http://www.fondos7.net/recorte/eeb11484f517b67adf866b65c26a27d7/lindos-cachorros_800x600.jpg");
        bean.setTitle("测试");
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
    //筛选
    private void setFilter() {
        types.clear();
        popupViews.clear();
        //益智类
        final MaxHeighListView sortView = new MaxHeighListView(getActivity());
        sortView.setDividerHeight(0);
        sortView.setMaxHeight(199);
        demands.clear();
        demands.add(new SelectedBean("", "默认排序"));
        demands.add(new SelectedBean("salesPrice_asc", "租金从低到高"));
        demands.add(new SelectedBean("salesPrice_desc", "租金从高到低"));
        demands.add(new SelectedBean("floorSpace_asc", "面积从小到大"));
        demands.add(new SelectedBean("floorSpace_desc", "面积从大到小"));
        mMenuAdapter2 = new PoPuMenuListAdapter(getActivity(), demands);
        sortView .setAdapter(mMenuAdapter2);
        sortView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                dropDownMenu.setTabText(demands.get(position).getName());
                dropDownMenu.closeMenu();
//                mSortBy = demands.get(position).getKey();
//                stitch();
            }
        });
        types.add("益智类");
        popupViews.add(sortView);


        final MaxHeighListView softView = new MaxHeighListView(getActivity());
        softView.setDividerHeight(0);
        softView.setMaxHeight(199);
        demands.clear();
        demands.add(new SelectedBean("", "默认排序"));
        demands.add(new SelectedBean("salesPrice_asc", "租金从低到高"));
        demands.add(new SelectedBean("salesPrice_desc", "租金从高到低"));
        demands.add(new SelectedBean("floorSpace_asc", "面积从小到大"));
        demands.add(new SelectedBean("floorSpace_desc", "面积从大到小"));
        mMenuAdapter2 = new PoPuMenuListAdapter(getActivity(), demands);
        softView .setAdapter(mMenuAdapter2);
        softView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                dropDownMenu.setTabText(demands.get(position).getName());
                dropDownMenu.closeMenu();
//                mSortBy = demands.get(position).getKey();
//                stitch();
            }
        });
        types.add("软件园店");
        popupViews.add(softView);
        dropDownMenu.setDropDownMenu(types, popupViews);
    }
    /**
     * 初始化标题
     */
    private void initTitle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_ff6900);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
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
            ImageLoadedrManager.getInstance().display(getActivity(), data.getImage(), imageView);
        }
    }

    private void initAD(final List<bannerBean> list) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.kanner_index, sf_listview, false);//headview,广告栏
        kanner = (ConvenientBanner) header.findViewById(R.id.convenientBanner);
        dropDownMenu= (DropDownMenu) header.findViewById(R.id.dropDownMenu);
        setFilter();
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
