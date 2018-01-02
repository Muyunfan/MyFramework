package com.muyunfan.fw.core.network;

import android.util.Log;

import com.muyunfan.fw.core.bean.BaseResult;
import com.muyunfan.fw.core.bean.account.Student;
import com.muyunfan.fw.core.bean.account.StudentInList;
import com.muyunfan.fw.core.code.APICode;
import com.muyunfan.fw.core.network.okhttp.OkHttpUtils;
import com.muyunfan.fw.mainmodule.model.MainModel;
import com.muyunfan.fw.widget.utils.common.GsonUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;
import com.muyunfan.fw.core.network.okhttp.builder.GetBuilder;
import com.muyunfan.fw.core.network.okhttp.builder.PostStringBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * 类名称：com.muyunfan.fw.BaseModule.Request
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.10:44
 * 修改人： 李程伟
 * 修改时间：2017/5/25.10:44
 * 修改备注：
 */
public class BaseRequest {

    private static final String TAG = BaseRequest.class.getSimpleName();

    //    private final String baseUrl = APICode.BASE_URL;
    private final String baseUrl = APICode.TEST_BASE_URL;

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Accept-Encoding", "gzip");
        return headers;
    }

    /**
     * get url方式请求数据
     *
     * @param requestCode 请求码
     * @param url         url
     * @param onResponse  回掉
     */
    protected void requestDataByUrl(final String requestCode, final String url, final OnResponse onResponse) {
        HashMap<String, String> headers = getHeaders();

        GetBuilder getBuilder = OkHttpUtils.get();
//            getBuilder.url(baseUrl + url);

        if (headers != null) {
            getBuilder.params(headers);
        }

        final long startTime = System.currentTimeMillis();
        getBuilder.url(baseUrl + url).build().execute(new ResultCallBack() {
            int responseCode = -1;
            String responseJson = "";

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                responseCode = response.code();
                responseJson = getRealString(response.body().bytes());
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                LogUtil.i("exception:" + e.toString());
                LogUtil.i("response:" + responseJson);
                LogUtil.i("-------------------------------华丽的分割线------------------------------");
                if (responseCode == 401) {
                    /** 请求异常，操作相关后续 */
//                    EventBus.getDefault().post(new EventCenter<>(AppCode.APP_ERROR));
                    onResponse.fail("请求认证已失效，请重新登录");
                } else {
                    onResponse.success(requestCode, baseUrl + url, responseJson);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse：complete");
                LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                LogUtil.i("response:" + response);
                LogUtil.i("-------------------------------华丽的分割线------------------------------");
                onResponse.success(requestCode, baseUrl + url, response);
            }
        });
    }


    /**
     * get方式请求数据
     *
     * @param requestCode 请求码
     * @param url         url
     * @param params      参数
     * @param onResponse  回掉
     */
    protected void requestDataByGet(final String requestCode, final String url, final HashMap<String, String> params, final OnResponse onResponse) {

        HashMap<String, String> headers = getHeaders();

        final GetBuilder getBuilder = OkHttpUtils.get();
        if (headers != null) {
            getBuilder.params(headers);
        }
        if (params != null) {
            getBuilder.params(params);
        }

        final long startTime = System.currentTimeMillis();
        getBuilder.url(baseUrl + url).build().execute(new ResultCallBack() {
            int responseCode = -1;
            String responseJson = "";

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                responseCode = response.code();
                responseJson = getRealString(response.body().bytes());
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                LogUtil.i("request:" + GsonUtil.toJson(params));
                LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                LogUtil.i("exception:" + e.toString());
                LogUtil.i("response:" + responseJson);
                LogUtil.i("-------------------------------华丽的分割线------------------------------");
                if (responseCode == 401) {
                    /** 请求异常，操作相关后续 */
//                    EventBus.getDefault().post(new EventCenter<>(AppCode.APP_ERROR));
                    onResponse.fail("请求认证已失效，请重新登录");
                } else {
                    onResponse.success(requestCode, baseUrl + url, responseJson);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse：complete");
                LogUtil.i("request:" + GsonUtil.toJson(params));
                LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                LogUtil.i("response:" + response);
                LogUtil.i("-------------------------------华丽的分割线------------------------------");
                onResponse.success(requestCode, baseUrl + url, response);
            }
        });
    }

    /**
     * post方式提交map
     *
     * @param requestCode 请求码
     * @param url         url
     * @param params      参数
     * @param onResponse  回掉
     */
    protected void requestDataByPost(String requestCode, String url, HashMap<String, String> params, OnResponse onResponse) {
        requestDataByPostJson(requestCode, url, GsonUtil.toJson(params), onResponse);
    }

    protected void requestDataByPostObject(String requestCode, String url, HashMap<String, Object> params, OnResponse onResponse) {
        requestDataByPostJson(requestCode, url, GsonUtil.toJson(params), onResponse);
    }

    /**
     * post方式提交json
     *
     * @param requestCode 请求码
     * @param url         url
     * @param contentJson json字符串
     * @param onResponse  回掉
     */
    protected void requestDataByPostJson(final String requestCode, final String url, final String contentJson, final OnResponse onResponse) {
        HashMap<String, String> headers = getHeaders();

        /**
         * 这是测试demo
         */
        if (MainModel.GET_STUDENTS.equals(requestCode)) {
            StudentInList studentInList = new StudentInList();
            studentInList.students = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Student student = new Student();
                student.age = 17 + i;
                student.name = "Wanger" + i;
                student.school = "ShangHai" + (i + 1) + "zhong";
                studentInList.students.add(student);
            }
            BaseResult baseResult = new BaseResult();
            baseResult.code = 0;
            baseResult.message = "获取数据成功";
            baseResult.data = studentInList;
            onResponse.success(requestCode, url, GsonUtil.toJson(baseResult));
            return;
        }

        /**
         * 正式的网络请求
         */
        PostStringBuilder postStringBuilder = OkHttpUtils.postString();

        if (headers != null) {
            postStringBuilder.headers(headers);
        }

        postStringBuilder.mediaType(MediaType.parse("application/json;charset=utf-8"));
        if (contentJson != null) {
            postStringBuilder.content(contentJson);
        }

        final long startTime = System.currentTimeMillis();
        postStringBuilder.url(baseUrl + url).build().execute(new ResultCallBack() {
            int responseCode = -1;
            String responseJson = "";

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                responseCode = response.code();
                responseJson = getRealString(response.body().bytes());
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                LogUtil.i("request:" + contentJson);
                LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                LogUtil.i("exception:" + e.toString());
                LogUtil.i("response:" + responseJson);
                LogUtil.i("-------------------------------华丽的分割线------------------------------");
                if (responseCode == 401) {
                    /** 请求异常，操作相关后续 */
//                    EventBus.getDefault().post(new EventCenter<>(AppCode.APP_ERROR));
                    onResponse.fail("请求认证已失效，请重新登录");
                } else {
                    onResponse.success(requestCode, baseUrl + url, responseJson);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse：complete");
                LogUtil.i("request:" + contentJson);
                LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                LogUtil.i("response:" + response);
                LogUtil.i("-------------------------------华丽的分割线------------------------------");
                onResponse.success(requestCode, baseUrl + url, response);
            }
        });
    }
}
