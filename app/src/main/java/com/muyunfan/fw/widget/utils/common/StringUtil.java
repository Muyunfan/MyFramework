package com.muyunfan.fw.widget.utils.common;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;

import com.muyunfan.fw.widget.utils.common.CheckUtil;

import java.text.DecimalFormat;

/*
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 */
public class StringUtil {

    private StringUtil() {
    }

    private static final DecimalFormat df = new DecimalFormat("#0.00");

    public static String getStringWithWord(String str, String word) {
        if (CheckUtil.isEmpty(str)) {
            if (CheckUtil.isEmpty(word)) {
                word = "";
            }
            return word;
        } else {
            return str;
        }
    }

    public static String getDecimalNum(Double num) {
        String result = df.format(num);
        return result;
    }

    public static String getDecimalNum(String num) {
        if (CheckUtil.isEmpty(num)) {
            return "0.00";
        }
        try {
            double n = Double.valueOf(num);
            String result = df.format(n);
            return result;
        } catch (Exception e) {
            return num;
        }
    }


    /**
     * 根据style 格式化 String 三段式
     *
     * @param context
     * @param style1
     * @param part1
     * @param style2
     * @param part2
     * @param style3
     * @param part3
     * @return
     */
    public static SpannableStringBuilder get3SpanText(Context context, int style1, String part1, int style2, String part2, int style3, String part3) {
        SpannableStringBuilder builder = new SpannableStringBuilder(part1 + part2 + part3);
        TextAppearanceSpan span1 = new TextAppearanceSpan(context, style1);
        TextAppearanceSpan span2 = new TextAppearanceSpan(context, style2);
        TextAppearanceSpan span3 = new TextAppearanceSpan(context, style3);

        builder.setSpan(span1, 0, part1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span2, part1.length(), part1.length() + part2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span3, part1.length() + part2.length(), part1.length() + part2.length() + part3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    /**
     * 根据style 格式化 String 两段式
     *
     * @param context
     * @param style1
     * @param part1
     * @param style2
     * @param part2
     * @return
     */
    public static SpannableStringBuilder get2SpanText(Context context, int style1, String part1, int style2, String part2) {
        if (part1 == null || part2 == null) {
            return new SpannableStringBuilder(part1 + part2);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(part1 + part2);
        TextAppearanceSpan span1 = new TextAppearanceSpan(context, style1);
        TextAppearanceSpan span2 = new TextAppearanceSpan(context, style2);

        builder.setSpan(span1, 0, part1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span2, part1.length(), part1.length() + part2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

}
