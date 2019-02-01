package com.eebbk.bfc.common.devices;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.common.inner.LogInner;
import com.eebbk.bfc.common.tools.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.NetworkInterface;

/**
 * 设备相关信息的工具类
 * Created by Simon on 2016/9/28.
 */
public final class DeviceUtils {
    private static final String TAG = "BfcCommon_DeviceUtils";
    /***
     * 如果获取MachineId时, 最后仍没找到数据, 则返回默认的值
     */
    public final static String DEFAULT_MACHINE_ID = "1234567890";

    /**
     * android在6.0上, 做了权限限制后, 默认返回的mac
     */
    private final static String DEFAULT_MAC = "02:00:00:00:00:00";

    private DeviceUtils() {
        throw new UnsupportedOperationException("Are u ok ?");
    }

    /***
     * 获取系统版本号, 即api的版本
     */
    public static int getSDK() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取IMEI, 可能返回空字符串
     * <p>
     * <p> 需要权限 android.permission.READ_PHONE_STATE </p>
     */
    public static
    @Nullable
    String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            LogInner.w(TAG, e, "获取IMEI失败");
            return "";
        }
    }

    /**
     * 获取MAC地址, 在6.0的系统上,不可靠;
     * 在6.0之上时, 不使用系统的android系统的方法,用其他的hack方法获取, 不保证全部兼容;
     * <p>
     * 在android6.0之后, 出于保护隐私的目的,google收紧权限, 获取mac地址时,将统一返回 02:00:00:00:00:00
     * 具体参考 https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html?hl=zh-cn#behavior-hardware-id
     * 如果是系统运用, 加上 android.permission.LOCAL_MAC_ADDRESS 权限, 还能获得
     * <p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>
     * 如果获取到的是 {@link DeviceUtils#DEFAULT_MAC}, 可能是获取失败了
     *
     * @return 获取的MAC地址
     */
    public static String getMac(Context context) {
        // 在android 6.0 上, 获取mac时 ,返回的是默认的mac, 具体参考 WifiInfo 源码
        String defaultMac = DEFAULT_MAC;
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return "";
        }
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        //获取的mac是默认值时, 就要开始用其他办法了
        if (defaultMac.equalsIgnoreCase(mac)) {
            mac = getMac();
        }
        if (StringUtils.isEmpty(mac)) {
            return defaultMac;
        }
        return mac;
    }

    /**
     * android6.0之上, mac地址返回的是默认值, 用jdk的方法来获取下
     */
    private static String getMac() {
        try {
            // wlan0 是通过循环打印分析得出的, 经验值, 应该不会变
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
            byte[] addr = networkInterface.getHardwareAddress();
            StringBuilder buf = new StringBuilder();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }

            return buf.toString();

        } catch (Exception e) {
            LogInner.w(TAG, e, "使用jdk方法获取mac地址失败");
            return null;
        }
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            return model.trim();
        } else {
            return "";
        }
    }

    /**
     * //todo 和硬件设备信息无关. 考虑删除该方法
     * <p>
     * 震动一下
     * 权限 <uses-permission android:name="android.permission.VIBRATE" />
     *
     * @param duration 震动的时间, 毫秒
     */
    @Deprecated
    public static void vibrate(Context context, long duration) {
        Vibrator vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }


    /**
     * 判断当前设备是否为手机
     *
     * @return 是手机返回{@code true}; 否则返回{@code false}
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephony.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取剩余的内存大小
     */
    public static long getMemoryAvailableSize(Context context) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    /**
     * 获取RAM总大小, 单位是 byte
     * api>=16 用AMS获得;
     * 其他通过读取系统的 /proc/meminfo 文件获得
     *
     * @return 返回内存大小, 单位是 byte; 获取失败, 返回 0;
     */
    public static long getMemoryTotalSize(Context context) {
        long totalMemory = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            totalMemory = mi.totalMem;
        } else {
            RandomAccessFile reader;
            String load;
            try {
                reader = new RandomAccessFile("/proc/meminfo", "r");
                load = reader.readLine().replaceAll("\\D+", "");
                totalMemory = (long) Integer.parseInt(load);
                reader.close();
            } catch (IOException e) {
                LogInner.w(TAG, e, "读取/proc/meminfo去获取RAM大小失败");
            }
        }
        return totalMemory;
    }

    /**
     * 获取cpu的核心数
     * <p>
     * 一般用来开启线程池, 根据cpu核心数设定线程池大小
     */
    public static int getCpuCoreSize() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取当前电池电量
     *
     * @return 当前电量的百分比 0~100;
     */
    public static int getBatteryLevel(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        assert intent != null;
        return intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }

    /**
     * 判断当时是否在充电
     */
    public static boolean isCharge(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        assert intent != null;
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        return status == BatteryManager.BATTERY_STATUS_CHARGING;
    }

    /**
     * 跳转到设置界面
     */
    public static void openSetting(Context context) {
        IntentUtils.startActivity(context, IntentUtils.getGoSettingsIntent());
    }

    /**
     * 获取机器唯一的id
     * <p>
     * 首先获取bbksn, 如果有则返回
     * <p>
     * 如果bbksn不存在, 则获取Build.getSerial()
     * <p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     * <p>
     * 如果Build.getSerial()不存在, 则获取IMEI
     * <p>
     * 如果IMEI也不存在, 则获取MAC
     * <p>
     * 如果MAC还是不存在, 则获取 Build.SERIAL
     * <p>
     * 如果最后还是不存在 则返回默认值 1234567890
     */
    public static String getMachineId(Context context) {
        String machineId = getBbkSn();
        if (TextUtils.isEmpty(machineId) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            machineId = Build.getSerial();
        }
        if (TextUtils.isEmpty(machineId) || TextUtils.equals(Build.UNKNOWN, machineId)) {
            machineId = getIMEI(context);
        }
        if (TextUtils.isEmpty(machineId)) {
            machineId = getMac(context);
        }
        if (TextUtils.equals(machineId, DEFAULT_MAC)) {
            machineId = Build.SERIAL;
        }
        if (TextUtils.isEmpty(machineId) || TextUtils.equals(Build.UNKNOWN, machineId)) {
            machineId = DEFAULT_MACHINE_ID;
        }
        return machineId;
    }

    /**
     * 获取bbk自己的序列号, 如果没有, 返回空字符串
     */
    public static String getBbkSn() {
        final String fileName = "/proc/bbksn";
        FileReader in = null;
        BufferedReader read = null;
        String str = "";

        try {
            in = new FileReader(fileName);
            read = new BufferedReader(in);

            String e;
            while ((e = read.readLine()) != null) {
                str = e;
            }
        } catch (Exception e) {
            LogInner.w(e, "获取bbksn失败");
        } finally {
            FileUtils.closeIO(in);
            FileUtils.closeIO(read);
        }
        return str;
    }

    /**
     * 获取系统的版本名称
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.INCREMENTAL;
    }
}
