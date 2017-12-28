package com.muyunfan.fw.core.code;

/**
 * 类名称：com.muyunfan.fw.basemodule.code
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/15.9:55
 * 修改人： 李程伟
 * 修改时间：2017/6/15.9:55
 * 修改备注：
 */
public class AccountCode {

    public static boolean isUploadNormal = false;
    public static int uploadErrorCount = 0;

    public static final String FINISH_APP = "finishApp";            //关闭应用
    public static final String USER_INFO_ERROR = "userInfoError";   //获取用户信息失败
    public static final String USER_INFO_REFRESH= "userInfoRefresh";   //用户信息刷新
    public static final String USER_INFO_INIT= "userInfoInit";   //用户信息更新显示

    public static final String USER_TYPE = "userType";      //用户类型
    public static final int TYPE_MANAGER = 0;       //管理员
    public static final int TYPE_USER = 1;          //用户
    public static final int TYPE_WORKER = 2;        //维修人员

    public static final int STATUS_NORMAL = 0;      //正常
    public static final int STATUS_ERROR = 1;       //封号
    public static final int STATUS_AUTHING = 2;     //等待审核
}
