package com.muyunfan.fw.widget.utils.annotation;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类名称：com.muyunfan.fw.widget.utils.annotation
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/10/31.13:55
 * 修改人： 李程伟
 * 修改时间：2017/10/31.13:55
 * 修改备注：
 */
public class AnnotationUtils {
    public static void injectViews(Activity activity) {
        Class<? extends Activity> object = activity.getClass(); // 获取activity的Class
        Field[] fields = object.getDeclaredFields(); // 通过Class获取activity的所有字段
        for (Field field : fields) { // 遍历所有字段
            // 获取字段的注解，如果没有ViewInject注解，则返回null
            FindViewById findViewById = field.getAnnotation(FindViewById.class);
            if (findViewById != null) {
                int viewId = findViewById.value(); // 获取字段注解的参数，这就是我们传进去控件Id
                if (viewId != -1) {
                    try {
                        // 获取类中的findViewById方法，参数为int
                        Method method = object.getMethod("findViewById", int.class);
                        // 执行该方法，返回一个Object类型的View实例
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        // 把字段的值设置为该View的实例
                        field.set(activity, resView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
