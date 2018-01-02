package com.muyunfan.fw.core.app;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.muyunfan.fw.R;
import com.muyunfan.fw.core.bean.EventCenter;
import com.muyunfan.fw.core.code.APICode;
import com.muyunfan.fw.core.code.ActionCode;
import com.muyunfan.fw.core.presenter.BasePresenter;
import com.muyunfan.fw.widget.bar.StatusBarCompat;
import com.muyunfan.fw.widget.dialog.ProgressDialog;
import com.muyunfan.fw.widget.utils.bitmap.GlideUtil;
import com.muyunfan.fw.widget.utils.common.CheckUtil;
import com.muyunfan.fw.widget.utils.common.SharedPreferencesUtil;
import com.muyunfan.fw.widget.utils.common.TimeCount;
import com.muyunfan.fw.widget.utils.common.ToastUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.fail;

/**
 * 类名称：com.muyunfan.fw.BaseModule.App
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.10:38
 * 修改人： 李程伟
 * 修改时间：2017/5/25.10:38
 * 修改备注：
 */
public abstract class BaseActivity<V extends AppCompatActivity, T extends BasePresenter> extends AppCompatActivity {

    public static final int DEFAULT_COLOR = Color.parseColor("#ffffff");
    protected T presenter;
    protected ProgressDialog mProgressView;
    public static boolean isInitOver;
    private static String mac;
    public Bundle savedInstanceState;
    protected boolean initPresenterFinished = false;
    protected TimeCount timeCountProgress;
    protected Typeface typeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (null != getSupportActionBar()) {
            getSupportActionBar().hide();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(setLayout());
//        typeface = Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf");
        initialize();
        EventBus.getDefault().register(this);
        initView();
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attach((V) this);
            initData();
            presenter.initialize();
        }
        initPresenterFinished = true;
        setStatusBar();
    }

    public String getMac() {
        if (mac == null) {
            try {
                mac = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                if (mac == null) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    mac = tm.getDeviceId();
                }
                if (mac == null) {
                    mac = "0000000000000000";
                }
            } catch (Exception e) {
                mac = "0000000000000000";
                e.printStackTrace();
            }
        }
        return mac;
    }


    /**
     * 时间格式
     *
     * @param l
     * @return
     */
    public String getTime(long l) {
        Date d = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(d);
    }

    /**
     * 打电话
     *
     * @param phone
     */

    public void phoneCall(String phone) {
        if (CheckUtil.isEmpty(phone)) {
            ToastUtil.showShort("你所拨打的手机号为空");
            return;
        }
        if (CheckUtil.hasPermission(BaseActivity.this, Manifest.permission.CALL_PHONE)) {
            try {
//                Intent phoneIntent = new Intent("android.intent.action.CALL",
//                        Uri.parse("tel:" + phone));
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + phone));
                startActivity(phoneIntent);
            } catch (Throwable e) {
                e.printStackTrace();
                ToastUtil.showShort("phone:" + phone);
            }
        } else {
            checkPhonePermission();
        }
    }

    /**
     * 权限通话检测
     */
    private void checkPhonePermission() {
        performCodeWithPermission("需要拨打电话请按确认", new PermissionCallback() {
            @Override
            public void hasPermission() {
            }

            @Override
            public void noPermission() {
                //在这里做权限申请失败需要做的事情
            }
        }, Manifest.permission.CALL_PHONE);
    }

    protected abstract int setLayout();

    protected abstract String setViewTag();

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void eventThread(EventCenter event);

    @Subscribe
    public void onEventMainThread(EventCenter event) {
        if ((APICode.ERROR + setViewTag()).contains(event.getEventCode())) {
            ToastUtil.showShort((String) event.getData());
        } else if(ActionCode.ACTION_FINISH.equals(event.getEventCode())){
            finish();
        }else{
            eventThread(event);
        }
    }

    public String getViewTag(){
        return setViewTag();
    }


    /**
     * progressDialog
     */
    public void showProgressViewDialog() {
        if (null == mProgressView)
            mProgressView = ProgressDialog.createProgressDialog(this);
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
        try {
            if (mProgressView != null && mProgressView.isShowing()) {
                mProgressView.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    打开地图路径
    */
    protected void startRouteActivity(Class<?> clz, String currentCity, String targetCity,
                                      String startLng, String startLat,
                                      Double endLng, Double endLat) {
        Intent intent = new Intent(getApplicationContext(), clz);
        intent.putExtra("currentCity", currentCity);
        intent.putExtra("targetCity", targetCity);
        intent.putExtra("startLng", Double.valueOf(startLng));
        intent.putExtra("startLat", Double.valueOf(startLat));
        intent.putExtra("endLng", endLng);
        intent.putExtra("endLat", endLat);
        startActivity(intent);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void setResult(int resultCode, Class<?> cls) {
        setResult(resultCode, cls, null);
    }

    public void setResult(int resultCode, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
//        finish();
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param
     */
    protected void startActivity(Class<?> clazz, String key, String value) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param
     */
    protected void startActivity(Class<?> clazz, String key, boolean value) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz, String key, int value) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected <FV extends View> FV genericFindViewById(int id) {
        return (FV) findViewById(id);
    }

    private void initialize() {
        LogUtil.isDebug = true;
        GlideUtil.getContext(this.getApplicationContext());
        SharedPreferencesUtil.getInstance("dhx", this.getApplicationContext());
        ToastUtil.register(this.getApplicationContext());
        isInitOver = true;
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void startActivityThenKill(Class<?> clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }

    protected void postEvent(String code, Object object) {
        LogUtil.i("postCode:"+code);
        EventBus.getDefault().post(new EventCenter<Object>(code, object));
    }

    protected void postEvent(String code) {
        LogUtil.i("postCode:"+code);
        EventBus.getDefault().post(new EventCenter<Object>(code));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.dismissProgressViewDialog();
            presenter.deleteAttach();
            presenter = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    public void btn_back(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //isOpen若返回true，则表示输入法打开
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }
        finish();
    }

    //**************** Android M Permission (Android 6.0权限控制代码封装)

    protected int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissions == null || permissions.length == 0) return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        } else {
            //permission has not been granted.
            requestPermission(permissionDes, permissionRequestCode, permissions);
        }

    }

    /**
     * @param permissions 需要检测的权限
     * @return 返回true说明具有此权限
     */
    public boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.

//            Snackbar.make(getWindow().getDecorView(), requestName,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.common_ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ActivityCompat.requestPermissions(BaseAppCompatActivity.this,
//                                    permissions,
//                                    requestCode);
//                        }
//                    })
//                    .show();
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    }).show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
//                showToast("暂无权限执行相关操作！");
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void dismissDialog(Dialog dialog) {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    //********************** END Android M Permission ****************************************

    protected void setStatusBar(){
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_white));
        StatusBarCompat.darkStatusBar(this);
    }
}
