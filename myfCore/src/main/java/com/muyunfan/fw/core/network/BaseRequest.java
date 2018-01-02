package com.muyunfan.fw.core.network;

import android.util.Log;

import com.muyunfan.fw.core.bean.BaseResult;
import com.muyunfan.fw.core.bean.EventCenter;
import com.muyunfan.fw.core.bean.account.Student;
import com.muyunfan.fw.core.bean.account.StudentInList;
import com.muyunfan.fw.core.code.APICode;
import com.muyunfan.fw.core.code.AccountCode;
import com.muyunfan.fw.core.network.okhttp.OkHttpUtils;
import com.muyunfan.fw.mainmodule.model.MainModel;
import com.muyunfan.fw.widget.utils.common.GsonUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;
import com.muyunfan.fw.core.network.okhttp.builder.GetBuilder;
import com.muyunfan.fw.core.network.okhttp.builder.PostStringBuilder;

import org.greenrobot.eventbus.EventBus;

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
    protected CustomGetThread threadGet = null;
    protected CustomUrlGetThread threadUrlGet = null;
    protected CustomPostJsonThread threadPostJson = null;

    //    private final String baseUrl = APICode.BASE_URL;
    private final String baseUrl = APICode.TEST_BASE_URL;

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Accept-Encoding", "gzip");
        return headers;
    }

    protected void requestDataByUrl(String requestCode, String url, OnResponse onResponse) {
        if (threadUrlGet != null) {
            threadUrlGet.interrupt();
            threadUrlGet = null;
        }
        threadUrlGet = new CustomUrlGetThread(requestCode, url, onResponse);
        threadUrlGet.start();
    }

    /**
     * get方式请求数据
     *
     * @param requestCode 请求码
     * @param url         url
     * @param params      参数
     * @param onResponse  回掉
     */
    protected void requestDataByGet(String requestCode, String url, HashMap<String, String> params, OnResponse onResponse) {
        if (threadGet != null) {
            threadGet.interrupt();
            threadGet = null;
        }
        threadGet = new CustomGetThread(requestCode, url, params, onResponse);
        threadGet.start();
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
        if (threadPostJson != null) {
            threadPostJson.interrupt();
            threadPostJson = null;
        }
        threadPostJson = new CustomPostJsonThread(requestCode, url, GsonUtil.toJson(params), onResponse);
        threadPostJson.start();
    }

    protected void requestDataByPostObject(String requestCode, String url, HashMap<String, Object> params, OnResponse onResponse) {
        if (threadPostJson != null) {
            threadPostJson.interrupt();
            threadPostJson = null;
        }
        threadPostJson = new CustomPostJsonThread(requestCode, url, GsonUtil.toJson(params), onResponse);
        threadPostJson.start();
    }

    /**
     * post方式提交json
     *
     * @param requestCode 请求码
     * @param url         url
     * @param contentJson json字符串
     * @param onResponse  回掉
     */
    protected void requestDataByPostJson(String requestCode, String url, String contentJson, OnResponse onResponse) {
        if (threadPostJson != null) {
            threadPostJson.interrupt();
            threadPostJson = null;
        }
        threadPostJson = new CustomPostJsonThread(requestCode, url, contentJson, onResponse);
        threadPostJson.start();
    }

    /**
     * Get方式
     */
    protected class CustomUrlGetThread extends Thread {

        private String requestCode;
        private String url;
        private OnResponse onResponse;
        private HashMap<String, String> headers;
        private int responseCode;
        private String responseJson;

        public CustomUrlGetThread(String requestCode, String url, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.onResponse = onResponse;
            this.headers = getHeaders();
        }

        @Override
        public void run() {
            super.run();
            GetBuilder getBuilder = OkHttpUtils.get();
//            getBuilder.url(baseUrl + url);

            if (headers != null) {
                getBuilder.params(headers);
            }

            final long startTime = System.currentTimeMillis();
            getBuilder.url(baseUrl + url).build().execute(new ResultCallBack() {

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
                        EventBus.getDefault().post(new EventCenter<>(AccountCode.USER_INFO_ERROR));
                        onResponse.fail("请求认证已失效，请重新登录");
                    } else {
                        onResponse.success(requestCode, baseUrl + url, responseJson);
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e(TAG, "onResponse：complete");
                    switch (id) {
                        case 100:
                            Log.e(TAG, "http");
                            break;
                        case 101:
                            Log.e(TAG, "https");
                            break;
                    }
                    LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                    LogUtil.i("response:" + response);
                    LogUtil.i("-------------------------------华丽的分割线------------------------------");
                    onResponse.success(requestCode, baseUrl + url, response);
                }
            });
        }
    }

    /**
     * Get方式
     */
    protected class CustomGetThread extends Thread {

        private String requestCode;
        private String url;
        private OnResponse onResponse;
        private HashMap<String, String> headers;
        private HashMap<String, String> params;
        private int responseCode;
        private String responseJson;

        public CustomGetThread() {
        }

        public CustomGetThread(String requestCode, String url, HashMap<String, String> params, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.params = params;
            this.onResponse = onResponse;
            this.headers = getHeaders();
        }

        @Override
        public void run() {
            super.run();
            GetBuilder getBuilder = OkHttpUtils.get();
            if (headers != null) {
                getBuilder.params(headers);
            }
            if (params != null)
                getBuilder.params(params);

            final long startTime = System.currentTimeMillis();
            getBuilder.url(baseUrl + url).build().execute(new ResultCallBack() {

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
                        EventBus.getDefault().post(new EventCenter<>(AccountCode.USER_INFO_ERROR));
                        onResponse.fail("请求认证已失效，请重新登录");
                    } else {
                        onResponse.success(requestCode, baseUrl + url, responseJson);
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e(TAG, "onResponse：complete");
                    switch (id) {
                        case 100:
                            Log.e(TAG, "http");
                            break;
                        case 101:
                            Log.e(TAG, "https");
                            break;
                    }
                    LogUtil.i("request:" + GsonUtil.toJson(params));
                    LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                    LogUtil.i("response:" + response);
                    LogUtil.i("-------------------------------华丽的分割线------------------------------");
                    onResponse.success(requestCode, baseUrl + url, response);
                }
            });
        }
    }

    /**
     * Post方式提交Json格式字符串
     */
    protected class CustomPostJsonThread extends Thread {
        private String requestCode;
        private String url;
        private HashMap<String, String> headers;
        private String contentJson;
        private OnResponse onResponse;
        private int responseCode;
        private String responseJson;

        public CustomPostJsonThread() {
        }

        public CustomPostJsonThread(String requestCode, String url, String contentJson, OnResponse onResponse) {
            this.requestCode = requestCode;
            this.url = url;
            this.contentJson = contentJson;
            this.onResponse = onResponse;
            this.headers = getHeaders();
        }

        @Override
        public void run() {
            super.run();
            /**
             * 这是测试demo
             */
            if (MainModel.GET_STUDENTS.equals(requestCode)) {
                StudentInList studentInList = new StudentInList();
                studentInList.students = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Student student = new Student();
                    student.age = 17+i;
                    student.name = "Wanger"+i;
                    student.school = "ShangHai"+(i+1)+"zhong";
                    studentInList.students.add(student);
                }
                BaseResult baseResult = new BaseResult();
                baseResult.code = 0;
                baseResult.message = "获取数据成功";
                baseResult.data = studentInList;
                onResponse.success(requestCode,url,GsonUtil.toJson(baseResult));
                return;
            }

            /**
             * 正式的网络请求
             */
            PostStringBuilder postStringBuilder = OkHttpUtils.postString();

            if (headers != null)
                postStringBuilder.headers(headers);
            postStringBuilder.mediaType(MediaType.parse("application/json;charset=utf-8"));
            if (contentJson != null)
                postStringBuilder.content(contentJson);

            final long startTime = System.currentTimeMillis();
            // TODO
            postStringBuilder.url(baseUrl + url).build().execute(new ResultCallBack() {
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
                        EventBus.getDefault().post(new EventCenter<>(AccountCode.USER_INFO_ERROR));
                        onResponse.fail("请求认证已失效，请重新登录");
                    } else {
                        onResponse.success(requestCode, baseUrl + url, responseJson);
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e(TAG, "onResponse：complete");
                    switch (id) {
                        case 100:
                            Log.e(TAG, "http");
                            break;
                        case 101:
                            Log.e(TAG, "https");
                            break;
                    }
                    LogUtil.i("request:" + contentJson);
                    LogUtil.i(baseUrl + url + " =" + (System.currentTimeMillis() - startTime) + "ms");
                    LogUtil.i("response:" + response);
                    LogUtil.i("-------------------------------华丽的分割线------------------------------");
                    onResponse.success(requestCode, baseUrl + url, response);
                }
            });
        }
    }
}
