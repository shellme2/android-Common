package com.eebbk.bfc.common.demo.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.eebbk.bfc.common.demo.BuildConfig;
import com.github.moduth.blockcanary.BlockCanaryContext;

import java.util.List;

/**
 * Created by Simon on 2017/3/24.
 */

public class BlockCanaryConfig extends BlockCanaryContext {
    private static final String TAG = "AppContext";

    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = App.getAppContext().getPackageManager()
                    .getPackageInfo(App.getAppContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "provideQualifier exception", e);
        }

        return qualifier;
    }

    @Override
    public String provideUid() {
        // 用户ID，对于我们来讲，可以随便写
        return "C2";
    }

    @Override
    public String provideNetworkType() {
        // 这个不知道是干什么的，先不管它
        return "4G";
    }

    @Override
    public int provideMonitorDuration() {
        // 单位是毫秒
        return 10*1000;
    }

    @Override
    public int provideBlockThreshold() {
        // 主要配置这里，多长时间的阻塞算是卡顿，单位是毫秒
        return 1000;
    }

    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }

    @Override
    public List<String> concernPackages() {
        List<String> list = super.provideWhiteList();

        return list;
    }

    @Override
    public List<String> provideWhiteList() {
        // 白名单，哪些包名的卡顿不算在内
        List<String> list = super.provideWhiteList();
        return list;
    }

    @Override
    public boolean stopWhenDebugging() {
        return true;
    }
}
