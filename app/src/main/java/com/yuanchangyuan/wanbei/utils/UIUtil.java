package com.yuanchangyuan.wanbei.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yuanchangyuan.wanbei.base.BaseContext;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 公用的UI工具类
 */
public class UIUtil {

    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    /**
     * @param context
     * @param dipValue
     * @return
     * @Description: dp转px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 比较真实完整的判断身份证号码的工具
     *
     * @param IdCard 用户输入的身份证号码
     * @return [true符合规范, false不符合规范]
     */
    public static boolean isRealIDCard(String IdCard) {
        if (IdCard != null) {
            int correct = new IdCardUtil(IdCard).isCorrect();
            if (0 == correct) {// 符合规范
                return true;
            }
        }
        return false;
    }
    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 计算两个日期型的时间相差多少时间
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static String twoDateDistance(Date startDate, Date endDate) {

        if (startDate == null || endDate == null) {
            return null;
        }
        long timeLong = endDate.getTime() - startDate.getTime();
//        if (timeLong<60*1000)
//            return timeLong/1000 + "秒前";
//        else if (timeLong<60*60*1000){
//            timeLong = timeLong/1000 /60;
//            return timeLong + "分钟前";
//        }
//        else
        if (timeLong < 60 * 60 * 24 * 1000) {
            timeLong = timeLong / 60 / 60 / 1000;
            return timeLong + "小时前";
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
            timeLong = timeLong / 1000 / 60 / 60 / 24;
            return timeLong + "天前";
        }
        return "";
//        else if (timeLong<60*60*24*1000*7*4){
//            timeLong = timeLong/1000/ 60 / 60 / 24/7;
//            return timeLong + "周前";
//        }
//        else {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//            return sdf.format(startDate);
//        }
    }

    /**
     * 上次点击时间
     */
    private static long lastClickTime;

    /**
     * 防止View连续快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long spaceTime = currentTime - lastClickTime;

        if (0 < spaceTime && spaceTime < 1000) {
            return true;
        }
        lastClickTime = currentTime;

        return false;
    }


    /**
     * 获取当前日期
     *
     * @return
     */
    public static String nowDateTime() {
        Date curDate = new Date(System.currentTimeMillis());
        return dateFormater.format(curDate);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty())
            format = "MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds
     * @return
     */
    public static String timeStamp2Date2(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }

        return dateFormater.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds
     * @return
     */
    public static String timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 根据data获取相应的格式时间
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTime(Date date, String formate) {
        SimpleDateFormat format;
        if (!TextUtils.isEmpty(formate)) {
            format = new SimpleDateFormat(formate);
        } else {

            format = new SimpleDateFormat("yyyy-MM-dd");
        }
        return format.format(date);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    //  输出结果：
    //  timeStamp=1417792627
    //  date=2014-12-05 23:17:07
    //  1417792627
    public static void main(String[] args) {
        String timeStamp = timeStamp();
        System.out.println("timeStamp=" + timeStamp);

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(timeStamp2);
    }

    /**
     * 文件转换成byte数组
     *
     * @param filePath
     * @return
     */
    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * byte数组转换成文件
     *
     * @param filePath
     * @return
     */
    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param ctx
     * @return 获取屏幕 宽度
     */
    public static int getScreenWidth(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * @param ctx
     * @return 获取屏幕 高度
     */
    public static int getScreenHeight(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 输入法隐藏，显示
     */
    public static void ShowOrHideSoftInput(Activity activity, boolean show) {
        InputMethodManager imm = (InputMethodManager) BaseContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {
            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null)
                    imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_FORCED);
            }
        } else {

            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }


    }

    /**
     * Toast
     *
     * @param content
     */
    private static Toast toast;

    public static void showToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(BaseContext.getInstance(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showToast(int resID) {
        if (toast == null) {
            toast = Toast.makeText(BaseContext.getInstance(), resID, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resID);
        }
        toast.show();
    }

}
