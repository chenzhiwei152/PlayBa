package com.yuanchangyuan.wanbei.ui.bean;

import java.io.Serializable;

/**
 * Created by chen.zhiwei on 2017-6-27.
 */

public class MemberRankBean implements Serializable{

    /**
     * id : 4
     * grade : 1
     * name : 一级会员
     * money : 199
     * discount : 0.9
     */

    private int id;
    private int grade;
    private String name;
    private String money;
    private double discount;
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
