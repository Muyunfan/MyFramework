package com.muyunfan.fw.core.model;

/**
 * 类名称：com.muyunfan.fw.basemodule.modle
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.15:16
 * 修改人： 李程伟
 * 修改时间：2017/5/25.15:16
 * 修改备注：
 */
public interface ModelCallBack {

    void success(String requestCode,Object data);

    void fail(String error);
}
