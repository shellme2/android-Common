package com.eebbk.bfc.common.devices;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.eebbk.bfc.common.file.FileUtils;

import java.io.File;

/**
 * 媒体库工具类
 * Created by Simon on 2016/10/6.
 */

public final class MediaLibraryUtils {
    private MediaLibraryUtils() {
    }

    /**
     * 添加到媒体库
     * 发送媒体库更新的广播, 通知系统更新媒体库;
     * <p>
     * 在公司的手机上兼容, 在部分低系统的手机上可能无效
     *
     * @param filePath 文件路径, 不支持文件夹(公司内部手机做过处理,可以传文件夹)
     */
    public static void sendScanFileBroadcast(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    /**
     * 使用媒体库, 扫描指定文件
     *
     * @param filePaths 文件路径
     */
    public static void scanFile(Context context, String... filePaths) {
        MediaScannerConnection.scanFile(context, filePaths, null, null);
    }

    /**
     * 从媒体库中间删除文件
     *
     * @param filePath 文件的具体位置
     */
    public static void deleteFromMediaLibrary(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            return;
        }

        String fileExtension = FileUtils.getFileExtension(filePath);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);

        if (isImage(mimeType)) {
            removeImageFromLib(context, filePath);
        } else if (isAudio(mimeType)) {
            removeAudioFromLib(context, filePath);
        } else if (isVideo(mimeType)) {
            removeVideoFromLib(context, filePath);
        } else {
            removeFileFromLib(context, filePath);
        }

    }

    private static void removeFileFromLib(Context context, String filePath) {
        Uri uri = MediaStore.Files.getContentUri("external");
        context.getContentResolver().delete(uri, MediaStore.Files.FileColumns.DATA + "=?", new String[]{filePath});
    }

    private static int removeImageFromLib(Context context, String filePath) {
        ContentResolver resolver = context.getContentResolver();
        return resolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});
    }

    private static int removeAudioFromLib(Context context, String filePath) {
        ContentResolver resolver = context.getContentResolver();
        return resolver.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});
    }

    private static int removeVideoFromLib(Context context, String filePath) {
        ContentResolver resolver = context.getContentResolver();
        return resolver.delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});
    }

    private static boolean isImage(String mimeType) {
        return !TextUtils.isEmpty(mimeType) && mimeType.startsWith("image");

    }

    private static boolean isAudio(String mimeType) {
        return !TextUtils.isEmpty(mimeType) && mimeType.startsWith("audio");

    }

    private static boolean isVideo(String mimeType) {
        return !TextUtils.isEmpty(mimeType) && mimeType.startsWith("video");

    }

}
