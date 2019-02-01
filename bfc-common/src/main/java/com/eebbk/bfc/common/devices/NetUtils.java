package com.eebbk.bfc.common.devices;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.telephony.TelephonyManager;

import com.eebbk.bfc.common.inner.LogInner;
import com.eebbk.bfc.common.tools.StringUtils;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Simon on 2016/9/29.
 */
public final class NetUtils {

    private NetUtils() {
        throw new UnsupportedOperationException("Are u ok ?");
    }

    /**
     * 判断是否有网络连接
     *
     * @return true 有网络链接; 否则 false
     */
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            return null != info && info.isConnected();
        }
        return false;
    }

    /**
     * 判断wifi是否连接
     *
     * @return true wifi已链接; 否则 false
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo == null) {
            return false;
        }
        NetworkInfo.State state = networkInfo.getState();
        return state != null && NetworkInfo.State.CONNECTED == state;
    }

    /**
     * 判断移动网络是否连接
     *
     * @return true 移动网络已链接; 否则 false
     */
    public static boolean isMobileDataConnected(@NonNull Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo == null) {
            return false;
        }
        NetworkInfo.State state = networkInfo.getState();
        return state != null && NetworkInfo.State.CONNECTED == state;

    }

    /**
     * 获取当前的ip地址
     */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "";
    }


    /**
     * 打开移动网络网络设置界面
     * <p>
     * 国产的部分机型, 对系统设置改过, 有的无法跳转,或者跳转出错, 请app做好容错
     */
    public static void openMobileDataSettings(@NonNull Context context) {

        IntentUtils.startActivity(context, new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
    }

    /**
     * 打开网络设置界面
     */
    public static void openWifiSetting(@NonNull Context ctx) {
        IntentUtils.startActivity(ctx, new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    /**
     * 打开或者关闭wifi
     * <p> 需要添加权限  android.permission.ACCESS_WIFI_STATE </p>
     *
     * @param ctx    Context对象
     * @param enable {@code true}打开; {@code false} turn off
     */
    public static void enableWifi(@NonNull Context ctx, boolean enable) {
        WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enable);
    }

    /**
     * 判断wifi是否打开; <b> 注意,该方法仅判断wifi开关是否打开, 打开不一定连接到网络 </b>
     *
     * @return true wifi已打开; 反之false
     */
    public static boolean isWifiEnable(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    /**
     * 用于ping网络, 确定网络能否访问互联网, 有些认证网络,连接了也访问不了网络; <b>比较耗时, 请自行决定, 调用的线程</b>
     * <p>
     * <b>经过测试, 在移动数据网络下, 结果不准确, 慎用</b>
     * <p>
     * <br>
     * 要ping通, app需要有网络访问权限  <uses-permission android:name="android.permission.INTERNET" />
     * <p> ping -c 1 -w 10 114.114.114.114; -c 指次数, -w 超时时间; </p>
     *
     * @param urlOrIp 对应的url地址; eg. www.baidu.com, 或者 114.114.114.114
     * @return true 可以ping成功; false 失败
     */
    @Deprecated
    @WorkerThread
    public static boolean ping(@NonNull String urlOrIp) {
        if (StringUtils.isEmpty(urlOrIp)) return false;
        String pingStr = "/system/bin/ping -c1 " + urlOrIp;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec(pingStr);
            int exitValue = ipProcess.waitFor();
//            返回0 成功; 2失败
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            LogInner.w(e, "ping 网络出错");
            return false;
        }
    }

    /**
     * 用于ping网络, 确定网络能否访问互联网, 有些认证网络,连接了也访问不了网络; <b>比较耗时, 请自行决定, 调用的线程</b>
     * <p>
     * <b>经过测试, 在移动数据网络下, 结果不准确, 慎用</b>
     * <br>
     * 要ping通, app需要有网络访问权限  <uses-permission android:name="android.permission.INTERNET" />
     *
     * @param urlOrIp    对应的url地址; eg. www.baidu.com, 或者 114.114.114.114
     * @param count      ping的次数
     * @param timeSecond 最长执行时间
     */
    @Deprecated
    @WorkerThread
    public static boolean ping(String urlOrIp, int count, int timeSecond) {
        if (StringUtils.isEmpty(urlOrIp)) return false;
        String pingStr = "/system/bin/ping -c" + count + " -w" + timeSecond + " " + urlOrIp;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec(pingStr);
            int exitValue = ipProcess.waitFor();
//            返回0 成功; 2失败
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            LogInner.w(e, "ping 网络出错");
            return false;
        }
    }

//    ##############################################################################################
//    下面是网络类型相关, 自己定义的, 不是系统原生, 可能不准确

    public static final int NETWORK_WIFI = 1;    // wifi network
    public static final int NETWORK_4G = 4;    // "4G" networks
    public static final int NETWORK_3G = 3;    // "3G" networks
    public static final int NETWORK_2G = 2;    // "2G" networks
    public static final int NETWORK_UNKNOWN = 5;    // unknown network
    public static final int NETWORK_BLUETOOTH = 6;    // 蓝牙网络
    public static final int NETWORK_NO = -1;   // no network

    @IntDef({NETWORK_WIFI, NETWORK_4G, NETWORK_3G, NETWORK_2G, NETWORK_UNKNOWN, NETWORK_BLUETOOTH, NETWORK_NO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetType {
    }

    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN = 18;

    /**
     * 获取当前的网络类型(WIFI,2G,3G,4G,蓝牙)
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param info 网络信息
     * @return 网络类型
     * <ul>
     * <li>{@link #NETWORK_WIFI   } = 1;</li>
     * <li>{@link #NETWORK_4G     } = 4;</li>
     * <li>{@link #NETWORK_3G     } = 3;</li>
     * <li>{@link #NETWORK_2G     } = 2;</li>
     * <li>{@link #NETWORK_UNKNOWN} = 5;</li>
     * <li>{@link #NETWORK_BLUETOOTH} = 6;</li>
     * <li>{@link #NETWORK_NO     } = -1;</li>
     * </ul>
     */
    @NetType
    public static int getNetworkType(NetworkInfo info) {
        int netType = NETWORK_NO;
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {

                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NETWORK_2G;
                        break;

                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NETWORK_3G;
                        break;

                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NETWORK_4G;
                        break;
                    default:

                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            netType = NETWORK_3G;
                        } else {
                            netType = NETWORK_UNKNOWN;
                        }
                        break;
                }
            } else if (info.getType() == ConnectivityManager.TYPE_BLUETOOTH) {
                netType = NETWORK_BLUETOOTH;
            } else {
                netType = NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    /**
     * 获取当前的网络类型(WIFI,2G,3G,4G)
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param context 上下文
     * @return 网络类型
     * <ul>
     * <li>{@link #NETWORK_WIFI   } = 1;</li>
     * <li>{@link #NETWORK_4G     } = 4;</li>
     * <li>{@link #NETWORK_3G     } = 3;</li>
     * <li>{@link #NETWORK_2G     } = 2;</li>
     * <li>{@link #NETWORK_UNKNOWN} = 5;</li>
     * <li>{@link #NETWORK_BLUETOOTH} = 6;</li>
     * <li>{@link #NETWORK_NO     } = -1;</li>
     * </ul>
     */
    public static
    @NetType
    int getNetWorkType(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return getNetworkType(info);
    }

    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
