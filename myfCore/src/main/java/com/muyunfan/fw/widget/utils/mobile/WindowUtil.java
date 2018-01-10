package com.muyunfan.fw.widget.utils.mobile;

import android.content.Context;
import android.view.WindowManager;

/**
 * 类名称：WindowUtil
 * 类描述：
 * 创建人：L.C.W
 * 创建时间：2018/1/10.15:16
 * 修改人：L.C.W
 * 修改时间：2018/1/10.15:16
 * 修改备注：
 */
public class WindowUtil {
    /**
     * 方法一：
     * WindowManager wm = (WindowManager) this
     * .getSystemService(Context.WINDOW_SERVICE);
     * int width = wm.getDefaultDisplay().getWidth();
     * int height = wm.getDefaultDisplay().getHeight();
     * 方法二：
     * WindowManager wm1 = this.getWindowManager();
     * int width1 = wm1.getDefaultDisplay().getWidth();
     * int height1 = wm1.getDefaultDisplay().getHeight();
     * 方法一与方法二获取屏幕宽度的方法类似，只是获取WindowManager 对象时的途径不同。
     * <p>
     * 方法三：
     * WindowManager manager = this.getWindowManager();
     * DisplayMetrics outMetrics = new DisplayMetrics();
     * manager.getDefaultDisplay().getMetrics(outMetrics);
     * int width2 = outMetrics.widthPixels;
     * int height2 = outMetrics.heightPixels;
     * 方法四：
     * Resources resources = this.getResources();
     * DisplayMetrics dm = resources.getDisplayMetrics();
     * float density1 = dm.density;
     * int width3 = dm.widthPixels;
     * int height3 = dm.heightPixels;
     */

    private Context context;

    public WindowUtil(Context context) {
        this.context = context;
    }

    /** 获取屏幕宽度，长度*/
    public int[] getWindowPixels() {
        WindowManager wm = (WindowManager) this.context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        int[] pixels = new int[2];
        pixels[0] = width;
        pixels[1] = height;
        return pixels;
    }
}
