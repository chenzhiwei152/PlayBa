package com.yuanchangyuan.wanbei.base;

/**
 * Created by sun.luwei on 2016/12/20.
 */

public class Constants {

    public static final int AddressUpdateSuccess = 0x10;

    public static class ErrorCode {
        public static String check_code = "400001039";//验证码错误
    }

    public static class Tag {
        public static final int SHOW_CHECK_CODE = 0x20;//显示验证码
        public static final int HIDE_CHECK_CODE = 0x21;//隐藏证码
        public static final int REGIST_SUCCESS = 0x22;//注册成功
        public static final int LOGIN_FAILURE = 0x40;//登录失败
        public static final int CHANGE_PASSWORD_SUCCESS = 0x23;//修改密码成功
    }


}
