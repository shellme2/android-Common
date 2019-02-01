package com.eebbk.bfc.common.devices;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.eebbk.bfc.common.inner.LogInner;

/**
 * 获取屏幕显示信息的工具类
 * Created by Simon on 2016/9/29.
 */
public class DisplayUtils {
    private DisplayUtils() {
        throw new UnsupportedOperationException("u can't instantiate me - -");
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕的dpi
     */
    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取屏幕像素密度
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.heightPixels;
    }

    /**
     * 获取statusbar高度
     *
     * @param context
     * @return 状态栏高度, 像素值
     */
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 是否存在导航栏(NavigateionBar)
     * 目前系统的api并没有提供比较好的方法检测, 现在是通过检测是否有菜单键和返回键来判断的
     */
    public static boolean isExistNavigationBar(Context context) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey && !hasBackKey;
    }

    /**
     * 获取navigationbar高度
     * <p>
     * 有可能没有导航栏, 但是仍有这个高度, 请结合{@link #isExistNavigationBar(Context)}使用
     *
     * @return 导航栏的高度, 像素值
     */
    public static int getNavigationBarHeight(Context context) {
        int height = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 获取当前屏幕截图
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureScreen(Activity activity) {
        View view = activity.getWindow().getDecorView();
        return captureView(view);
    }


    /**
     * 获取view对应的图像
     */
    protected static Bitmap captureView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight());
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLocked(Context context) {
        KeyguardManager km = (KeyguardManager) context.getApplicationContext()
                .getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }


    /**
     * 获取屏幕亮度模式, 分自动和手动
     *
     * @return SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度 <b>
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    public static int getScreenBrightnessMode(Context context) {
        final Context ctx = context.getApplicationContext();
        try {
            return Settings.System.getInt(ctx.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            LogInner.w(e, "获取屏幕亮度模式失败");
            return Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        }
    }

    /**
     * 获取当前屏幕亮度, 0~255
     */
    public static int getScreenBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getApplicationContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            LogInner.w(e, "获取屏幕亮度失败");
            return 0;
        }
    }

    /**
     * 设置屏幕亮度和模式
     *
     * @param brightnessMode SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     *                       SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     * @param brightness     屏幕亮度值 1~255
     */
    public static void setScreenBrightness(Activity activity, int brightnessMode, int brightness) {
        setScreenBrightness(activity, brightness);
        setScreenBrightnessMode(activity, brightnessMode);
    }

    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    private static void setScreenBrightnessMode(Context context, int value) {
        Settings.System.putInt(context.getApplicationContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value);
    }

    private static void setScreenBrightness(Activity activity, float value) {
        Window mWindow = activity.getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        final float f = value / 255.0F;
        mParams.screenBrightness = f;
        mWindow.setAttributes(mParams);

        // 保存设置的屏幕亮度值
        Settings.System.putInt(activity.getApplicationContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) value);
    }

}
