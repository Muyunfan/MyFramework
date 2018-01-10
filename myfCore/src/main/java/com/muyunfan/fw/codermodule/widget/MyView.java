package com.muyunfan.fw.codermodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

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
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        //create a circle
        canvas.drawCircle(400, 400, 200, paint);
        canvas.drawColor(Color.parseColor("#884d4f53"));

    }
}
