package com.eebbk.bfc.common.devices;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.eebbk.bfc.common.file.FileUtils;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * <pre>
 *     Created by Simon on 2016/9/29.
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 将启动services的含蓄的Intent转换为明确的Intent;
     * android 5.0 后, service用含蓄的Intent无法启动(eg. new Intent("com.eebbk.thirdapp.sendsuggest"))
     *
     * @return 明确的意图; 如果对应的services意图不存在或者有多个, 返回null;
     */
    public static Intent createExplicitFromImplicitIntentForServices(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }


    /**
     * 获取安装App(支持6.0)的意图
     *
     * @param file 文件
     */
    public static Intent getInstallAppIntent(@NonNull File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type;
        if (Build.VERSION.SDK_INT < 23) {
            type = "application/vnd.android.package-archive";
        } else {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtils.getFileExtension(file));
        }
        intent.setDataAndType(Uri.fromFile(file), type);
        return intent;
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent;
    }

    /**
     * 获取启动App的意图
     * Returns null if it's not found
     *
     * @param context     上下文
     * @param packageName 包名
     * @return intent; 如果不存在,返回null
     */
    public static Intent getLaunchAppIntent(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getAppInfoSettingsIntent(String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent;
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return intent
     */
    public static Intent getShareImageIntent(String content, String imagePath) {
        return getShareImageIntent(content, new File(imagePath));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 文本
     * @param image   图片文件
     * @return intent 如果文件不存在.返回null
     */
    public static Intent getShareImageIntent(String content, File image) {
        if (!FileUtils.isFileExists(image)) return null;
        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片uri
     * @return intent
     */
    public static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 获取拍照的意图
     *
     * @param outUri 照片输出的uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转到图库,选择照片的Intent
     */
    public static Intent getPickIntentWithGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        return intent.setType("image/*");
    }

    private static Intent buildImageGetIntent(Uri saveTo, int outputX, int outputY, boolean returnData) {
        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
    }

    private static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
                                              int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        intent.putExtra("output", saveTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    private static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int outputX, int outputY, boolean returnData) {
        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
    }

    private static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
                                               int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriFrom, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uriTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    private static Intent buildImageCaptureIntent(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 启动activity
     * <p>
     * 从context启动activity, 自动添加需要的FLAG_ACTIVITY_NEW_TASK标志位
     */
    public static void startActivity(Context context, Intent intent) {
        if (context == null || intent == null) return;
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    /**
     * 跳转到桌面的Intent
     */
    public static Intent getGoHomeIntent() {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        return mHomeIntent;
    }

    /**
     * 获取跳转到设置界面的Intent
     *
     */
    public static Intent getGoSettingsIntent() {
        return new Intent(Settings.ACTION_SETTINGS);
    }

    /**
     * 拨打电话的Intent, 启动后将直接拨打电话
     * 需要权限 <uses-permission android:name="android.permission.CALL_PHONE"/>
     * 6.0后还需要手动请求权限
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getCallPhoneIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
    }

    /**
     * 跳转至拨号界面的Intent
     *
     * @param phoneNumber 电话号码电话号码
     */
    public static Intent getDialIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
    }

    /**
     * 跳转发送短信的Intent
     *
     * @param phoneNumber 电话号码
     * @param content     短信内容
     */
    public static Intent getSendSmsIntent(String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:"
                + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        return intent;
    }

    /**
     * 直接调用短信接口发短信,需要添加权限, 启动后将直接发送短信
     */
    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }


    /**
     * 选择所有文件
     */
    public final static String MINE_TYPE_ALL = "*/*";
    /**
     * 选择图片文件
     */
    public final static String MINE_TYPE_IMAGE = "image/*";
    /**
     * 选择音频文件
     */
    public final static String MINE_TYPE_AUDIO = "audio/*";
    /**
     * 选择视频文件
     */
    public final static String MINE_TYPE_VIDEO = "video/*";

    @StringDef({MINE_TYPE_ALL, MINE_TYPE_IMAGE, MINE_TYPE_AUDIO, MINE_TYPE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileType {
    }


    /**
     * 选择文件的Intent
     * <p>
     * <p>
     * 实际的代码如下<br/>
     * Intent intent = new Intent(Intent.ACTION_GET_CONTENT); <br/>
     * intent.addCategory(Intent.CATEGORY_OPENABLE);<br/>
     * intent.setType(mineType);<br/>
     * </p>
     *
     * @param mineType 根据传入的mineType选择文件
     *                 <li> image/* 图片 </li>
     *                 <li> {@code *\*} 任何文件 </li>
     *                 <li> audio/* 音频 </li>
     *                 <li> video/* 视频 </li>
     *                 <p>
     */
    public static Intent getPickFileIntent(@FileType String mineType) {
        if (TextUtils.isEmpty(mineType)) {
            mineType = MINE_TYPE_ALL;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(mineType);
        return intent;
    }


    /**
     * 检测 响应某个意图的Activity 是否存在
     */
    public static boolean isActivityAvailable(Context context, @NonNull Intent intent) {
        if (intent == null) return false;
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
