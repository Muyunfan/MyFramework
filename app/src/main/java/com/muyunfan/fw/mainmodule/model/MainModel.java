package com.muyunfan.fw.mainmodule.model;

import com.muyunfan.fw.basemodule.model.BaseModel;
import com.muyunfan.fw.basemodule.network.request.AccountRequest;

/**
 * 类名称：com.muyunfan.fw.mainmodule.model
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/10/17.17:02
 * 修改人： 李程伟
 * 修改时间：2017/10/17.17:02
 * 修改备注：
 */
public class MainModel extends BaseModel {

    private static final String BASE_MODEL = "MainModel";
    public static final String GET_STUDENTS = BASE_MODEL + "getStudents";

    public void getStudents(){
        AccountRequest.getInstance(this).getStudents(GET_STUDENTS);
    }

    @Override
    protected void responseSuccess(String requestCode, String url, String data) {
        switch (requestCode){
            case GET_STUDENTS:
                mModelCallBack.success(requestCode,data);
                break;
        }
    }
}
