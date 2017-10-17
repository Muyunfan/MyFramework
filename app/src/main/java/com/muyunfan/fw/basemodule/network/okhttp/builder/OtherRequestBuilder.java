package com.muyunfan.fw.basemodule.network.okhttp.builder;

import com.muyunfan.fw.basemodule.network.okhttp.request.OtherRequest;

import okhttp3.RequestBody;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.builder
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:13
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:13
 * 修改备注：
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder>
{
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method)
    {
        this.method = method;
    }

    @Override
    public com.muyunfan.fw.basemodule.network.okhttp.request.RequestCall build()
    {
        return new OtherRequest(requestBody, content, method, url, tag, params, headers,id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody)
    {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content)
    {
        this.content = content;
        return this;
    }


}