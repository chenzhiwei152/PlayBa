package com.yuanchangyuan.wanbei.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by zhao.wenchao on 2017/5/19.
 * email: zhao.wenchao@jyall.com
 * introduce:
 */

public class SchemeToJumpUtils {
    private static SchemeToJumpUtils instance;

    private SchemeToJumpUtils() {
    }

    public static SchemeToJumpUtils getInstance() {
        if (null == instance) {
            synchronized (SchemeToJumpUtils.class) {
                if (null == instance) {
                    instance = new SchemeToJumpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * @param context
     * @param url     Scheme
     * @return return true 安装了应用跳转  false 未安装应用
     */
    public boolean startApp(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();//判断Scheme是否有效
        if (isValid) {// 安装了该应用
            context.startActivity(intent);
            return true;
        } else {// 按包名启动 若再无效   跳转H5
            if (!startAppByPkg(context, url)) {
                return false;
            }else {
                return true;
            }
        }

    }

    /**
     * @param context
     * @param url     scheme
     * @return false 不能通过包名启动 true 可以通过包名启动
     */
    public boolean startAppByPkg(Context context, String url) {
        try {
            if (url.equalsIgnoreCase(AppSchemeAndPkgEnum.CLOUD_SCHEME.getValue())) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(AppSchemeAndPkgEnum.CLOUD_PKG.getValue());
                context.startActivity(intent);
            } else if (url.equalsIgnoreCase(AppSchemeAndPkgEnum.JYALL_SCHEME.getValue())) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(AppSchemeAndPkgEnum.JYALL_PKG.getValue());
                context.startActivity(intent);
            } else if (url.equalsIgnoreCase(AppSchemeAndPkgEnum.YSALL_SCHEME.getValue())) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(AppSchemeAndPkgEnum.YSALL_PKG.getValue());
                context.startActivity(intent);
            } else if (url.equalsIgnoreCase(AppSchemeAndPkgEnum.SHOUBA_SCHEME.getValue())) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(AppSchemeAndPkgEnum.SHOUBA_PKG.getValue());
                context.startActivity(intent);
            }else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
