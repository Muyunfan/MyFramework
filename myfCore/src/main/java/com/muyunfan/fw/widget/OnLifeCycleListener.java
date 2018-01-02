package com.muyunfan.fw.widget;

import android.os.Bundle;

/**
 * 类名称：OnLifeCycleListener
 * 类描述：生命周期接口
 * 创建人：李程伟
 * 创建时间：2017/12/28.15:13
 * 修改人： 李程伟
 * 修改时间：2017/12/28.15:13
 * 修改备注：
 */
public interface OnLifeCycleListener {

    void onCreate(Bundle saveState);

    void onResume();

    void onPause();

    void onDestroy();
}
