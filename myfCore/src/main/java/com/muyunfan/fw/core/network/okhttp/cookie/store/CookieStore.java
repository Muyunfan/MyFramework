package com.muyunfan.fw.core.network.okhttp.cookie.store;

import java.util.List;

import okhttp3.Cookie;
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
public interface CookieStore
{

    void add(HttpUrl uri, List<Cookie> cookie);

    List<Cookie> get(HttpUrl uri);

    List<Cookie> getCookies();

    boolean remove(HttpUrl uri, Cookie cookie);

    boolean removeAll();

}
