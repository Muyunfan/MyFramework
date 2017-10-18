package com.muyunfan.fw.widget.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.muyunfan.fw.widget.utils.common.CheckUtil;
import com.muyunfan.fw.widget.utils.common.CommonUtil;
import com.muyunfan.fw.widget.utils.mobile.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.muyunfan.fw.R.mipmap.ic_launcher;

/*
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 */
public class GlideUtil {
    private static Context mContext;

    public static void getContext(Context context) {
        mContext = context;
    }

    public static void loadPathImage(String filePath, ImageView imageView) {

        if (CheckUtil.isEmpty(filePath)) {
            imageView.setImageResource(ic_launcher);
        } else {
            Glide.with(mContext)
                    .load(filePath)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }
    }

    public static void loadBanner(String url, ImageView imageView, int emptyImg, int errorImg) {

        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(emptyImg);
        } else {
            Glide.with(mContext)
                    .load(url)
                    .error(errorImg)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }
    }

    public static void loadImage(String url, ImageView imageView) {

        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(ic_launcher);
        } else {
            Glide.with(mContext)
                    .load(url)
                    .error(ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }
    }

    public static void loadImageHead(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
//                .placeholder(R.mipmap.new_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadRoundImage(String url, ImageView imageView, int round) {

        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(ic_launcher);
        } else {
            Glide.with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .transform(new GlideRoundTransform(mContext, round))
                    .into(imageView);
        }
    }

    public static void loadCircleImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(ic_launcher)
                .error(ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

    /**
     * 加载用户头像
     *
     * @param context
     * @param url
     * @param imageView
     * @param defaultSrc 默认图片
     */
    public static void displayImageHead(Context context, String url, int defaultSrc, ImageView imageView) {

        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(defaultSrc);
        } else {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .error(ic_launcher)
                    .transform(new GlideCircleTransform(mContext))
                    .into(imageView);
        }

    }

    /**
     * 清除图片磁盘缓存(Glide自带清除磁盘缓存)
     */
    public static boolean clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
                return true;
            } else {
                Glide.get(context).clearDiskCache();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            long size = FileUtil.getFolderSize(new File(context.getCacheDir() +
                    "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
            if (size > 0) {
                return CommonUtil.getFormatSize(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用下的图像
     *
     * @param context
     * @param avatar_url
     * @return
     */
    public static Bitmap getUserIcon(Context context, String avatar_url) {
        Bitmap bitmap = null;
        try {
            File file = new File(context.getFilesDir(), URLEncoder.encode(avatar_url, "utf-8"));
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 把用户的图像信息保存到应用文件夹里
     * 注意：用户登录后保存该文件
     *
     * @param context 上下文
     * @param image   bitmap类型图片
     * @param url     服务器上图片的url
     */
    public static void saveUserPicToFile(Context context, Bitmap image, String url) {
        FileOutputStream fos = null;
        try {
            File file = context.getFilesDir();        // 获取应用的目录
            File bitmapFile = new File(file, URLEncoder.encode(url, "utf-8"));
            fos = new FileOutputStream(bitmapFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把数据写入文件
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
