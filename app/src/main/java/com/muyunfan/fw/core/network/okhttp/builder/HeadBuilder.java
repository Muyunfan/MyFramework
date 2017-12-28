package com.muyunfan.fw.core.network.okhttp.builder;

import com.muyunfan.fw.core.network.okhttp.OkHttpUtils;
import com.muyunfan.fw.core.network.okhttp.request.OtherRequest;
import com.muyunfan.fw.core.network.okhttp.request.RequestCall;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.builder
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:12
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:12
 * 修改备注：
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}