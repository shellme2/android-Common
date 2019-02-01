package com.eebbk.bfc.common.inner;

import android.os.Looper;

/**
 * Created by Simon on 2016/10/18.
 */

public class Preconditions {

    /**
     * 检查参数, 确保参数不为null, 如果为null, 则抛异常
     */
    public static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    /**
     * 确保不在UI线程操作, 如果是, 则抛异常
     */
    public static void checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }
    }
}
