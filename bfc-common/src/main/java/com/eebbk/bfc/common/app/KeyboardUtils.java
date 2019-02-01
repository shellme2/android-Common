package com.eebbk.bfc.common.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.eebbk.bfc.common.inner.LogInner;

/**
 * 软键盘的工具类
 * Created by Simon on 2016/9/29.
 */
public class KeyboardUtils {
    /**
     * 判断软键盘是否打开
     * <p>
     * 系统没有提供判断的api, 这个是读取的DecorView的可视区域,和屏幕高度对比;<br>
     * 如果可以区域高度明显小于屏幕高度, 则认为软键盘已经打开
     *
     * @return true 已经打开; false 关闭
     */
    public static boolean isSoftkeyboardShow(Activity activity) {
        View rootView = activity.getWindow().getDecorView();
        final int softKeyboardHeight = 200;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    /**
     * 显示输入法
     */
    public static void showSoftKeyborad(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            LogInner.w(e, "打开软键盘出错");
        }
    }

    /**
     * 隐藏键盘
     * 强制隐藏
     */
    public static void hideSoftKeyborad(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            LogInner.w(e, "关闭软键盘出错");
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyborad(Activity activity) {
        if (null == activity.getCurrentFocus()) {
            LogInner.d("该activity中没有获取焦点的view, 不能打开软键盘");
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyborad(Activity activity) {
        if (null == activity.getCurrentFocus()) {
            LogInner.d("该activity中没有已经获取焦点的view, 不需要关闭软键盘");
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 点击屏幕空白区域隐藏软键盘（方法1）
     * <p>在onTouch中处理，未获焦点则隐藏</p>
     * <p>参照以下注释代码</p>
     */
    protected static void clickBlankArea2HideSoftInput0() {
        Log.i("tips", "U should copy the following code.");
        /*
        @Override
        public boolean onTouchEvent (MotionEvent event){
            if (null != this.getCurrentFocus()) {
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            return super.onTouchEvent(event);
        }
        */
    }

    /**
     * 点击屏幕空白区域隐藏软键盘（方法2）
     * <p>根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
     * <p>需重写dispatchTouchEvent</p>
     * <p>参照以下注释代码</p>
     */
    protected static void clickBlankArea2HideSoftInput1() {
        Log.i("tips", "U should copy the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        // 获取InputMethodManager，隐藏软键盘
        private void hideKeyboard(IBinder token) {
            if (token != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        */
    }

}
