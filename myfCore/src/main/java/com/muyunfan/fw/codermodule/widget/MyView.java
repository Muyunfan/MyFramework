package com.muyunfan.fw.codermodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.muyunfan.fw.widget.utils.mobile.WindowUtil;

import java.util.ArrayList;
import java.util.List;

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
    Path path = new Path(); // 初始化 Path 对象

    private void initPaint() {
//        paint.setStyle(Style style) 设置绘制模式
//        paint.setColor(int color) 设置颜色
//        paint.setStrokeWidth(float width) 设置线条宽度
//        paint.setTextSize(float textSize) 设置文字大小
//        paint.setAntiAlias(boolean aa) 设置抗锯齿开关

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        paint.setColor(Color.parseColor("#FF4081"));
//        Rect rect = new Rect();
//        RectF rectF = new RectF();

        // 使用 path 对图形进行描述（这段描述代码不必看懂）
//        path.addArc(200, 200, 400, 400, -225, 225);
//        path.arcTo(400, 200, 600, 400, -180, 225, false);
//        path.lineTo(400, 542);
//
//        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成
//
//
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStrokeWidth(80);
//        canvas.drawLine(200, 200, 800, 500, paint);

//        initPaint();

//        WindowUtil windowUtil = new WindowUtil(context);
//        int[] windowPixels = windowUtil.getWindowPixels();
//        int width = windowPixels[0];
//        int height = windowPixels[1];
//
//        Log.i(TAG, "width:" + width + " , height:" + height);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
//        path.lineTo(100, 100);
//        path.arcTo(100, 100, 300, 300, -90, 90, true); // 强制移动到弧形起点（无痕迹）

//        path.lineTo(100, 100);
//        path.arcTo(100, 100, 300, 300, -90, 90, false); // 直接连线连到弧形起点（有痕迹）

//        path.moveTo(100, 100);
//        path.lineTo(200, 100);
//        path.lineTo(150, 150);

//        path.moveTo(100, 100);
//        path.lineTo(200, 100);
//        path.lineTo(150, 150);
//        path.close(); // 使用 close() 封闭子图形。等价于 path.lineTo(100, 100)

        paint.setStyle(Paint.Style.FILL);
        path.moveTo(100, 100);
        path.lineTo(200, 100);
        path.lineTo(150, 150);
        // 这里只绘制了两条边，但由于 Style 是 FILL ，所以绘制时会自动封口

        canvas.drawPath(path, paint);
        //create a circle
//        canvas.drawCircle(width / 2, height / 2 - 100, 200, paint);

//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(100, 100, 500, 500, paint);
//
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(600, 100, 1000, 500, paint);

//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStrokeWidth(30);
//        canvas.drawPoint(width / 2 - 50, (float) (height / 1.5), paint);
//
//        paint.setStrokeCap(Paint.Cap.SQUARE);
//        paint.setStrokeWidth(30);
//        canvas.drawPoint(width / 2 + 50, (float) (height / 1.5), paint);
//
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStrokeWidth(30);
//        canvas.drawPoint(width / 2, (float) (height / 1.5 + 100), paint);
//
//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//// 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
//        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
//                8 /* 一共绘制 8 个数（4 个点）*/, paint);
//
//                paint.setStyle(Paint.Style.FILL);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(50, 50, 350, 200, paint);
//        }
//
//        paint.setStyle(Paint.Style.STROKE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(400, 50, 700, 200, paint);
//        }

//        canvas.drawLine(200, 200, 800, 500, paint);

//        paint.setStrokeWidth(5);
//
//        List<RectF> lines = new ArrayList<>();
//        RectF line = new RectF(20, 20, 120, 20);
//        RectF line2 = new RectF(70, 20, 70, 120);
//        RectF line3 = new RectF(20, 120, 120, 120);
//        RectF line4 = new RectF(150, 20, 250, 20);
//        RectF line5 = new RectF(150, 20, 150, 120);
//        RectF line6 = new RectF(250, 20, 250, 120);
//        RectF line7 = new RectF(150, 120, 250, 120);
//        lines.add(line);
//        lines.add(line2);
//        lines.add(line3);
//        lines.add(line4);
//        lines.add(line5);
//        lines.add(line6);
//        lines.add(line7);
//
//        float[] allPoints = drawLine(lines);
//        canvas.drawLines(allPoints, paint);

//        paint.setStyle(Paint.Style.FILL); // 填充模式
//        canvas.drawArc(200, 100, 800, 500, -110, 100, true, paint); // 绘制扇形
//        canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
//        paint.setStyle(Paint.Style.STROKE); // 画线模式
//        canvas.drawArc(200, 100, 800, 500, 180, 60, false, paint); // 绘制不封口的弧形

    }

    private float[] drawLine(List<RectF> lines) {
        float[] allPoints = new float[lines.size() * 4];
        for (int i = 0; i < lines.size(); i++) {
            RectF l = lines.get(i);
            allPoints[0 + (4 * i)] = l.left;
            allPoints[1 + (4 * i)] = l.top;
            allPoints[2 + (4 * i)] = l.right;
            allPoints[3 + (4 * i)] = l.bottom;
        }
        return allPoints;
    }
}

