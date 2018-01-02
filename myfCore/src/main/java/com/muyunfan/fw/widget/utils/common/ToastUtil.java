package com.muyunfan.fw.widget.utils.common;

import android.content.Context;
import android.widget.Toast;

/*
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 */
public class ToastUtil {

    public static Context sContext;

    private ToastUtil() {}

    public static void register(Context context) {
        sContext = context.getApplicationContext();
    }

    private static void check() {
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtils.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }


    public static void showShort(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }


    public static void showLongX2(String message) {
        showLong(message);
        showLong(message);
    }


    public static void showLongX2(int resId) {
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(int resId) {
        showLong(resId);
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(String message) {
        showLong(message);
        showLong(message);
        showLong(message);
    }
    public static void toast(String message) {
//        Toast	toast = Toast.makeText(sContext,
//                message, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        LinearLayout toastView = (LinearLayout) toast.getView();
//        ImageView imageCodeProject = new ImageView(sContext);
////        imageCodeProject.setImageResource(R.mipmap.ic_launcher);
//        toastView.addView(imageCodeProject, 0);
//        toast.show();
        if (sContext == null) {
            Toast.makeText(sContext,message, Toast.LENGTH_SHORT).show();
        }

//        View layout = LayoutInflater.from(sContext).inflate(R.layout.custom_toast, null);
//        // set a message
//        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);
//        toastText.setText(message);
//
//        // Toast...
//        Toast toast = new Toast(sContext);
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
////        toast.setDuration(duration);
//        toast.setView(layout);
//        toast.show();
    }
}
