package com.muyunfan.fw.basemodule.network.request;

import android.net.Uri;

import com.muyunfan.fw.basemodule.code.APICode;
import com.muyunfan.fw.basemodule.network.BaseRequest;
import com.muyunfan.fw.basemodule.network.OnResponse;
import com.muyunfan.fw.widget.utils.common.MD5;

import java.util.HashMap;

/**
 * 类名称：com.muyunfan.fw.basemodule.network
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/13.11:52
 * 修改人： 李程伟
 * 修改时间：2017/6/13.11:52
 * 修改备注：
 */
public class AccountRequest extends BaseRequest{

    private OnResponse mOnResponse;
    private static AccountRequest instance;

    private void setOnResponse(OnResponse onResponse) {
        this.mOnResponse = onResponse;
    }

    public static AccountRequest getInstance(OnResponse onResponse){
        if(null == instance){
            synchronized (AccountRequest.class) {
                if (instance == null) {
                    instance = new AccountRequest();
                }
            }
        }
        instance.setOnResponse(onResponse);
        return instance;
    }

    /*
    密码登录
     */
    public void login(String requestCode,String phone,String password){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", phone);
        try {
            params.put("password", MD5.md5(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestDataByPost(requestCode, APICode.LOGIN, params, mOnResponse);
    }

}
