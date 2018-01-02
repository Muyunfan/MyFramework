package com.muyunfan.fw.widget.utils.common;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 */
public class TimeUtil {
    /**
     * 转换成时间显示格式
     */
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd hh:mm");
    /**
     * 转换成时间显示格式
     */
    private static final SimpleDateFormat TIME_ALL_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss");
    /**
     * 转换成时间显示格式
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");

    /**
     * 转换成时间显示格式
     */
    private static final SimpleDateFormat DAY_HOUR_FORMAT = new SimpleDateFormat(
            "MM月dd日 hh:mm");

    public static String dayHourToDisplay(long time) {
        if (time > 0) {
            return DAY_HOUR_FORMAT.format(time);
        } else {
            return null;
        }
    }


    public static String dateToDisplay(long time) {
        if (time > 0) {
            return DATE_FORMAT.format(time);
        } else {
            return null;
        }
    }

    public static String dateToDisplay(String time){
        long dateL = 0l;
        String dateS = time;
        try {
            dateL =  Long.parseLong(time);
            dateS =  dateToDisplay(dateL);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtil.getStringWithWord(dateS,"");
    }

    public static String timeToDisplay(String date){
        long dateL = 0l;
        String dateS = date;
        try {
            dateL =  Long.parseLong(date);
            dateS =  timeToDisplay(dateL);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtil.getStringWithWord(dateS,"");
    }

    public static String timeToDisplay(long time) {
        if (time > 0) {
            return TIME_FORMAT.format(time);
        } else {
            return null;
        }
    }

    public static long stringToTimeStamp(String time) {
        if (!TextUtils.isEmpty(time)) {
            Date date;
            try {
                LogUtil.i(time);
                date = TIME_FORMAT.parse(time);
                return date.getTime();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     *
     * @param dateStr       比较的时间
     * @param timeQuantum   时间段
     * @return
     * @throws Exception
     */
    public static int compareCurrentTime(String dateStr, int timeQuantum){
        if (CheckUtil.isEmpty(dateStr)) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        String currentDate = TIME_FORMAT.format(calendar.getTime());
        Date d1 = null;
        try {
            d1 = TIME_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        calendar.setTime(d1);
        calendar.add(Calendar.HOUR, -(timeQuantum + 1));
        String compareDate = TIME_FORMAT.format(calendar.getTime());
        Log.d("time", "currentDate=" + currentDate + "\n" + "compareDate=" + compareDate);
        int compareTo = currentDate.compareTo(compareDate);

        return compareTo;
    }

    public static String currentTime(){
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static final long[] times = new long[]{
            1000,1000*60,1000*60*60,1000*60*60*24
    };

    /**
     * 获取剩余处理时间
     * @param time
     */
    public static String getLeaveTime(long time){
        StringBuffer sb = new StringBuffer();
        if(time<0){
            sb.append("已超时");
            time = 0-time;
        }

        if(time/times[3]>0){
            sb.append(time/times[3]).append("天");
            time = time%times[3];
        }
        if(time/times[2]>0){
            sb.append(time/times[2]).append("时");
            time = time%times[2];
        }
        if(time/times[1]>0){
            sb.append(time/times[1]).append("分");
            time = time%times[1];
        }
        if(time/times[0]>0){
            sb.append(time/times[0]).append("秒");
        }
        if(CheckUtil.isEmpty(sb.toString())){
            return "已超时";
        }
        return sb.toString();
    }

    /**
     * 获取剩余处理时间
     * @param time
     */
    public static String getLeaveTimeM(long time){
        StringBuffer sb = new StringBuffer();
        if(time<0){
            sb.append("已超时");
            time = 0-time;
        }

        if(time/times[3]>0){
            sb.append(time/times[3]).append("天");
            time = time%times[3];
        }
        if(time/times[2]>0){
            sb.append(time/times[2]).append("时");
            time = time%times[2];
        }
        if(time/times[1]>0){
            sb.append(time/times[1]).append("分");
        }
        if(CheckUtil.isEmpty(sb.toString())){
            return "已超时";
        }
        return sb.toString();
    }


}
