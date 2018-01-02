package com.muyunfan.fw.core.network;

/**
 * 类名称：com.muyunfan.fw.basemodule.request
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.15:01
 * 修改人： 李程伟
 * 修改时间：2017/5/25.15:01
 * 修改备注：
 */
public interface OnResponse {

    void success(String requestCode, String url, String data);

    void fail(String error);
}
