package com.muyunfan.fw.widget.utils.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.muyunfan.fw.widget.utils.mobile.RegIDCard;

import java.util.List;

/**
 * 类名：CheckUtil
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 */

public class CheckUtil {

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null") || value.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean noChinese(String value) {//全是汉字返回false
        String reg = "[\\u4e00-\\u9fa5]+";//汉字
        return !value.matches(reg);
    }

    public static boolean isEmpty1(String value) {
        if (value == null || value.equals("") || value.equals("null") || value.equals("---")) {
            return true;
        }
        return false;
    }

    /**
     * 检测手机号
     *
     * @param phone
     * @return
     */
    public static String checkPhone(String phone) {
        String result = null;
        if (isEmpty(phone)) {
            result = "请输入账号";
//        } else if (!"1".equals(phone.substring(0, 1))) {
//            result = "手机号首位必须为“1”";
//        } else if (!(phone.length() == 11)) {
//            result = "手机号必须为11位";
//        } else if (!RegIDCard.isNumeric(phone)) {
//            result = "手机号必须为数字";
        }
        return result;
    }

    /**
     * 检测验证码
     *
     * @param code
     * @return
     */
    public static String checkVerificationCode(String code) {
        String result = null;
        if (isEmpty(code)) {
            result = "请输入验证码";
        } else if (!(code.length() == 4)) {
            result = "验证码必须为4位";
        } else if (!RegIDCard.isNumeric(code)) {
            result = "验证码必须为数字";
        }
        return result;
    }

    /**
     * 检测密码
     *
     * @param psw
     * @return
     */
    public static String checkPsw(String psw) {
        String result = null;
        if (isEmpty(psw)) {
            result = "请输入密码";
        } else if (psw.length() < 6) {
            result = "密码长度至少6位";
        }
//        else if (!RegIDCard.checkPassword(psw)) {
//            result = "密码必须是字母加数字";
//        }
        return result;
    }

    /**
     * checkSelfPermission
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        boolean flag = true;
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            flag = false;
        }
        if (flag == false) {
            flag = isRecordGranted(context, permission);
        }
        return flag;
    }

    private static boolean isRecordGranted(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission, context.getPackageName()) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测是否有sd卡
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    检测是否安装某APK
    baidu:com.baidu.BaiduMap
    amap:com.autonavi.minimap
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
