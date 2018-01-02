package com.muyunfan.fw.core.network.okhttp.builder;

import com.muyunfan.fw.core.network.okhttp.request.PostFileRequest;

import java.io.File;

import okhttp3.MediaType;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.builder
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:13
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:13
 * 修改备注：
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public com.muyunfan.fw.core.network.okhttp.request.RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}