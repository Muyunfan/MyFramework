package com.muyunfan.fw.widget.utils.bitmap;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.WindowManager;

import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;

public class BitmapUtil {
	/**
	 * 读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Bitmap ReadBitmapById(Context context, int resId) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			// 获取资源图片
			InputStream is = context.getResources().openRawResource(resId);
			bitmap = BitmapFactory.decodeStream(is, null, opt);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return bitmap;
		}
	}

	/***
	 * 根据资源文件获取Bitmap
	 *
	 * @param context
	 * @param drawableId
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Bitmap ReadBitmapById(Context context, int drawableId,
			int screenWidth, int screenHight) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.ARGB_8888;
			options.inInputShareable = true;
			options.inPurgeable = true;
			InputStream stream = context.getResources().openRawResource(
					drawableId);
			bitmap = BitmapFactory.decodeStream(stream, null, options);
			bitmap = getBitmap(bitmap, screenWidth, screenHight);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return bitmap;
		}
	}

	/**
	 * 压缩图片
	 *
	 * @param bitmap
	 *            源图片
	 *            想要的宽度
	 *            想要的高度
	 * @param isAdjust
	 *            是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
	 * @return Bitmap
	 */
	@SuppressWarnings("finally")
	public static Bitmap reduce(Bitmap bitmap, boolean isAdjust, Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		// 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
		if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
			return bitmap;
		}
		// 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor,
		// int scale, int roundingMode);
		// scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
		float sx = new BigDecimal(width).divide(
				new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		float sy = new BigDecimal(height).divide(
				new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
			sx = (sx < sy ? sx : sy);
			sy = sx;// 哪个比例小一点，就用哪个比例
		}
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
		try {
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return bitmap;
		}
	}

	/***
	 * 等比例压缩图片
	 *
	 * @param bitmap
	 * @param screenWidth
	 * @param screenHight
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Bitmap getBitmap(Bitmap bitmap, int screenWidth,
			int screenHight) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Log.e("jj", "图片宽度" + w + ",screenWidth=" + screenWidth);
		Matrix matrix = new Matrix();
		float scale = (float) screenWidth / w;
		float scale2 = (float) screenHight / h;

		// scale = scale < scale2 ? scale : scale2;

		// 保证图片不变形.
		matrix.postScale(scale, scale);
		// w,h是原图的属性.
		try {
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return bitmap;
		}
	}

	/***
	 * 保存图片至SD卡
	 *
	 * @param bm
	 * @param url
	 * @param quantity
	 */
	private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;
	private static int MB = 1024 * 1024;
	public final static String DIR = "/sdcard/hypers";

	public static void saveBmpToSd(Bitmap bm, String url, int quantity) {
		// 判断SDCard是否存在
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
			return;
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			return;
		}
		String filename = url;
		// 目录不存在就创建
		File dirPath = new File(url);
		if (!dirPath.getParentFile().exists()) {
			dirPath.getParentFile().mkdirs();
		}
		// File file = new File(DIR + "/" + filename);
		try {
			dirPath.createNewFile();
			OutputStream outStream = new FileOutputStream(dirPath);
			bm.compress(Bitmap.CompressFormat.WEBP, quantity, outStream);
			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/***
	 * 获取SD卡图片
	 *
	 * @param url
	 * @param quantity
	 * @return
	 */
	public static Bitmap GetBitmap(String url, Context context, int quantity) {
		InputStream inputStream = null;
		String filename = "";
		Bitmap map = null;
		URL url_Image = null;
		String LOCALURL = "";
		if (url == null)
			return null;
		try {
			filename = url;
		} catch (Exception err) {
		}
		// if (Exist(DIR + "/" + LOCALURL)) {
		// map = BitmapFactory.decodeFile(DIR + "/" + LOCALURL);
		File file = new File(filename);
		if (file.exists()) {
			map = getBitmapFromFile(file, context, 0, 0);
		} else {
			LOCALURL = URLEncoder.encode(filename);
			try {
				url_Image = new URL(url);
				inputStream = url_Image.openStream();
				byte[] input2byte = input2byte(inputStream);
				map = getBitmapFromData(input2byte, context, 0, 0);
				// url = URLEncoder.encode(url, "UTF-8");
				if (map != null) {
					saveBmpToSd(map, LOCALURL, quantity);
				}
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return map;
	}

	/**
	 * inputsream转换成byte[]
	 *
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static final byte[] input2byte(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	/***
	 * 判断图片是存在
	 *
	 * @param url
	 * @return
	 */
	public static boolean Exist(String url) {
		File file = new File(DIR + url);
		return file.exists();
	}

	/**
	 * 判断SD卡是否存在
	 *
	 * @return
	 */
	private static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/** * 计算sdcard上的剩余空间 * @return */
	private static int freeSpaceOnSd() {
		if (!ExistSDCard()) {
			return 0;
		}
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;

		return (int) sdFreeMB;
	}

	/**
	 * 动态分配空间，防止内存溢出
	 * 
	 * @param dst
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapFromFile(File dst, Context context,
			int width, int height) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		if (width == 0) {
			width = wm.getDefaultDisplay().getWidth();
		}
		if (height == 0) {
			height = wm.getDefaultDisplay().getHeight();
		}
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options(); // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height); // 这里一定要将其设置回false，因为之前我们将其设置成了true
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 将dyte数据转换成bitmap
	 * 
	 * @param data
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapFromData(byte[] data, Context context,
			int width, int height) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		if (width == 0) {
			width = wm.getDefaultDisplay().getWidth();
		}
		if (height == 0) {
			height = wm.getDefaultDisplay().getHeight();
		}
		// ByteArrayInputStream input = new ByteArrayInputStream(data);
		BitmapFactory.Options opts = null;
		if (width > 0 && height > 0) {
			opts = new BitmapFactory.Options(); // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
			opts.inJustDecodeBounds = true;
			// BitmapFactory.decodeStream(input, null, opts);
			BitmapFactory.decodeByteArray(data, 0, data.length, opts);
			// 计算图片缩放比例
			final int minSideLength = Math.min(width, height);
			opts.inSampleSize = computeSampleSize(opts, minSideLength, width
					* height); // 这里一定要将其设置回false，因为之前我们将其设置成了true
			opts.inJustDecodeBounds = false;
			opts.inInputShareable = true;
			opts.inPurgeable = true;
		}
		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按比例压缩
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * crop image
	 * @param activity
	 * @param sourceUri
	 * @param destinationUri
	 */
	public static void startCrop(Activity activity, Uri sourceUri, Uri destinationUri) {
		UCrop.Options options = new UCrop.Options();
		options.setShowCropFrame(false);
		options.setOvalDimmedLayer(true);

		UCrop.of(sourceUri, destinationUri)
				.withAspectRatio(1, 1)
				.withMaxResultSize(320, 320)
				.withOptions(options)
				.start(activity);
	}

	/**
	 * 根据资源名获取资源
	 *
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getDrawableWithResName(Context context, String name) {
		Resources res = context.getResources();
		final String packageName = context.getPackageName();
		int imageResId = res.getIdentifier(name, "drawable", packageName);
		return imageResId;
	}
}
