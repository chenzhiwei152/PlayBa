package com.yuanchangyuan.wanbei.ui.bean;

import java.io.Serializable;

/**
 * Created by chen.zhiwei on 2017-6-23.
 */

public class ShoppingAddressListItemBean implements Serializable {

    /**
     * id : 1
     * name : 小明爸爸
     * detail : 成都市武侯区341苑
     * phone : 13691525924
     * userId : 1
     */

    private int id;
    private String name;
    private String province;
    private String city;
    private String area;
    private String detail;
    private String phone;
    private int userId;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
