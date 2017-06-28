package com.yuanchangyuan.wanbei.api;


import com.yuanchangyuan.wanbei.bean.ErrorBean;
import com.yuanchangyuan.wanbei.ui.bean.GoodsFilterBean;
import com.yuanchangyuan.wanbei.ui.bean.GoodsListBean;
import com.yuanchangyuan.wanbei.ui.bean.MemberRankBean;
import com.yuanchangyuan.wanbei.ui.bean.ShoppingAddressListItemBean;
import com.yuanchangyuan.wanbei.ui.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * 网络请求接口
 */

public interface JyApi {


    /**
     * 登陆
     */
    @POST("/resource/user/login")
    Call<UserInfoBean> login(@Body Map<String, String> map);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @POST("/resource/user/userRegister")
    Call<String> reister(@Body Map<String, String> map);

    /**
     * 修改密码，忘记密码
     *
     * @param map
     * @return
     */
    @POST("/resource/user/forgetPwd")
    Call<ErrorBean> commitNewPassword(@Body Map<String, String> map);


    /**
     * 增加收货地址
     *
     * @param map
     * @return
     */
    @POST("/resource/deliveryAddress/addAddress")
    Call<String> addAddress(@Body Map<String, String> map);

    /**
     * 编辑收货地址
     *
     * @param map
     * @return
     */
    @POST("/resource/deliveryAddress/updateDeliveryAddress")
    Call<String> editAddress(@Body Map<String, String> map);

    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @GET("/resource/deliveryAddress/deleteById")
    Call<ErrorBean> deleteAddress(@Query("id") String id);

    /**
     * 获取地址列表
     *
     * @param userid
     * @return
     */
    @GET("/resource/deliveryAddress/getAllDeliveryAddress")
    Call<List<ShoppingAddressListItemBean>> getAddressList(@Query("userid") String userid);


    /**
     * 获取商品类型筛选条件
     *
     * @return
     */
    @GET("/resource/goodstype/getAllList")
    Call<List<GoodsFilterBean>> getAllFilterType();

    /**
     * 获取商品门店筛选条件
     *
     * @return
     */
    @POST("/resource/shop/getAllList")
    Call<List<GoodsFilterBean>> getAllFilterShops();

    /**
     * 获取首页列表
     *
     * @param map
     * @return
     */
    @POST("/resource/goods/goodsAllList")
    Call<List<GoodsListBean>> getGoodsList(@Body Map<String, String> map);

    /**
     * 实名认证
     *
     * @param map
     * @return
     */
    @POST("/resource/user/userAuth")
    Call<String> commitRealName(@Body Map<String, String> map);

    /**
     * 获取会员登记信息
     *
     * @return
     */
    @GET("/resource/viptype/getAllList")
    Call<List<MemberRankBean>> getMemberRank();

    /**
     * 广告位数据
     *
     */
    @GET("/resource/goods/adList")
    Call<List<GoodsListBean>> getAdList(@Query("userId") String userId);
}
