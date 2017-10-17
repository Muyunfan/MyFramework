package com.muyunfan.fw.widget.utils.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * 作者：李程伟
 * 时间：2017/5/26 0026.9:34
 * 类描述：
 */

public class LaunchUtil {

    private Context context;
    private static LaunchUtil instance;
    private Bundle bundle;

    private LaunchUtil(Context context) {
        this.context = context;
        this.bundle = new Bundle();
    }

    /*
    instance
     */
    public static LaunchUtil getInstance(Context context) {
        return instance = new LaunchUtil(context);
    }

    public LaunchUtil putAll(Bundle bundle) {
        this.bundle.putAll(bundle);
        return instance;
    }

    public LaunchUtil putExtra(String key, Bundle bundle) {
        this.bundle.putBundle(key, bundle);
        return instance;
    }

    public LaunchUtil putExtra(String key, Serializable value) {
        this.bundle.putSerializable(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, int value) {
        this.bundle.putInt(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, String value) {
        this.bundle.putString(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, byte value) {
        this.bundle.putByte(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, byte[] value) {
        this.bundle.putByteArray(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, char value) {
        this.bundle.putChar(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, char[] value) {
        this.bundle.putCharArray(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, float value) {
        this.bundle.putFloat(key, value);
        return instance;
    }

    public LaunchUtil putExtra(String key, short value) {
        this.bundle.putShort(key, value);
        return instance;
    }

    /**
     * startActivity
     */
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        if (bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        this.context.startActivity(intent);
    }

    /*
    startActivityForResult with eventBus post back
     */
    public void startActivityForResult(Class<?> cls, String requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle.size() > 0) {
            bundle.putString("requestCode",requestCode);
            intent.putExtras(bundle);
        }
        this.context.startActivity(intent);
    }
}
