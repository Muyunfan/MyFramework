package com.muyunfan.fw.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;

import com.muyunfan.fw.R;

/**
 * 类名称：com.muyunfan.fw.widget.dialog
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/12.13:54
 * 修改人： 李程伟
 * 修改时间：2017/6/12.13:54
 * 修改备注：
 */
public class ProgressDialog extends Dialog {

    private static ProgressDialog mProgressDialog;

    private ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static ProgressDialog createProgressDialog(Context context) {
        mProgressDialog = new ProgressDialog(context,
                R.style.SF_pressDialogCustom);
        mProgressDialog.setContentView(R.layout.dialog_progress_view);
        mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return mProgressDialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (null == mProgressDialog)
            return;
        ImageView loadingImageView = (ImageView) mProgressDialog
                .findViewById(R.id.sf_iv_progress_dialog_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView
                .getBackground();
        animationDrawable.start();
    }
//    public ProgressDialog setMessage(String msg) {
//        TextView loadingTextView = (TextView) mProgressDialog
//                .findViewById(R.id.sf_tv_progress_dialog_loading);
//        if (!TextUtils.isEmpty(msg))
//            loadingTextView.setText(msg);
//        else
//            loadingTextView.setText(R.string.progress_view_content);
//        return mProgressDialog;
//    }

//    public ProgressDialog setMessage(int msg) {
//        TextView loadingTextView = (TextView) mProgressDialog
//                .findViewById(R.id.sf_tv_progress_dialog_loading);
//        if (0 != msg)
//            loadingTextView.setText(msg);
//        else
//            loadingTextView.setText(R.string.progress_view_content);
//        return mProgressDialog;
//    }


//    // 调用展示
//    /**
//     * progressDialog
//     */
//    public void showProgressViewDialog() {
////        if (null == mProgressDialog)
////            mProgressDialog = ProgressDialog.createProgressDialog(context);
//        if (null != mProgressDialog) {
//            mProgressDialog.show();
//            mProgressDialog.setCancelable(false);
//        }
//    }
//
//    // 调用隐藏
//    public void dismissProgressViewDialog() {
//        try {
//            if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
