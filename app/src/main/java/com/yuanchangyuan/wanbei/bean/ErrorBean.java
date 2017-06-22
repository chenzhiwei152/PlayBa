package com.yuanchangyuan.wanbei.bean;

import java.io.Serializable;

/**
 * Created by sun.luwei on 2016/3/17.
 * 错误返回
 */
public class ErrorBean implements Serializable {
    private static final long serialVersionUID = -3745028092791488888L;

    public String msg;//返回错误信息
    public String code;//返回错误码
}
