package com.muyunfan.fw.basemodule.presenter;

import android.app.Activity;

import com.muyunfan.fw.basemodule.bean.EventCenter;
import com.muyunfan.fw.basemodule.code.APICode;
import com.muyunfan.fw.basemodule.model.BaseModel;
import com.muyunfan.fw.basemodule.model.ModelCallBack;
import com.muyunfan.fw.widget.dialog.ProgressDialog;
import com.muyunfan.fw.widget.utils.common.TimeCount;
import com.muyunfan.fw.widget.utils.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.amap.api.mapcore.util.cz.A;

/**
 * 类名称：com.muyunfan.fw.BaseModule.Presenter
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.11:09
 * 修改人： 李程伟
 * 修改时间：2017/5/25.11:09
 * 修改备注：
 */
public abstract class BasePresenter<V extends Activity, M extends BaseModel> implements ModelCallBack {

    protected final static int PAGE_SIZE = 10;
    protected WeakReference<V> mWeakReference;
    protected M model;
    protected TimeCount timeCountProgress;

    public void attach(V v) {
        mWeakReference = new WeakReference<V>(v);
        model = createPresenter();
        model.setModelCallBack(this);
    }

    protected abstract M createPresenter();

    public void deleteAttach() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }

    public boolean isViewAttached() {
        return mWeakReference != null && mWeakReference.get() != null;
    }

    public V getView() {
        if (mWeakReference != null) {
            return mWeakReference.get();
        }
        return null;
    }

    public abstract String setViewTag();

    public abstract void initialize();

    public abstract void modelCallBackSuccess(String requestCode, Object data);

    @Override
    public void success(final String requestCode, final Object data) {
        if (getView() != null) {
            getView().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissProgressViewDialog();
                    modelCallBackSuccess(requestCode, data);
                }
            });
        }
    }

    @Override
    public void fail(final String error) {
        if (getView() != null) {
            getView().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissProgressViewDialog();
                    ToastUtil.showShort(error);
                }
            });
        }
    }

    protected void postEvent(String code, Object object) {
        EventBus.getDefault().post(new EventCenter<Object>(code, object));
    }

    protected void postEvent(String code) {
        EventBus.getDefault().post(new EventCenter<Object>(code));
    }

    private int lastViewId;
    private long lastClickMillis;
    private long currentMillis;

    /*
    是否多次点击
     */
    public boolean isDoubleClick(int viewId, long millis) {
        currentMillis = System.currentTimeMillis();
        if ((currentMillis - lastClickMillis) < millis && lastViewId == viewId) {
            return true;
        } else {
            lastViewId = viewId;
            lastClickMillis = System.currentTimeMillis();
            return false;
        }
    }

    public boolean isDoubleClick(int viewId) {
        currentMillis = System.currentTimeMillis();
        if ((currentMillis - lastClickMillis) < 1000 && lastViewId == viewId) {
            return true;
        } else {
            lastViewId = viewId;
            lastClickMillis = System.currentTimeMillis();
            return false;
        }
    }

    protected ProgressDialog mProgressView;

    /**
     * progressDialog
     */
    public void showProgressViewDialog() {
        if (!isViewAttached()) {
            return;
        }
        if (null == mProgressView)
            mProgressView = ProgressDialog.createProgressDialog(getView());
        if (null != mProgressView) {
            mProgressView.show();
            mProgressView.setCancelable(false);
        }
        if (null == timeCountProgress)
            timeCountProgress = new TimeCount(30000L, 1000);//30秒计时器
        if (null != timeCountProgress) {
            if (!timeCountProgress.isStart()) {
                timeCountProgress.startTime();
            }
            timeCountProgress.setOnFinishListener(new TimeCount.OnFinishListener() {
                @Override
                public void finish() {
                    dismissProgressViewDialog();
                }
            });
        }
    }


    public void dismissProgressViewDialog() {
        if (!isViewAttached()) {
            return;
        }
        try {
            if (mProgressView != null && mProgressView.isShowing()) {
                mProgressView.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
