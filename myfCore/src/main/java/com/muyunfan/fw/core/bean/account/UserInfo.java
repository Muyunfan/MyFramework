package com.muyunfan.fw.core.bean.account;

import java.io.Serializable;

/**
 * 类名称：com.muyunfan.fw.basemodule.bean.account
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/14.11:13
 * 修改人： 李程伟
 * 修改时间：2017/6/14.11:13
 * 修改备注：
 */
public class UserInfo implements Serializable {

    public String id;               //用户id
    public String phoneNumber;      //用户手机号
    public String address;          //用户地址
    public double latitude;         //纬度
    public double longitude;        //经度
}
