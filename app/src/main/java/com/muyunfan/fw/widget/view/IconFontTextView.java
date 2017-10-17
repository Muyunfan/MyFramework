package com.muyunfan.fw.widget.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 类名称：com.muyunfan.fw.widget.view
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/19.19:29
 * 修改人： 李程伟
 * 修改时间：2017/6/19.19:29
 * 修改备注：
 */
public class IconFontTextView extends android.support.v7.widget.AppCompatTextView {

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        //加载字体文件
        Typeface typeface = IconFontTypeFace.getTypeface(context);
        this.setTypeface(typeface);
        //去掉padding,这样iconfont和普通字体容易对齐
        setIncludeFontPadding(false);
    }


    public static class IconFontTypeFace {

        //用static,整个app共用整个typeface就够了
        private static Typeface ttfTypeface = null;

        public static synchronized Typeface getTypeface(Context context) {
            if (ttfTypeface == null) {
                try {
                    ttfTypeface = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
                } catch (Exception e) {

                }
            }
            return ttfTypeface;
        }

        public static synchronized void clearTypeface() {
            ttfTypeface = null;
        }
    }
}
