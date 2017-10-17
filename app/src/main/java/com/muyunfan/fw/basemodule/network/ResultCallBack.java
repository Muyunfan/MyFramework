package com.muyunfan.fw.basemodule.network;

import android.util.Log;

import com.muyunfan.fw.basemodule.network.okhttp.callback.Callback;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 类名称：com.muyunfan.fw.basemodule.request
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.15:05
 * 修改人： 李程伟
 * 修改时间：2017/5/25.15:05
 * 修改备注：
 */
abstract class ResultCallBack extends Callback<String> {

    private static final String TAG = ResultCallBack.class.getSimpleName();
    @Override
    public void onBefore(Request request, int id) {
        Log.e(TAG, "loading...");
    }

    @Override
    public void onAfter(int id) {
        Log.e(TAG, "hbh-okHttp.");
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        ResponseBody body = response.body();
        return getRealString(response.body().bytes());
    }

    private static int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    /**
     * gzip解压，由于android不能正确解读header中的gzip字段，需要处理
     *
     * @param data
     * @return
     */
    public static String getRealString(byte[] data) {
        byte[] h = new byte[2];
        h[0] = (data)[0];
        h[1] = (data)[1];
        int head = getShort(h);
        boolean t = head == 0x1f8b;
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            if (t) {
                in = new GZIPInputStream(bis);
            } else {
                in = bis;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"),
                    1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
