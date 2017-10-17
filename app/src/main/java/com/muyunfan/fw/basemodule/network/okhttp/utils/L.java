package com.muyunfan.fw.basemodule.network.okhttp.utils;

import android.util.Log;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.utils
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:22
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:22
 * 修改备注：
 */
public class L
{
    private static boolean debug = false;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}
