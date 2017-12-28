package com.muyunfan.fw.core.bean;

/**
 * 类名称：com.muyunfan.fw.basemodule.bean
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/12.13:49
 * 修改人： 李程伟
 * 修改时间：2017/6/12.13:49
 * 修改备注：
 */
public class EventCenter <T> {

    /**
     * reserved data
     */
    private T data;

    /**
     * this code distinguish between different events
     */
    private String eventCode;

    public EventCenter(String eventCode) {
        this(eventCode, null);
    }

    public EventCenter(String eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public String getEventCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public T getData() {
        return this.data;
    }
}
