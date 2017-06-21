package com.yuanchangyuan.wanbei.base;

/**
 * Created by sun.luwei on 2016/12/20.
 */

public class Constants {

    public static String APP_KEY = "RrdOnO5IChenLaoMoJKq4QLM96smuRrz";
    public static String JUMP_EXTRA = "jump_extra";
    public static String STRING_TAG = "String_Tag";
    public static String STRING_TAG_ANOTHER = "String_Tag_Another";
    public static String STRING_TAG_GCID = "String_Tag_Three";
    public static String INT_TAG = "int_tag";
    public static String SELECTED_CITY_ID = "SELECTED_CITY_ID";
    public static String SELECTED_CITY_NAME = "SELECTED_CITY_NAME";
    public static String DEFAULT_CITYID = "110100000";//默认城市 Id 北京
    public static int INDUSTRY_ORDER_INDUSTRY = 8;//医疗订单业态默认8
    public static String NEWS_URL = "http://m.ysall.cn/article/yiliao";//资讯URL
    public static String Overseas_URL = "https://m.ysall.cn/yiliao/overseas.html";//海外医疗 URL
    public static String SP_SERVICE_URL = "https://m.ysall.cn/medical/special-services/index.html";//特需服务
    public static String ABOUT_US_URL = "http://m.ysall.cn/yiliao/about-us.html";//关于我们
    public static String AGREEMENT_URL = "http://m.ysall.cn/yiliao/agreement.html";//用户协议
    public static String DOCTOR_AGREEMENT_URL = "https://m.ysall.cn/medical/health-copywriting/agreement.html";//用户协议
    public static String EMPTY_VIEW_TAG = "empty_view_tag";//模板空布局tag

    public static class ErrorCode {
        public static String check_code = "400001039";//验证码错误
    }

    public static class Tag {
        public static final int SHOW_CHECK_CODE = 0x20;//显示验证码
        public static final int HIDE_CHECK_CODE = 0x21;//隐藏证码
        public static final int REGIST_SUCCESS = 0x22;//注册成功
        public static final int LOGIN_FAILURE = 0x40;//登录失败
        public static int User_Refresh = 0x1;//刷新用户信息
        public static final int CHANGE_PASSWORD_SUCCESS = 0x23;//修改密码成功
        public static int User_Logout = 0x2;//退出登录
        public static int Change_City = 0x24;//选择城市
        public static int SHOW_INDEX = 0x25;//首页选项卡
        public static int MESURE_1002 = 0x26;//测量1002模板高度
        public static int SHOW_POP_AD = 0x27;//弹窗 广告
        public static int SHOW_POP_AGREEMENT = 0x28;//医生第一次登陆 需同意协议
        public static int MALL_LOAD_LIST = 0x29;//健康商城加载数据
        public static int MALL_SET_DEFAULT = 0x30;//健康商城 设置默认值  刷新
        public static int DOC_LOAD_LIST = 0x31;//医生列表加载数据
        public static int DOC_SET_DEFAULT = 0x32;//医生列表 设置默认值  刷新
        public static int INDICATOR_CHANGE = 0x34;//我的健康档案指示器 切换
        public static int UPLOAD_INFO = 0x35;//我的健康档案上传编辑数据
        public static int EDITABLE = 0x36;//我的健康档案可编辑
        public static int NO_EDITABLE = 0x37;//我的健康档案不可编辑
        public static int TOGGLE = 0x38;//编辑状态切换
        public static int EDITING = 0x39;//正在编辑
        public static int ON_RESULT = 0x40;//onactivity result
        public static int CANCLE_EDIT = 0x41;//取消编辑
        public static int AGREE_ACTION = 0x42;//同意协议
        public static int SHOW_ERROR = 0x43;//显示错误页面
        public static int SHOW_NORMAL = 0x44;//显示正常页面
        public static int SHOW_NONET = 0x45;//显示无网络页面
        public static int REFRESH = 0x46;//刷新页面
        public static int CHECK_AGREE = 0x47;//解决 viewpager预加载 医生协议弹窗显示

        public static int PAY_SUCCESS = 0x48;//支付成功
        public static int PAY_FAIL = 0x48;//支付失败
        public static int REFRESH_ORDER_LIST = 0x49;//刷新订单
        public static int REFRESH_HEALTH_RECORD = 0x50;//刷新健康档案
    }


    public static class PopupAds {//弹窗广告 页面ID
        public static final String P_INDEX = "ylIndexPopAds";//首页
        public static final String P_TY_ID = "yljtysProDes";//家庭医生
        public static final String P_JKTJ_ID = "yljktjProDes";//健康体检
        public static final String P_YNZZ_ID = "ylynzzProDes";//疑难杂症

    }
    public static class COUBUB{
        public static class PAGE{
            public static final String HOME="Android_SYYL_201704_PAGE";//首页
            public static final String FIND_DOCTOR="Android_ZYSYL_201704_PAGE";// 找医生
            public static final String HEALTH_MALL="Android_JKTJYL_201704_PAGE";//健康体检
            public static final String FAMILY_DOCTOR="Android_JTYSYL_201704_PAGE";//家庭医生
            public static final String DIFFICULT_DISORDERS="Android_YNZZYL_201704_PAGE";//疑难重症
            public static final String EXPERT_DOCTOR="Android_ZJHZYL_201704_PAGE";//专家会诊
            public static final String FAMILY_EXPERT_DOCTOR="Android_YSZJLBYYL_201704_PAGE";//医生和专家列表页
            public static final String MY_DOCTOR="Android_WDYSYL_201704_PAGE";//我的医生
            public static final String MY_PATIENTS="Android_WDHZYL_201704_PAGE";//我的患者
            public static final String MY_HEATH_RECORD="Android_WDJKDAYL_201704_PAGE";//我的健康档案
            public static final String DETAIL="Android_SPXQYYL_201704_PAGE";//我的健康档案
        }
    }
}
