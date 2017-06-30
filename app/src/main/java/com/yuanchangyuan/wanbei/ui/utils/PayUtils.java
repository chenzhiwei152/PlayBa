package com.yuanchangyuan.wanbei.ui.utils;

/**
 * Created by chen.zhiwei on 2017-6-30.
 */

public class PayUtils {

    /**
     * 微信支付
     */
    private void weixinPay() {
        //1.创建微信支付请求
//        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
//                .with(this) //activity实例
//                .setAppId(appid) //微信支付AppID
//                .setPartnerId(partnerid)//微信支付商户号
//                .setPrepayId(prepayid)//预支付码
////								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
//                .setNonceStr(noncestr)
//                .setTimeStamp(timestamp)//时间戳
//                .setSign(sign)//签名
//                .create();
        //2.发送微信支付请求
//        PayAPI.getInstance().sendPayRequest(wechatPayReq);


        //关于微信支付的回调
        //wechatPayReq.setOnWechatPayListener(new OnWechatPayListener);
    }


    /**
     * 支付宝支付
     */
    public void alPay() {
        //1.创建支付宝支付订单的信息
//        String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
//                .setPartner(partner) //商户PID || 签约合作者身份ID
//                .setSeller(seller)  // 商户收款账号 || 签约卖家支付宝账号
//                .setOutTradeNo(outTradeNo) //设置唯一订单号
//                .setSubject(orderSubject) //设置订单标题
//                .setBody(orderBody) //设置订单内容
//                .setPrice(price) //设置订单价格
//                .setCallbackUrl(callbackUrl) //设置回调链接
//                .createOrderInfo(); //创建支付宝支付订单信息
//
//
//        //2.签名  支付宝支付订单的信息 ===>>>  商户私钥签名之后的订单信息
//        //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
//        String signAliOrderInfo = getSignAliOrderInfoFromServer(rawAliOrderInfo);
//
//        //3.发送支付宝支付请求
//        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
//                .with(activity)//Activity实例
//                .setRawAliPayOrderInfo(rawAliOrderInfo)//支付宝支付订单信息
//                .setSignedAliPayOrderInfo(signAliOrderInfo) //设置 商户私钥RSA加密后的支付宝支付订单信息
//                .create()//
//                .setOnAliPayListener(null);//
//        PayAPI.getInstance().sendPayRequest(aliPayReq);

        //关于支付宝支付的回调
        //aliPayReq.setOnAliPayListener(new OnAliPayListener);
    }

}
