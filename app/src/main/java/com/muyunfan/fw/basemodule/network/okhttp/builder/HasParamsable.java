package com.muyunfan.fw.basemodule.network.okhttp.builder;

import com.muyunfan.fw.basemodule.network.okhttp.builder.OkHttpRequestBuilder;

import java.util.Map;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.builder
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:12
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:12
 * 修改备注：
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}