package com.muyunfan.fw.basemodule.cache;

import com.muyunfan.fw.basemodule.bean.account.UserInfo;
import com.muyunfan.fw.widget.utils.common.CheckUtil;
import com.muyunfan.fw.widget.utils.common.GsonUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;
import com.muyunfan.fw.widget.utils.common.SharedPreferencesUtil;

/**
 * 类名称：com.muyunfan.fw.basemodule.cache
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/14.11:47
 * 修改人： 李程伟
 * 修改时间：2017/6/14.11:47
 * 修改备注：
 */
public class AppCache {

    public static double currentLat = 0;
    public static double currentLng = 0;

    private static AppCache instance;

    private AppCache() {
    }

    public static AppCache getInstance() {
        if (null == instance) {
            synchronized (AppCache.class) {
                if (instance == null) {
                    instance = new AppCache();
                }
            }
        }
        return instance;
    }

    private static final String TOKEN = "token";
    private static final String TOKEN_TIME = "tokenTime";

    private final static long THIRTY_DAYS = 24 * 60 * 60 * 1000 * 30;

    public void setToken(String token) {
        SharedPreferencesUtil.putSharePrefString(TOKEN, token);
        SharedPreferencesUtil.putSharePrefLong(TOKEN_TIME, System.currentTimeMillis());
    }

    public String getToken() {
        return SharedPreferencesUtil.getSharePrefString(TOKEN);
    }

    public void refreshToken(String refreshToken) {
        long time = System.currentTimeMillis();
        long lastTime = SharedPreferencesUtil.getSharePrefLong(TOKEN_TIME);
        if (lastTime > 0 && (time - lastTime > THIRTY_DAYS) && !CheckUtil.isEmpty(refreshToken)) {
            setToken(refreshToken);
        }
    }


    private static final String USERINFO = "userInfo";

    public void clearUserInfo() {
        SharedPreferencesUtil.putSharePrefString(USERINFO, "");
    }

    public void setUserInfo(UserInfo userInfo) {
        SharedPreferencesUtil.putSharePrefString(USERINFO, GsonUtil.toJson(userInfo));
    }

    public UserInfo getUserInfo() {
        String userInfo = SharedPreferencesUtil.getSharePrefString(USERINFO);
        if (!CheckUtil.isEmpty(userInfo)) {
            return GsonUtil.fromJson(userInfo, UserInfo.class);
        } else {
            return null;
        }
    }


}
