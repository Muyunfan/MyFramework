package com.muyunfan.fw.basemodule.code;

/**
 * 类名称：com.muyunfan.fw.basemodule.code
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/15.14:57
 * 修改人： 李程伟
 * 修改时间：2017/6/15.14:57
 * 修改备注：
 */
public class TaskCode {

    public static final String TASK_ID = "taskId";       //订单id

    public static final String CALL = "call";           //拨打电话

    public static final String TASK_LAT_LNG = "taskLatLng";     //订单经纬度

    public static final String NEW_TASK = "newTask";            //新订单

    public static final String STATUS_ALL_FILTER = "";   //全部
    public static final String STATUS_ACCEPT_FILTER = "0";      //待接单
    public static final String STATUS_WORKING_FILTER = "1";     //待处理
    public static final String STATUS_FINISH_FILTER = "2";      //已完成
    public static final String STATUS_CANCEL_FILTER = "3";      //已取消

    public static final int STATUS_CREATE = 0;      //已创建
    public static final int STATUS_WORKING = 1;     //待处理
    public static final int STATUS_FINISH = 2;      //已完成
    public static final int STATUS_CANCEL = 3;      //已取消


    public static final String REFRESH = "refresh";


    public static final int DEVICE_TYPE_COPIER = 1;
    public static final int DEVICE_TYPE_PRINTER = 2;
    public static final int DEVICE_TYPE_ALLINONE = 3;
    public static final int DEVICE_TYPE_COMPUTER = 4;
}
