package com.muyunfan.fw.core.app;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muyunfan.fw.core.bean.EventCenter;
import com.muyunfan.fw.core.code.APICode;
import com.muyunfan.fw.core.presenter.BaseFPresenter;
import com.muyunfan.fw.widget.utils.common.CheckUtil;
import com.muyunfan.fw.widget.utils.common.ToastUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 类名称：com.muyunfan.fw.BaseModule.App
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/5/25.11:09
 * 修改人： 李程伟
 * 修改时间：2017/5/25.11:09
 * 修改备注：
 */
public abstract class BaseFragment<F extends Fragment, P extends BaseFPresenter> extends Fragment {

    protected Context context;
    protected View view;
    protected Activity activity;
    protected P presenter;
    protected boolean mIsPause;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("BaseFragment", "onCreate");
        context = this.getActivity();
        activity = this.getActivity();
        EventBus.getDefault().register(this);
        presenter = createPresenter();
        presenter.attach((F) this);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i("BaseFragment", "onCreateView");
        view = inflater.inflate(setLayout(), null);
        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i("BaseFragment", "onViewCreated");
        initViewsAndEvents();
        presenter.initialize();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    protected <T extends View> T findViewById(int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.dismissProgressViewDialog();
            presenter.deleteAttach();
            presenter = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int setLayout();


    protected abstract String setViewTag();

    protected abstract P createPresenter();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void eventThread(EventCenter event);

    @Subscribe
    public void onEventMainThread(EventCenter event) {
        if ((APICode.ERROR + setViewTag()).equalsIgnoreCase(event.getEventCode())) {
            ToastUtil.showShort((String) event.getData());
        } else {
            eventThread(event);
        }
    }

    public String getViewTag(){
        return setViewTag();
    }

    /*
    打开地图路径
     */
    protected void startRouteActivity(Class<?> clz, String currentCity, String targetCity,
                                      String startLng, String startLat,
                                      double endLng, double endLat) {
        Intent intent = new Intent(getActivity(), clz);
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
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz, String key, int value) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz, String key, String value) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void finishActivity() {
        activity.finish();
    }

    protected <T extends View> T genericFindViewById(int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPause = false;
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
        if (CheckUtil.hasPermission(getActivity(), Manifest.permission.CALL_PHONE)) {
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

    //**************** Android M Permission (Android 6.0权限控制代码封装) ***********************************

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
    private boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(getActivity(), p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(getActivity())
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
                        }
                    }).show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), p)) {
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
                ToastUtil.showShort("暂无权限执行相关操作！");
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

    //************************************* END Android M Permission ****************************************
}
