package com.yuanchangyuan.wanbei.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yuanchangyuan.wanbei.api.JyCallBack;
import com.yuanchangyuan.wanbei.api.RestAdapterManager;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.bean.SuperBean;
import com.yuanchangyuan.wanbei.ui.bean.UserInfoBean;
import com.yuanchangyuan.wanbei.ui.index.MainActivity;
import com.yuanchangyuan.wanbei.ui.utils.login.UserInfo;
import com.yuanchangyuan.wanbei.utils.DialogUtils;
import com.yuanchangyuan.wanbei.utils.ErrorMessageUtils;
import com.yuanchangyuan.wanbei.utils.SharePreManager;
import com.yuanchangyuan.wanbei.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-27.
 */

public class LoginUtils {
    static Call<SuperBean<UserInfoBean>> loginCall;
    static Call<SuperBean<UserInfoBean>> thirdLoginCall;
    static Call<SuperBean<UserInfoBean>> getInfoCall;
    static boolean isSuccess;

    public static void commitlogin(final Context context, final String tel, String password) {
        DialogUtils.showDialog(context, "登陆...", false);
        Map<String, String> map = new HashMap<>();
        map.put("phone", tel);
        map.put("pwd", password);
        loginCall = RestAdapterManager.getApi().login(map);
        loginCall.enqueue(new JyCallBack<SuperBean<UserInfoBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<UserInfoBean>> call, Response<SuperBean<UserInfoBean>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    BaseContext.getInstance().setUserInfo(response.body().getData());
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    SharePreManager.instance(context).setLoginTime(now.getTime());
                    SharePreManager.instance(context).setUserInfo(response.body().getData());
                    SharePreManager.instance(context).setUserInfo(response.body().getData());
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));

                    Intent jmActivityIntent = new Intent(context, MainActivity.class);
                    context.startActivity(jmActivityIntent);
                    ((Activity) context).finish();
                } else {
                    try {
                        UIUtil.showToast(response.body().getMsg());
                    } catch (Exception e) {
                    }

                }
            }

            @Override
            public void onError(Call<SuperBean<UserInfoBean>> call, Throwable t) {
                UIUtil.showToast("登陆失败~请稍后重试");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperBean<UserInfoBean>> call, Response<SuperBean<UserInfoBean>> response) {
                try {
                    DialogUtils.closeDialog();
                    ErrorMessageUtils.taostErrorMessage(context, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 第三方登录
     *
     * @param context
     * @param userInfo
     * @return
     */
    public static boolean thirdLogin(final Context context, final UserInfo userInfo) {
        DialogUtils.showDialog(context, "登陆...", false);
        Map<String, String> map = new HashMap<>();
        map.put("nickname", userInfo.getUserName());
        map.put("authUID", userInfo.getUserNote());
        map.put("sex", userInfo.getUserGender().name().equals("男") ? "1" : "0");
        map.put("headimg", userInfo.getUserIcon());
        thirdLoginCall = RestAdapterManager.getApi().authLogin(map);
        thirdLoginCall.enqueue(new JyCallBack<SuperBean<UserInfoBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<UserInfoBean>> call, Response<SuperBean<UserInfoBean>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    BaseContext.getInstance().setUserInfo(response.body().getData());
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    SharePreManager.instance(context).setLoginTime(now.getTime());
                    SharePreManager.instance(context).setUserInfo(response.body().getData());
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
                    Intent jmActivityIntent = new Intent(context, MainActivity.class);
                    context.startActivity(jmActivityIntent);
                    ((Activity) context).finish();
                } else {
                    UIUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onError(Call<SuperBean<UserInfoBean>> call, Throwable t) {
                UIUtil.showToast("登陆失败~请稍后重试");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperBean<UserInfoBean>> call, Response<SuperBean<UserInfoBean>> response) {
                try {
                    DialogUtils.closeDialog();
                    ErrorMessageUtils.taostErrorMessage(context, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    /**
     * 根据id获取个人信息
     */
    public static void getInfo() {
        final Context context = BaseContext.getInstance();
        if (BaseContext.getInstance().getUserInfo() != null) {
            getInfoCall = RestAdapterManager.getApi().getinfo(BaseContext.getInstance().getUserInfo().userId);
            getInfoCall.enqueue(new JyCallBack<SuperBean<UserInfoBean>>() {
                @Override
                public void onSuccess(Call<SuperBean<UserInfoBean>> call, Response<SuperBean<UserInfoBean>> response) {
                    if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                        BaseContext.getInstance().setUserInfo(response.body().getData());
                        Timestamp now = new Timestamp(System.currentTimeMillis());
                        SharePreManager.instance(context).setLoginTime(now.getTime());
                        SharePreManager.instance(context).setUserInfo(response.body().getData());
                        EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.PAY_MEMBER_SUCCESS));
                        EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
//                        Intent jmActivityIntent = new Intent(context, MainActivity.class);
//                        context.startActivity(jmActivityIntent);
//                        ((Activity) context).finish();
                    } else {
                        UIUtil.showToast(response.body().getMsg());
                    }
                }

                @Override
                public void onError(Call<SuperBean<UserInfoBean>> call, Throwable t) {

                }

                @Override
                public void onError(Call<SuperBean<UserInfoBean>> call, Response<SuperBean<UserInfoBean>> response) {

                }
            });
        }
    }
}
