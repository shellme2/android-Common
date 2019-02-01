package com.eebbk.bfc.common.file;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import com.eebbk.bfc.common.devices.DeviceUtils;
import com.eebbk.bfc.common.reflect.ReflectException;

import java.io.File;

import static com.eebbk.bfc.common.reflect.Reflect.on;


/**
 * 公司内部机器获取存储目录的工具类
 * Created by Simon on 2017/6/8.
 */

public class BBKStorageUtils {
    // 支持SD card的机型
    private final static String[] SUPPOORT_SDCARD_MODE = new String[]{"H5", "H8", "H8S", "H8SM", "H9", "H9S"};

    private BBKStorageUtils() {
    }

    /**
     * 公司内部 A 盘的位置
     */
    public static File getFlashA() {
        try {
            return on(Environment.class).call("getInternalStorageDirectory").get();
        } catch (ReflectException e) {
        }

        return null;
    }

    /**
     * 获取A盘的挂载状态
     *
     * @return {@link Environment#MEDIA_UNKNOWN}, {@link Environment#MEDIA_REMOVED},
     * {@link Environment#MEDIA_UNMOUNTED}, {@link Environment#MEDIA_CHECKING},
     * {@link Environment#MEDIA_NOFS}, {@link Environment#MEDIA_MOUNTED},
     * {@link Environment#MEDIA_MOUNTED_READ_ONLY}, {@link Environment#MEDIA_SHARED},
     * {@link Environment#MEDIA_BAD_REMOVAL}, {@link Environment#MEDIA_UNMOUNTABLE} or ""
     */
    public static String getFlashAStorageState() {
        try {
            return on(Environment.class).call("getInternalStorageState").get();
        } catch (ReflectException e) {
        }
        return null;
    }

    /**
     * 公司内部 B 盘的位置, 即公司机器sd卡的位置
     */
    private static File getFlashB() {
        try {
            // 系统定义的B盘方法  public static File getExternalFlashStorageDirectory()
            return on(Environment.class).call("getExternalFlashStorageDirectory").get();
        } catch (ReflectException e) {
        }

        return null;
    }

    private static String getFlashBStorageState() {
        try {
            return on(Environment.class).call("getExternalFlashStorageState").get();
        } catch (ReflectException e) {
        }
        return null;
    }

    /**
     * 获取内置盘(机器自身存储)的的位置
     */
    public static File getExternalStorage() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取内置盘的挂载状态
     *
     * @return {@link Environment#MEDIA_UNKNOWN}, {@link Environment#MEDIA_REMOVED},
     * {@link Environment#MEDIA_UNMOUNTED}, {@link Environment#MEDIA_CHECKING},
     * {@link Environment#MEDIA_NOFS}, {@link Environment#MEDIA_MOUNTED},
     * {@link Environment#MEDIA_MOUNTED_READ_ONLY}, {@link Environment#MEDIA_SHARED},
     * {@link Environment#MEDIA_BAD_REMOVAL}, {@link Environment#MEDIA_UNMOUNTABLE} or ""
     */
    public static String getExternalStorageState() {
        return Environment.getExternalStorageState();
    }

    /**
     * 机器是否支持使用SD卡, 即是否有SD的卡槽
     */
    public static boolean isSupportSDCard() {
        for (String mode : SUPPOORT_SDCARD_MODE) {
            if (mode.equalsIgnoreCase(DeviceUtils.getModel())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取SD卡的路径
     */
    public static File getSDCard() {
        return getFlashB();
    }

    /**
     * 获取SD卡的状态
     */
    public static String getSDCardStorageState() {
        return getFlashBStorageState();
    }


    /**
     * 获取OTG的文件位置
     *
     * @return OTG对应的位置, 如果不存在 返回null
     */
    public static File getOTG(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Object[] arrayOfObject = (Object[]) sm.getClass().getMethod("getVolumeList").invoke(sm);
            if ((arrayOfObject != null)) {
                int length = arrayOfObject.length;
                for (int i = 0; i < length; i++) {
                    //是否允许通过USB分享，返回值：内部共享存储空间（false）、SD 卡(true)、U盘(false)
                    Boolean allowMassStorage = (Boolean) arrayOfObject[i].getClass()
                            .getMethod("allowMassStorage")
                            .invoke(arrayOfObject[i]);

                    //是否可移除，返回值：内部共享存储空间（false）、SD 卡(true)、U盘(true)
                    Boolean isRemovable = (Boolean) arrayOfObject[i].getClass()
                            .getMethod("isRemovable")
                            .invoke(arrayOfObject[i]);

                    // 获取存储路径
                    String path = (String) arrayOfObject[i].getClass()
                            .getMethod("getPath")
                            .invoke(arrayOfObject[i]);

                    //此存储路径，是否处于挂载状态，返回值：mounted—挂载，可以使用
                    String volumeState = (String) sm.getClass()
                            .getMethod("getVolumeState", new Class[]{String.class})
                            .invoke(sm, new Object[]{path});
                    if (!allowMassStorage && isRemovable && "mounted".equals(volumeState)) {
                        return new File(path);
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
