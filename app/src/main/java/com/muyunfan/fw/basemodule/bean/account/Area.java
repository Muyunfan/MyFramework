package com.muyunfan.fw.basemodule.bean.account;

import java.io.Serializable;

public class Area implements Serializable {

    public double lat;                    //纬度
    public double lng;                    //经度
    public String address;                  //地址

    public String getShortAddress(){
        int index = address.indexOf("市");
        String shortAddress = address.substring(index+1);
        return shortAddress;
    }

}
