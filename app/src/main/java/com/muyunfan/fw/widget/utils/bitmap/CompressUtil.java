package com.muyunfan.fw.widget.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.muyunfan.fw.widget.utils.mobile.FileUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/*
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 */
public class CompressUtil {

    public static byte[] compressBitmapToBytes(String filePath, int reqWidth, int reqHeight, int quality, Bitmap.CompressFormat format) {
        Bitmap bitmap = getSmallBitmap(filePath, reqWidth, reqHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LogUtil.e("---------",filePath);
        bitmap.compress(format, quality, baos);
        byte[] bytes = baos.toByteArray();
        bitmap.recycle();
        LogUtil.i("CompressImage", "Bitmap compressed success, size: " + bytes.length/1024 + "kb");
        return bytes;
    }

    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
//        options.inPreferQualityOverSpeed = true;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int h = options.outHeight;
        int w = options.outWidth;
        int inSampleSize = 0;
        if (h > reqHeight || w > reqWidth) {
            float ratioW = (float) w / reqWidth;
            float ratioH = (float) h / reqHeight;
            inSampleSize = (int) Math.min(ratioH, ratioW);
        }
        inSampleSize = Math.max(1, inSampleSize);
        return inSampleSize;
    }

    private static void compress(String path,String path2) {
        try {
            File file = new File(path);
            byte[] bytes1 = CompressUtil.compressBitmapToBytes(file.getPath(), 600, 0, 10, Bitmap.CompressFormat.WEBP);
            File webp = new File(path2);
            FileUtil.writeByteArrayToFile(webp, bytes1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
