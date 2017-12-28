package com.muyunfan.fw.core.network.okhttp.builder;

import com.muyunfan.fw.core.network.okhttp.request.PostStringRequest;

import okhttp3.MediaType;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.builder
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:14
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:14
 * 修改备注：
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public com.muyunfan.fw.core.network.okhttp.request.RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}