package com.muyunfan.fw.widget.scan.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.muyunfan.fw.widget.scan.core.MyPlanarYUVLuminanceSource;
import com.muyunfan.fw.widget.scan.core.QRCodeView;
import com.muyunfan.fw.widget.utils.common.LogUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;


public class ZXingView extends QRCodeView {
    private MultiFormatReader mMultiFormatReader;
    private Bitmap scanBitmap;
//    private int rctWidth;

    public ZXingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QRCodeView);
//        rctWidth = (int)ta.getDimension(R.styleable.QRCodeView_qrcv_rectWidth,-1);
        initMultiFormatReader();
    }

    private void initMultiFormatReader() {
        mMultiFormatReader = new MultiFormatReader();
        mMultiFormatReader.setHints(QRCodeDecoder.HINTS);
    }

    @Override
    protected void handleData(final byte[] data, final int width, final int height, final Camera camera) {
        new AsyncTask<Void,Void,String>() {

            @Override
            protected String doInBackground(Void... params) {
                String result = null;
                Result rawResult = null;
                long start = System.currentTimeMillis();

                MyPlanarYUVLuminanceSource source = new MyPlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    rawResult = mMultiFormatReader.decodeWithState(binaryBitmap);
                    scanBitmap = source.renderCroppedGreyscaleBitmap();
                } catch (Exception e) {
                } finally {
                    mMultiFormatReader.reset();
                }

                if (rawResult != null) {
                    long end = System.currentTimeMillis();
                    LogUtil.i("Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
                    result = rawResult.getText();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                if (mDelegate != null && !TextUtils.isEmpty(result)) {
                    mDelegate.onScanQRCodeSuccess(result,scanBitmap);
                } else {
                    try {
                        camera.setOneShotPreviewCallback(ZXingView.this);
                    } catch (RuntimeException e) {
                    }

                }
            }
        }.execute();
    }
}