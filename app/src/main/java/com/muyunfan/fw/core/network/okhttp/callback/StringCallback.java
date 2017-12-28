package com.muyunfan.fw.core.network.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.callback
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:16
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:16
 * 修改备注：
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
