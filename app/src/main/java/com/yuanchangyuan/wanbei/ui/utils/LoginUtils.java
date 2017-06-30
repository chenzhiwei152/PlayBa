package com.yuanchangyuan.wanbei.ui.utils;

import android.app.Activity;
import android.content.Context;

import com.yuanchangyuan.wanbei.api.JyCallBack;
import com.yuanchangyuan.wanbei.api.RestAdapterManager;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.bean.UserInfoBean;
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
    static Call<UserInfoBean> loginCall;
    static Call<UserInfoBean> thirdLoginCall;
    static boolean isSuccess;

    public static void commitlogin(final Context context, final String tel, String password) {
        DialogUtils.showDialog(context, "登陆...", false);
        Map<String, String> map = new HashMap<>();
        map.put("phone", tel);
        map.put("pwd", password);
        loginCall = RestAdapterManager.getApi().login(map);
        loginCall.enqueue(new JyCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    BaseContext.getInstance().setUserInfo(response.body());
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    SharePreManager.instance(context).setLoginTime(now.getTime());
                    SharePreManager.instance(context).setUserInfo(response.body());
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
//                    Intent jmActivityIntent = new Intent(context, MainActivity.class);
//                    context.startActivity(jmActivityIntent);
                    ((Activity) context).finish();
                } else {
                    UIUtil.showToast(response.body().msg);
                }
            }

            @Override
            public void onError(Call<UserInfoBean> call, Throwable t) {
                UIUtil.showToast("登陆失败~请稍后重试");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                try {
                    DialogUtils.closeDialog();
                    ErrorMessageUtils.taostErrorMessage(context, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean thirdLogin(final Context context, final UserInfo userInfo) {
        DialogUtils.showDialog(context, "登陆...", false);
        Map<String, String> map = new HashMap<>();
        map.put("nickname", userInfo.getUserName());
        map.put("authUID", userInfo.getUserNote());
        map.put("sex", userInfo.getUserGender().name().equals("男") ? "1" : "0");
        map.put("headimg", userInfo.getUserIcon());
        thirdLoginCall = RestAdapterManager.getApi().authLogin(map);
        thirdLoginCall.enqueue(new JyCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    BaseContext.getInstance().setUserInfo(response.body());
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    SharePreManager.instance(context).setLoginTime(now.getTime());
                    SharePreManager.instance(context).setUserInfo(response.body());
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
//                    Intent jmActivityIntent = new Intent(context, MainActivity.class);
//                    context.startActivity(jmActivityIntent);
                    ((Activity) context).finish();
                } else {
                    UIUtil.showToast(response.body().msg);
                }
            }

            @Override
            public void onError(Call<UserInfoBean> call, Throwable t) {
                UIUtil.showToast("登陆失败~请稍后重试");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<UserInfoBean> call, Response<UserInfoBean> response) {
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
}
