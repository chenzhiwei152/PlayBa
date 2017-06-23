package com.yuanchangyuan.wanbei.api;


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
     * 获取地址列表
     *
     * @param userid
     * @return
     */
    @GET("/resource/deliveryAddress/getAllDeliveryAddress")
    Call<List<ShoppingAddressListItemBean>> getAddressList(@Query("userid") String userid);

}
