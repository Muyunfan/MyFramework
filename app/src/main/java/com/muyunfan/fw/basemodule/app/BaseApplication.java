package com.muyunfan.fw.basemodule.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.muyunfan.fw.basemodule.network.okhttp.OkHttpUtils;
import com.muyunfan.fw.widget.utils.common.LogUtil;
import com.muyunfan.fw.basemodule.network.okhttp.cookie.CookieJarImpl;
import com.muyunfan.fw.basemodule.network.okhttp.cookie.store.PersistentCookieStore;
import com.muyunfan.fw.basemodule.network.okhttp.https.HttpsUtils;
import com.muyunfan.fw.basemodule.network.okhttp.log.LoggerInterceptor;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * 类名称：com.muyunfan.fw.BaseModule.App
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.11:08
 * 修改人： 李程伟
 * 修改时间：2017/5/25.11:08
 * 修改备注：
 */
public class BaseApplication extends MultiDexApplication {

    private Map<String, Activity> activities = new HashMap<>();
    public static int currentApiVersion = android.os.Build.VERSION.SDK_INT;

    public static Context applicationContext;
    private static BaseApplication instance;
    public ArrayList<Group> groups = new ArrayList<>();//app全局选中的二级品类集合

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        init();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 初始化项目中用到的第三方框架
     */
    private void init() {
        LogUtil.i("init");
        initDhx();
        initOkHttp();
    }

    private void initDhx(){
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    private void initOkHttp(){
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)
                .cookieJar(cookieJar)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


}
