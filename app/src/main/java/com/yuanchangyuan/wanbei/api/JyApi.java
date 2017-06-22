package com.yuanchangyuan.wanbei.api;


import com.yuanchangyuan.wanbei.ui.bean.UserInfoBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


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


}
