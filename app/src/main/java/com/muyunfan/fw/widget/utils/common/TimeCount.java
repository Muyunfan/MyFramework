package com.muyunfan.fw.widget.utils.common;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 定时器
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 * */
public class TimeCount extends CountDownTimer {

	View messageView;
	Button btn;
	TextView textView;
	String text;
	int btnColor;
	boolean isstart;
	boolean isFinished;
	OnFinishListener mOnFinishListener;

	public interface OnFinishListener{
		void finish();
	}

	public void setOnFinishListener(OnFinishListener onFinishListener){
		this.mOnFinishListener = onFinishListener;
	}

	public TimeCount(long millisInFuture, long countDownInterval){
		super(millisInFuture, countDownInterval);
	}

	public TimeCount(long millisInFuture, long countDownInterval, View textView) {
		this(millisInFuture, countDownInterval, textView, "重新验证");
	}

	public TimeCount(long millisInFuture, long countDownInterval, View textView, String text, int btnColor) {
		this(millisInFuture, countDownInterval, textView, text);
		this.btnColor = btnColor;
	}

	/**
	 * @param millisInFuture 时长
	 * @param countDownInterval	频率
	 */
	public TimeCount(long millisInFuture, long countDownInterval,
					 View view, String text) {
		super(millisInFuture, countDownInterval);
		messageView = view;
		btn = (Button) messageView;
		this.text = text;
	}

	/**
	 *
	 * @param millisInFuture 时长
	 * @param countDownInterval	频率
	 * @param textView
	 */
	public TimeCount(long millisInFuture, long countDownInterval,
					 TextView textView, String text) {
		super(millisInFuture, countDownInterval);
		messageView = textView;
		this.textView = (TextView) messageView;
		this.text = text;
	}

	@Override
	public void onFinish() {// 计时完毕时触发
		if (btn != null) {
			isstart = false;
			btn.setText(this.text);
			btn.setClickable(true);
			if(btnColor!=0){
				btn.setBackgroundResource(btnColor);
			}
		}else if(textView != null){
			isstart = false;
			textView.setText(this.text);
			textView.setClickable(true);
			if(btnColor!=0){
				textView.setBackgroundResource(btnColor);
			}
		}
		if(mOnFinishListener!=null){
			mOnFinishListener.finish();
		}
		isFinished = true;
	}

	public void startTime() {
		isstart = true;
		this.start();
	}

	public boolean isStart() {
		return isstart;
	}

	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public void onTick(long millisUntilFinished) {// 计时过程显示
		if (btn != null) {
			btn.setClickable(false);
			btn.setText(millisUntilFinished / 1000 + "秒");
		}else if(textView != null){
			textView.setClickable(false);
			textView.setText(millisUntilFinished / 1000 + "秒");
		}
	}
	
	public static String getLeftTime(String acceptDate,int countTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date date1 = null;
		try {
			date1 = sdf.parse(acceptDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long lDate1 = date1.getTime();
		long lDate2 = date.getTime();
		
		long dateTemp = lDate2-lDate1;
		
		long longCountTime = countTime*1000*60*60;
		long leftTime = longCountTime - dateTemp;
		
		long hour = leftTime/(60*60*1000); 
		long minute = (leftTime - hour*60*60*1000)/(60*1000); 
		long second = (leftTime - hour*60*60*1000 - minute*60*1000)/1000; 
		
		StringBuffer sb = new StringBuffer();
		sb.append(hour).append("时").append(minute).append("分").append(second).append("秒");
		
		if(leftTime<0){
			return null;
		}else{
			return sb.toString();
		}
	}
}
