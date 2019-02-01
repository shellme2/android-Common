package com.eebbk.bfc.common.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.eebbk.bfc.common.devices.IntentUtils;
import com.eebbk.bfc.common.devices.ShellUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.common.inner.LogInner;
import com.eebbk.bfc.common.tools.StringUtils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Simon on 2016/9/29.
 */
public class AppUtils {
    private static final String TAG = "BfcCommon_AppUtils";

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断指定包名的app是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isAppInstalled(@NonNull Context context, @NonNull String packageName) {
        // 获取packageManager
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos == null) {
            return false;
        }

        // 用于存储所有已安装程序的包名
        for (PackageInfo packageInfo : packageInfos) {
            if (packageName.equals(packageInfo.packageName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断App是否是系统应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 不是系统app或者没安装改app, 返回false
     */
    public static boolean isSystemApp(@NonNull Context context, @NonNull String packageName) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 调用系统的apk安装管理器, 安装App
     *
     * @param context 上下文
     * @param apkFile apk文件
     */
    public static void installApp(@NonNull Context context, @NonNull File apkFile) {
        IntentUtils.startActivity(context, IntentUtils.getInstallAppIntent(apkFile));
    }

    /**
     * 静默安装App
     * 系统app才能调用
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param context 上下文
     * @param apkFile 要安装的apk文件
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    protected static boolean installAppSilent(@NonNull Context context, @NonNull File apkFile) {
        if (!FileUtils.isFileExists(apkFile)) {
            return false;
        }
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + apkFile.getPath();
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, !isSystemApp(context, context.getPackageName()), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * 调用系统的apk管理器, 卸载指定app
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApp(@NonNull Context context, @NonNull String packageName) {
        IntentUtils.startActivity(context, IntentUtils.getUninstallAppIntent(packageName));
    }

    /**
     * 静默卸载App
     * 系统app才能调用
     * <p>
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param context     上下文
     * @param packageName 包名
     * @param isKeepData  是否保留数据
     * @return {@code true}: 卸载成功<br>{@code false}: 卸载成功
     */
    protected static boolean uninstallAppSilent(Context context, @NonNull String packageName, boolean isKeepData) {
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + (isKeepData ? "-k " : "") + packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, !isSystemApp(context, context.getPackageName()), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * 跳转到app在设置里面的应用详情界面
     */
    public static void openAppInfoSettings(Context context) {
        openAppInfoSettings(context, context.getPackageName());
    }

    /**
     * 跳转到指定app, 在设置里面的应用详情界面
     *
     * @param packageName 包名
     */
    public static void openAppInfoSettings(Context context, @NonNull String packageName) {
        final Intent intent = IntentUtils.getAppInfoSettingsIntent(packageName);
        IntentUtils.startActivity(context, intent);
    }


    /**
     * 获取App包名
     *
     * @param context 上下文
     * @return App包名
     */
    public static String getPackageName(@NonNull Context context) {
        return context.getPackageName();
    }

    /**
     * 获取App名称
     *
     * @param context 上下文
     * @return App名称
     */
    public static String getAppName(@NonNull Context context) {
        return getAppName(context, context.getPackageName());
    }

    /**
     * 获取指定app的名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App名称, 如果不存在, 返回 null
     */
    public static String getAppName(Context context, @NonNull String packageName) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取App的 VersionName
     *
     * @param context 上下文
     * @return App版本名
     */
    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取指定App的 VersionName
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本名, 如果指定包名的app不存在,返回{@code null}
     */
    @Nullable
    public static String getVersionName(Context context, @NonNull String packageName) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本码
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本码; 如果指定包名的app不存在,返回 -1
     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 获取App签名
     *
     * @param context 上下文
     * @return App签名
     */
    public static Signature[] getSignature(Context context) {
        return getSignature(context, context.getPackageName());
    }

    /**
     * 获取App签名
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App签名; 如果指定包名的app不存在 或者未签名,返回{@code null}
     */
    public static Signature[] getSignature(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取签名的md5值
     *
     * @return 签名的md5值;
     */
    public static String getSignatureMd5(Context context) {
        Signature[] signs = getSignature(context);
        return getSignsMd5(signs);
    }

    /**
     * 获取签名的md5值
     *
     * @return 签名的md5值; 如果指定包名的app不存在返回null
     */
    public static String getSignatureMd5(Context context, @NonNull String packageName) {
        Signature[] signs = getSignature(context, packageName);
        return getSignsMd5(signs);
    }

    private static
    @Nullable
    String getSignsMd5(Signature[] signs) {
        if (signs == null) {
            return null;
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LogInner.w(TAG, e, "获取app签名md5时, 没找到md5算法");
            return null;
        }

        for (Signature sign : signs) {
            md.update(sign.toByteArray());
        }
        return StringUtils.bytes2HexString(md.digest());
    }

    /**
     * 判断app是否在前台运行, 即是否有activity显示在屏幕最上层
     * <p> 通过RunningTask判断 </p>
     *
     * @return 当前app是否在前台
     */
    public static boolean isForeground(Context context) {
        String packageName = context.getPackageName();
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return !TextUtils.isEmpty(packageName) && packageName.equals(cn.getPackageName());
    }


    /**
     * 启动指定包名的app
     * <p>
     * 启动和桌面图标点击启动的activity相同
     */
    public static void startApp(Context context, @NonNull String packageName) {
        Intent intent = IntentUtils.getLaunchAppIntent(context, packageName);
        if (intent == null) return;
        IntentUtils.startActivity(context, intent);
    }

    /**
     * 禁用某个组件, 通常用来隐藏桌面图标 <br/>
     * <p>
     * 权限限制, 不能禁用其他app的组件;不过root权限好像可以
     */
    protected static void enableComponent(Context context, ComponentName component, boolean enable) {
        context.getPackageManager().setComponentEnabledSetting(component,
                enable ? PackageManager.COMPONENT_ENABLED_STATE_DEFAULT : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 启用某个组件
     */
    public static void enableComponent(Context context, Class componentClass) {
        ComponentName componentName = new ComponentName(context, componentClass);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用当前app某个组件
     */
    public static void disableComponent(Context context, Class componentClass) {
        ComponentName componentName = new ComponentName(context, componentClass);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
