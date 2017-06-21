package com.yuanchangyuan.wanbei.api;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by sun.luwei on 2016/11/23.
 * 网络请求接口
 */

public interface JyApi {

    String VERSION = "v1";
    String CMS_API = "/jycms-api/" + VERSION;
    String USER_API = "/user-api/" + VERSION;//用户中心服务
    String JYGOODS_API = "/jygoods-api/" + VERSION;//商品服务
    String COMMON_UPGRADE = "/common-upgrade/" + VERSION;
    String JYORDER_API = "/jyorder-center/" + VERSION;//订单服务
    String COMMON_CITY = "/common-city/" + VERSION;//地址
    String CASHIER = "/jypay-cashier/" + VERSION;//收银台

    String FEEDBACK_UPLOADIMAGE_URL = RestAdapterManager.BASEURL + JYGOODS_API + "/feedback/upload";

    @GET(CMS_API + "/data/info/getPageDataInfo/510100000/0/{user}")
    Call<String> getFeed(@Path("user") String user);//test


}
