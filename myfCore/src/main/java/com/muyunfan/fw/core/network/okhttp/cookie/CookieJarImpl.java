package com.muyunfan.fw.core.network.okhttp.cookie;

import com.muyunfan.fw.core.network.okhttp.cookie.store.CookieStore;
import com.muyunfan.fw.core.network.okhttp.utils.Exceptions;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 类名称：com.muyunfan.fw.basemodule.network.okhttp.cookie.store
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/21.21:17
 * 修改人： 李程伟
 * 修改时间：2017/6/21.21:17
 * 修改备注：
 */
public class CookieJarImpl implements CookieJar
{
    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore)
    {
        if (cookieStore == null) Exceptions.illegalArgument("cookieStore can not be null.");
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies)
    {
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url)
    {
        return cookieStore.get(url);
    }

    public CookieStore getCookieStore()
    {
        return cookieStore;
    }
}

