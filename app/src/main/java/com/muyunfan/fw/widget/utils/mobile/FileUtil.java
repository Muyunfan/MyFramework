package com.muyunfan.fw.widget.utils.mobile;

import android.os.Environment;

import com.muyunfan.fw.basemodule.cache.AppCache;
import com.muyunfan.fw.basemodule.code.APICode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 类名：
 * 类描述：
 * 创建人：李程伟
 * 创建日期： 2017年5月25日14:44:48
 * 版本：V1.0
 */
public class FileUtil {

    public static String getImgFile() {

        File fileDir = new File(getFolderPath());
        if (fileDir.exists()) {
            return getFolderPath() + getPhotoFileName();
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getPhotoFileName();
        }
    }

    /**
     * APP文件根目录
     *
     * @return
     */
    private static String getFolderPath() {
        if (null != AppCache.getInstance().getUserInfo() && null != AppCache.getInstance().getUserInfo().id) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/" + APICode.THE_ROOT_DIRECTORY + "/" + AppCache.getInstance().getUserInfo().id);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                return dir.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                return Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        } else {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + APICode.THE_ROOT_DIRECTORY);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getPath();
        }
    }

    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmssSSS", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static void writeByteArrayToFile(File file,byte[] bt) throws IOException {
        //建立输出字节流
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bt);//用FileOutputStream 的write方法写入字节数组
        fos.close();//为了节省IO流的开销，需要关闭
    }

    public static Object byteToObject(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
