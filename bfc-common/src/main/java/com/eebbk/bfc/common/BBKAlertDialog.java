package com.eebbk.bfc.common;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;

import static com.eebbk.bfc.common.reflect.Reflect.invokeMethod;

/**
 * 作者： jiazy
 * 日期： 2017/8/14.
 * 公司： 步步高教育电子有限公司
 * 描述：系统AlertDialog的代理类, 使用方法和系统AlertDialog相同, 仅额外添加了部分方法;
 */
public class BBKAlertDialog extends AlertDialog {
    /**
     * 系统定制样式参数
     */
    public enum DialogStyleType {
        /**
         * 卸载应用
         */
        UNINSTALL,
        /**
         * 删除单个
         */
        DELETE_SINGLE,
        /**
         * 删除多个
         */
        DELETE_MULTIPLE,
        /**
         * 无网络
         */
        NO_NETWORK,
        /**
         * 存储空间不足
         */
        NO_SPACE
    }

    protected BBKAlertDialog(Context context) {
        super(context);
    }

    public static class Builder extends AlertDialog.Builder {

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int themeResId) {
            super(context, themeResId);
        }

        /**
         * 兼容系统增加的设置正文Icon和声音提示的方法
         * @param styleType 指定使用的icon和声音样式，该值为系统定制
         */
        public Builder setBBKDialogStyle(DialogStyleType styleType) {
            int resId = switchResourceType(styleType);
            invokeMethod(AlertDialog.Builder.class, this, "setMessageIconAndSound",
                    new Class[]{int.class}, resId);

            return this;
        }

        /**
         * 兼容系统增加的设置正文Icon
         * @param messageIcon 正文Icon
         */
        public Builder setBBKMessageIcon(@DrawableRes int messageIcon) {
            invokeMethod(AlertDialog.Builder.class, this, "setMessageIcon",
                    new Class[]{int.class}, messageIcon);

            return this;
        }

        /**
         * 兼容系统增加的设置声音提示
         * @param soundUri 音源的uri，暂只支持从res/raw里的音源
         */
        public Builder setBBKSound(Uri soundUri) {
            invokeMethod(AlertDialog.Builder.class, this, "setSound",
                    new Class[]{Uri.class}, soundUri);

            return this;
        }

        /**
         * 兼容系统增加的设置声音提示
         * @param soundRes 音源的id，暂只支持从res/raw里的音源
         */
        public Builder setBBKSound(@RawRes int soundRes) {
            String path = "android.resource://" + getContext().getPackageName() + "/" + soundRes;//raw目录ok，assets目录无法播放.
            Uri uri = Uri.parse(path);
            invokeMethod(AlertDialog.Builder.class, this, "setSound",
                    new Class[]{Uri.class}, uri);

            return this;
        }

        private int switchResourceType(DialogStyleType resType) {
            int resId;
            switch (resType) {
                case UNINSTALL:
                    resId = 0;
                    break;
                case DELETE_SINGLE:
                    resId = 1;
                    break;
                case DELETE_MULTIPLE:
                    resId = 2;
                    break;
                case NO_NETWORK:
                    resId = 3;
                    break;
                case NO_SPACE:
                    resId = 7;
                    break;
                default:
                    resId = -1;
                    break;
            }

            return resId;
        }

    }

}
