package com.eebbk.bfc.common.app;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.Window;

import com.eebbk.bfc.common.inner.LogInner;

import java.lang.reflect.Method;

/**
 * 对dialog操作的工具类
 * 目前只针对公司内部手机有效
 * Created by Simon on 2016/10/9.
 */

public class DialogUtils {

    /**
     * 取消Dialog的弹框没动画 <br/>
     * 在dialog.show()之前调用
     */
    private final static int PRIVATE_FLAG_PREVENT_DIALOG_NO_ANIMATE = 0x10000000;


    /**
     * 取消Dialog的弹框没动画 <br/>
     * 在dialog.show()之前调用
     * <p>
     * 目前只支持m1000和m2000
     */
    private static void setNoAnimate(@NonNull Dialog dialog) {
        Window window = dialog.getWindow();
        try {
            Method method = Window.class.getDeclaredMethod("setPrivateFlags", int.class, int.class);
            method.setAccessible(true);
            method.invoke(window, PRIVATE_FLAG_PREVENT_DIALOG_NO_ANIMATE, PRIVATE_FLAG_PREVENT_DIALOG_NO_ANIMATE);
        } catch (Exception e) {
            LogInner.w(e, "设置取消dialog动画发生异常");
        }
    }


}
