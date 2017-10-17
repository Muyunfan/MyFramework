package com.muyunfan.fw.basemodule.network.okhttp.request;

import com.muyunfan.fw.basemodule.network.okhttp.request.OkHttpRequest;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.request
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:19
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:19
 * 修改备注：
 */
public class GetRequest extends OkHttpRequest
{
    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id)
    {
        super(url, tag, params, headers,id);
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return builder.get().build();
    }


}