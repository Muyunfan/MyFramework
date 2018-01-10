package com.muyunfan.fw.codermodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.muyunfan.fw.widget.utils.mobile.WindowUtil;

/**
 * 类名称：MyView
 * 类描述：
 * 创建人：L.C.W
 * 创建时间：2018/1/10.10:50
 * 修改人：L.C.W
 * 修改时间：2018/1/10.10:50
 * 修改备注：
 */
public class MyView extends View {

    private Context context;
    private static final String TAG = "MyView";

    public MyView(Context context) {
        super(context);
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    Paint paint = new Paint();

    private void initPaint() {
//        paint.setStyle(Style style) 设置绘制模式
//        paint.setColor(int color) 设置颜色
//        paint.setStrokeWidth(float width) 设置线条宽度
//        paint.setTextSize(float textSize) 设置文字大小
//        paint.setAntiAlias(boolean aa) 设置抗锯齿开关

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#884d4f53"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initPaint();

        WindowUtil windowUtil = new WindowUtil(context);
        int[] windowPixels = windowUtil.getWindowPixels();
        int width = windowPixels[0];
        int height = windowPixels[1];

        Log.i(TAG, "width:" + width + " , height:" + height);

        //create a circle
        canvas.drawCircle(width / 2, height / 2 - 100, 200, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 500, 500, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(600, 100, 1000, 500, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(30);
        canvas.drawPoint(width / 2 - 50, (float) (height / 1.5), paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(30);
        canvas.drawPoint(width / 2 + 50, (float) (height / 1.5), paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(30);
        canvas.drawPoint(width / 2, (float) (height / 1.5 + 100), paint);

        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
// 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
                8 /* 一共绘制 8 个数（4 个点）*/, paint);

    }
}
