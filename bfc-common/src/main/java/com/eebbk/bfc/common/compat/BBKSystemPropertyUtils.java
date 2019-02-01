package com.eebbk.bfc.common.compat;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;

/**
 * 公司内部机器,在系统上添加的部分和系统有个的变量属性
 * Created by Simon on 2017/7/10.
 */

public class BBKSystemPropertyUtils {

    private BBKSystemPropertyUtils() {
    }

    // 中小学系统区分的字段
    public final static String JUNIOR_MARK = "junior_mark";

    /**
     * 获取中小学区分的字段的值
     *
     * @return 1 代表是junior 当前是小学;  0 代表是中学;
     */
    public static int getJuniorMarkValue(Context context) {
        return Settings.System.getInt(context.getContentResolver(), JUNIOR_MARK, 0);
    }


    private final static String EYE_PROTECT_MODE_KEY = "sys.bbksafe.eyeprotectmode";

    /**
     * 是否打开了护眼模式
     *
     * @return
     */
    private static boolean isEyeProtectMode() {
        return "eyeprotection".equalsIgnoreCase(SystemProperties.get(EYE_PROTECT_MODE_KEY));
    }

    /**
     * 大打开护眼模式
     */
    private static void openEyeProtectMode() {
        SystemProperties.set(EYE_PROTECT_MODE_KEY, "eyeprotection");
    }

    /**
     * 关闭护眼模式
     */
    private static void closeEyeProtectMode() {
        SystemProperties.set(EYE_PROTECT_MODE_KEY, "normal");
    }
}
