package com.muyunfan.fw.basemodule.network.okhttp.utils;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.utils
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:22
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:22
 * 修改备注：
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
