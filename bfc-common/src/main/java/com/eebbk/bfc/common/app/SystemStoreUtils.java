package com.eebbk.bfc.common.app;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;

/**
 * 用来把数据保存到{@link android.provider.Settings.System} 中的工具类
 * <p>
 * 保存的数据.可以在app清除数据之后, 仍然存在<br>
 * 用途: eg, 首次使用app, 弹出权限声明,需要保存的数据
 * <p>
 * Created by Simon on 2016/10/9.
 */

public class SystemStoreUtils {
    private static final String TAG = "BfcCommon_SystemSettingUtils";

    private SystemStoreUtils() {
        throw new UnsupportedOperationException("u con't instantantiate me  - -");
    }

    /**
     * 向 Setting.Systems中添加String类型的数据
     * <p>
     * <p> 可以用于权限声明的保存 </p>
     *
     * @return true 成功;  false保存数据失败
     * @throws {@link SecurityException} 非系统app调用, 没有权限, 会抛出异常
     */
    public static boolean put(Context context, @NonNull String key, @NonNull String value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.putString(context.getApplicationContext().getContentResolver(), key, value);
        } else {
            return Settings.System.putString(context.getApplicationContext().getContentResolver(), key, value);
        }
    }

    /**
     * 向 Setting.Systems中添加Int类型的数据
     * <p>
     *
     * @return true 成功;  false保存数据失败
     * @throws {@link SecurityException} 非系统app调用, 没有权限, 会抛出异常
     */
    public static boolean put(Context context, @NonNull String key, int value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.putInt(context.getApplicationContext().getContentResolver(), key, value);
        } else {
            return Settings.System.putInt(context.getApplicationContext().getContentResolver(), key, value);
        }
    }

    /**
     * 向 Setting.Systems中添加Float类型的数据
     * <p>
     *
     * @return true 成功;  false保存数据失败
     * @throws {@link SecurityException} 非系统app调用, 没有权限, 会抛出异常
     */
    public static boolean put(Context context, @NonNull String key, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.putFloat(context.getApplicationContext().getContentResolver(), key, value);
        } else {
            return Settings.System.putFloat(context.getApplicationContext().getContentResolver(), key, value);
        }
    }

    /**
     * 从 Setting.Systems中获取String类型的数据
     *
     * @return 保存的字符串; 如果没有数据 返回null
     */
    public static String get(Context context, @NonNull String key) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            return Settings.Global.getString(context.getApplicationContext().getContentResolver(), key);
        } else {
            return Settings.System.getString(context.getApplicationContext().getContentResolver(), key);
        }
    }

    /**
     * 从 Setting.Systems中获取Int类型的数据
     *
     * @return 保存的数据;  如果不存在保存的数据,或者保存的数据类型不对, 将返回 defaultValue
     */
    public static int get(Context context, @NonNull String key, int defaultValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(context.getApplicationContext().getContentResolver(), key, defaultValue);
        } else {
            return Settings.System.getInt(context.getApplicationContext().getContentResolver(), key, defaultValue);
        }
    }

    /**
     * 从 Setting.Systems中获取float类型的数据
     *
     * @return 保存的数据;  如果不存在保存的数据,或者保存的数据类型不对, 将返回 defaultValue
     */
    public static float get(Context context, @NonNull String key, float defaultValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getFloat(context.getApplicationContext().getContentResolver(), key, defaultValue);
        } else {
            return Settings.System.getFloat(context.getApplicationContext().getContentResolver(), key, defaultValue);
        }
    }

    /**
     * 删除具体的数据
     *
     * @param key 要删除的key
     * @return 受影响的数据条数
     * @throws {@link SecurityException} 非系统app调用, 没有权限, 会抛出异常
     */
    public static int remove(Context context, @NonNull String key) {
        key = Uri.encode(key);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.getContentResolver().delete(Settings.Global.getUriFor(key), null, null);
        } else {
            return context.getContentResolver().delete(Settings.System.getUriFor(key), null, null);
        }
    }
}
