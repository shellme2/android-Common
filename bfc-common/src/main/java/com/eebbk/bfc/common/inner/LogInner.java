package com.eebbk.bfc.common.inner;

import android.util.Log;

import com.eebbk.bfc.common.BuildConfig;

/**
 * BfcCommon内部使用的log
 * <p>
 * Created by Simon on 2016/10/6.
 */

public class LogInner {
    private static final String DEFAULT_TAG = "BfcCommon";
    private final static boolean mShouldLog = BuildConfig.DEBUG;


    public static void w(String tag, Throwable e, String msg) {
        if (!mShouldLog) return;
        Log.w(tag, msg, e);
    }

    public static void e(String tag, Throwable e, String msg) {
        if (!mShouldLog) return;
        Log.e(tag, msg, e);
    }

    public static void e(String tag, String msg) {
        if (!mShouldLog) return;
        Log.e(tag, msg);
    }


    public static void w(Throwable e, String msg) {
        if (!mShouldLog) return;
        Log.w(DEFAULT_TAG, msg, e);
    }

    public static void d(String msg) {
        if (!mShouldLog) return;
        Log.d(DEFAULT_TAG, msg);
    }

}
