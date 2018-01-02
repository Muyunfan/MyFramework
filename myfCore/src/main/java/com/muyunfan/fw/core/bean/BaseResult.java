package com.muyunfan.fw.core.bean;

import java.io.Serializable;

/**
 * 类名称：com.muyunfan.fw.basemodule.bean
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.15:02
 * 修改人： 李程伟
 * 修改时间：2017/5/25.15:02
 * 修改备注：
 */
public class BaseResult implements Serializable {

    public int code;
    public Object data;
    public String message;
}
