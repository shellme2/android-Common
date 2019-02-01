package com.eebbk.bfc.common.compat;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 公司内部. 操作window相关的接口
 * Created by Simon on 2017/7/10.
 */

public class BBKWindowUtils {
    final private static int PRIVATE_FLAG_PREVENT_HOME_KEY = 0x02000000;
    final private static int PRIVATE_FLAG_PREVENT_CAMERA_KEY = 0x01000000;
    final private static int PRIVATE_FLAG_PREVENT_POWER_KEY = 0x04000000;
    final private static int PRIVATE_FLAG_PREVENT_APP_SWITCH_KEY = 0x00100000;


    private BBKWindowUtils() {
    }

    /**
     * disable home key default action
     */
    public static void disableHomeKey(Activity activity) {
        if (activity == null) {
            return;
        }
        disableHomeKey(activity.getWindow());
    }

    /**
     * disable home key default action
     */
    public static void disableHomeKey(Dialog dialog) {
        if (null == dialog) {
            return;
        }
        disableHomeKey(dialog.getWindow());
    }

    /**
     * disable home key default action
     */
    public static void disableHomeKey(Window window) {
        if (window == null) {
            return;
        }
        setFlags(window, PRIVATE_FLAG_PREVENT_HOME_KEY, PRIVATE_FLAG_PREVENT_HOME_KEY);
    }

    /**
     * enable home key default action
     */
    public static void enableHomeKey(Activity activity) {
        if (activity == null) {
            return;
        }
        enableHomeKey(activity.getWindow());
    }

    public static void enableHomeKey(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        enableHomeKey(dialog.getWindow());
    }

    public static void enableHomeKey(Window window) {
        if (window == null) {
            return;
        }
        setFlags(window, 0X00000000, PRIVATE_FLAG_PREVENT_HOME_KEY);
    }

    public static void disableAppSwitchKey(Activity activity) {
        if (activity == null) {
            return;
        }

        disableAppSwitchKey(activity.getWindow());
    }

    public static void disableAppSwitchKey(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        disableAppSwitchKey(dialog.getWindow());
    }

    public static void disableAppSwitchKey(Window window) {
        if (window == null) {
            return;
        }
        setFlags(window, PRIVATE_FLAG_PREVENT_APP_SWITCH_KEY, PRIVATE_FLAG_PREVENT_APP_SWITCH_KEY);
    }

    public static void enableAppSwitchKey(Activity activity) {
        if (activity == null) {
            return;
        }
        enableAppSwitchKey(activity.getWindow());
    }

    public static void enableAppSwitchKey(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        enableAppSwitchKey(dialog.getWindow());
    }

    public static void enableAppSwitchKey(Window window) {
        if (window == null) {
            return;
        }

        setFlags(window, 0X00000000, PRIVATE_FLAG_PREVENT_APP_SWITCH_KEY);
    }

    // disable camera key default action
    private static void disableCameraKey(Window window) {
        if (window == null) {
            return;
        }
        setFlags(window, PRIVATE_FLAG_PREVENT_CAMERA_KEY, PRIVATE_FLAG_PREVENT_CAMERA_KEY);
    }

    // disable power key default action
    private static void disablePowerKey(Window window) {
        if (window == null) {
            return;
        }
        setFlags(window, PRIVATE_FLAG_PREVENT_POWER_KEY, PRIVATE_FLAG_PREVENT_POWER_KEY);
    }

    private static void setPrivateFlags(Window window, int flag, int mask) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = Window.class.getDeclaredMethod("setPrivateFlags", int.class, int.class);
        method.setAccessible(true);
        method.invoke(window, flag, mask);
    }

    private static void setFlags(Window window, int flag, int mask) {
        try {
            setPrivateFlags(window, flag, mask);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        }
    }

    /**
     * 禁止下拉通知栏
     */
    public static void disablePullStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }

        disablePullStatusBar(activity.getWindow());
    }

    /**
     * 禁止下拉通知栏
     */
    private static void disablePullStatusBar(Window window) {
        if (window == null) {
            return;
        }
        int systemUIVis = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(systemUIVis | 0x00000010);

    }

    /**
     * 允许下拉通知栏
     */
    public static void enablePullStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }
        enablePullStatusBar(activity.getWindow());
    }

    /**
     * enable pull down the status bar
     *
     * @param window
     */
    private static void enablePullStatusBar(Window window) {
        if (window == null) {
            return;
        }
        int systemUIVis = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(systemUIVis ^ 0x00000010);
    }

}
