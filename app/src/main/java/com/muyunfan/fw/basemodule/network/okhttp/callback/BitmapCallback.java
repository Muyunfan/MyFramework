package com.muyunfan.fw.basemodule.network.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.muyunfan.fw.basemodule.network.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.callback
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:15
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:15
 * 修改备注：
 */
public abstract class BitmapCallback extends Callback<Bitmap>
{
    @Override
    public Bitmap parseNetworkResponse(Response response , int id) throws Exception
    {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
