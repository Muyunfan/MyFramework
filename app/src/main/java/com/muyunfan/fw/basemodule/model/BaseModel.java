package com.muyunfan.fw.basemodule.model;

import com.muyunfan.fw.basemodule.bean.BaseResult;
import com.muyunfan.fw.basemodule.network.BaseRequest;
import com.muyunfan.fw.basemodule.network.OnResponse;
import com.muyunfan.fw.widget.utils.common.GsonUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 类名称：com.muyunfan.fw.basemodule.model
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/12.15:08
 * 修改人： 李程伟
 * 修改时间：2017/6/12.15:08
 * 修改备注：
 */
public abstract class BaseModel implements OnResponse {

    public static final int PAGE_SIZE = 10;

    public ModelCallBack mModelCallBack;

    public void setModelCallBack(ModelCallBack mModelCallBack) {
        this.mModelCallBack = mModelCallBack;
    }

    protected abstract void responseSuccess(String requestCode, String url, String data);

    List<String> requestCodeList;

    public void addFliter(String requestCode){
        if(requestCodeList == null){
            requestCodeList = new ArrayList<>();
        }
        requestCodeList.add(requestCode);
    }

    @Override
    public void success(String requestCode, String url, String data) {
        if(requestCodeList != null && requestCodeList.size()>0){
            if(requestCodeList.contains(requestCode)){
                return;
            }
        }
        BaseResult response = GsonUtil.fromJson(data, BaseResult.class);
        if (response == null) {
            mModelCallBack.fail("网络请求异常，请稍后重试");
        } else if (response.code == 0) {
            responseSuccess(requestCode, url, GsonUtil.toJson(response.data));
        } else {
            if (mModelCallBack != null) {
                mModelCallBack.fail(response.message);
            }
        }
    }

    @Override
    public void fail(String error) {
        if (mModelCallBack != null) {
            mModelCallBack.fail(error);
        }
    }
}
