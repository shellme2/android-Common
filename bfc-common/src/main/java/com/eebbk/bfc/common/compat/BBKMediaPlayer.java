package com.eebbk.bfc.common.compat;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.media.PlaybackParams;
import android.media.SyncParams;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.eebbk.bfc.common.inner.LogInner;
import com.eebbk.bfc.common.reflect.Reflect;
import com.eebbk.bfc.common.reflect.ReflectException;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 系统MediaPlayer的代理类, 通过 {@link BBKMediaPlayer#create(MediaPlayer)} 方法创建该对象的实例;
 * 使用方法和系统MediaPlayer相同, 仅额外添加了部分方法;
 * <p>
 * Created by Simon on 2017/7/10.
 */

public class BBKMediaPlayer {

    public enum SPEED_LEVEL {
        SPEED_INDEX_0_DOT_0,
        SPEED_INDEX_0_DOT_3,        // 0.3倍
        SPEED_INDEX_0_DOT_5,        // 0.5倍
        SPEED_INDEX_0_DOT_75,        // 0.75倍
        SPEED_INDEX_1_DOT_0,        // 1.0倍
        SPEED_INDEX_1_DOT_25,        // 1.25倍
        SPEED_INDEX_1_DOT_5,        // 1.5倍
        SPEED_INDEX_1_DOT_75,        // 1.75倍
        SPEED_INDEX_2_DOT_0,        // 2.0倍
    }

    public enum TONE_TYPE {
        TONE_INDEX_NONE,
        TONE_INDEX_BLUE,
        TONE_INDEX_ORANGE,
        TONE_INDEX_PURPLE,
        TONE_INDEX_TEST,
    }


    private MediaPlayer mMediaPlayer;

    public BBKMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
    }

    private BBKMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    /**
     * 构建BBK内部的Mediaplayer, 用于调用系统添加的接口
     *
     * @param mediaPlayer 系统原生的MediaPlayer
     */
    public static BBKMediaPlayer create(MediaPlayer mediaPlayer) {
        return new BBKMediaPlayer(mediaPlayer);
    }


    public void setDataSource(byte[] bytes, int size)
            throws IOException, IllegalArgumentException, IllegalStateException {
        try {
            // Reflect.on(mMediaPlayer).call("setDataSource", bytes, size);
            Method method = MediaPlayer.class.getMethod("setDataSource", byte[].class, int.class);
            method.invoke(mMediaPlayer, bytes, size);
        } catch (NoSuchMethodException e) {
            LogInner.w(e, "反射调用出错");
        } catch (IllegalAccessException e) {
            LogInner.w(e, "反射调用出错");
        } catch (InvocationTargetException e) {
            Throwable exception = e.getTargetException();
            if (exception instanceof IOException) {
                throw (IOException) exception;
            } else if (exception instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) exception;
            } else if (exception instanceof IllegalStateException) {
                throw (IllegalStateException) exception;
            }
            LogInner.w(e, "反射调用出错");
        }
    }

    public void setDataSource(String mediaFilePath, String lrcFilePath)
            throws IOException, IllegalArgumentException, IllegalStateException {
        try {
            Reflect.on(mMediaPlayer).call("setDataSource", mediaFilePath, lrcFilePath);
        } catch (ReflectException e) {
            Throwable exception = e.getCause();
            if (exception instanceof IOException) {
                throw (IOException) exception;
            } else if (exception instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) exception;
            } else if (exception instanceof IllegalStateException) {
                throw (IllegalStateException) exception;
            }
            LogInner.w(e, "反射调用出错");
        }
    }

    /**
     * Sets the data source a mp3 and lrc fd and offset paired source to be sync played.
     *
     * @param mediaFileFd     the fd of audio/video format file.
     * @param mediaFileOffset the offset of the av in the fd.
     * @param mediaFileLength the length of the av in the fd.
     * @param lrcFileFd       the fd of the lrc file.
     * @param lrcFileOffset   the offset of the lrc in the fd.
     * @param lrcFileLength   the length of the lrc in the fd.
     * @throws IllegalStateException if it is called in an invalid state
     */

    public void setDataSource(FileDescriptor mediaFileFd, long mediaFileOffset, long mediaFileLength,
                              FileDescriptor lrcFileFd, long lrcFileOffset, long lrcFileLength)
            throws IOException, IllegalArgumentException, IllegalStateException {
        try {
            Reflect.on(mMediaPlayer).call("setDataSource", mediaFileFd, mediaFileOffset, mediaFileLength,
                    lrcFileFd, lrcFileOffset, lrcFileLength);
        } catch (ReflectException e) {
            Throwable exception = e.getCause();
            if (exception instanceof IOException) {
                throw (IOException) exception;
            } else if (exception instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) exception;
            } else if (exception instanceof IllegalStateException) {
                throw (IllegalStateException) exception;
            }
            LogInner.w(e, "反射调用出错");
        }

    }

    /**
     * Sets the player to play the specific start-end timed audio for passed times.
     * The setLooping is a lazy operation for the loop will not happen until AudioPlayer
     * reached the endTime and notify the Awesomeplayer. once audio playing paused, setLooping
     * wont work until next play.
     * The time gap between start and end time could be at least less than 500ms. less than 500ms
     * may still work, but make no sense for the application.
     *
     * @param startTime time(ms) of the looping
     * @param endTime   time(ms) of the looping
     * @param loopCount how many times for the looping
     */

    public void setLooping(int startTime, int endTime, int loopCount) {
        try {
            Reflect.on(mMediaPlayer).call("setLooping", startTime, endTime, loopCount);
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
    }

    public boolean enableTimedTextTrackIndex(int index) {
        try {
            return Reflect.on(mMediaPlayer).call("enableTimedTextTrackIndex", index).get();
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
        return false;
    }

    public boolean enableTimedText() {
        try {
            return Reflect.on(mMediaPlayer).call("enableTimedText").get();
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
        return false;
    }

    public boolean disableTimedText() {
        try {
            return Reflect.on(mMediaPlayer).call("disableTimedText").get();
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
        return false;
    }

    /**
     * 设置播放速率
     */
    public void setPlaySpeed(SPEED_LEVEL speedLevel) {
        Object level;
        switch (speedLevel) {
            case SPEED_INDEX_0_DOT_0:
                level = getSpeedLevelSystem("SPEED_INDEX_0_DOT_0");
                break;
            case SPEED_INDEX_0_DOT_3:
                level = getSpeedLevelSystem("SPEED_INDEX_0_DOT_3");
                break;
            case SPEED_INDEX_0_DOT_5:
                level = getSpeedLevelSystem("SPEED_INDEX_0_DOT_5");
                break;
            case SPEED_INDEX_0_DOT_75:
                level = getSpeedLevelSystem("SPEED_INDEX_0_DOT_75");
                break;
            case SPEED_INDEX_1_DOT_0:
                level = getSpeedLevelSystem("SPEED_INDEX_1_DOT_0");
                break;
            case SPEED_INDEX_1_DOT_25:
                level = getSpeedLevelSystem("SPEED_INDEX_1_DOT_25");
                break;
            case SPEED_INDEX_1_DOT_5:
                level = getSpeedLevelSystem("SPEED_INDEX_1_DOT_5");
                break;
            case SPEED_INDEX_1_DOT_75:
                level = getSpeedLevelSystem("SPEED_INDEX_1_DOT_75");
                break;
            case SPEED_INDEX_2_DOT_0:
                level = getSpeedLevelSystem("SPEED_INDEX_2_DOT_0");
                break;
            default:
                return;
        }

        setPlaySpeed(level);
    }

    private Object getSpeedLevelSystem(String levelType) {
        Object speedLevelInner = null;
        try {
            speedLevelInner = Reflect.on("android.media.MediaPlayer$SPEED_LEVEL").field(levelType).get();
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
        return speedLevelInner;
    }

    private void setPlaySpeed(Object level) {
        if (level == null) {
            return;
        }

        //  系统内部增加的接口是 Mediaplayer.setPlaySpeed(SPEED_LEVEL level)；

        try {
            // TODO: 2017/7/14 可能需要考虑性能, 把第一次获取到的 Method 对象进行缓存, 增加下一次反射的效率
            Reflect.on(mMediaPlayer).call("setPlaySpeed", level);
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
    }


    public void setPlayTone(TONE_TYPE toneType) {
        // 对应系统自己添加的 android.media.MediaPlayer$TONE_TYPE 枚举的一个类别,
        Object tone;

        switch (toneType) {
            case TONE_INDEX_NONE:
                tone = getToneTypeInSystem("TONE_INDEX_NONE");
                break;
            case TONE_INDEX_BLUE:
                tone = getToneTypeInSystem("TONE_INDEX_BLUE");
                break;
            case TONE_INDEX_ORANGE:
                tone = getToneTypeInSystem("TONE_INDEX_ORANGE");
                break;
            case TONE_INDEX_PURPLE:
                tone = getToneTypeInSystem("TONE_INDEX_PURPLE");
                break;
            case TONE_INDEX_TEST:
                tone = getToneTypeInSystem("TONE_INDEX_TEST");
                break;
            default:
                return;
        }

        if (tone == null) {
            return;
        }

        try {
            Reflect.on(mMediaPlayer).call("setPlayTone", tone);
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
    }

    private Object getToneTypeInSystem(String toneType) {
        Object toneTypeInner = null;
        try {
            toneTypeInner = Reflect.on("android.media.MediaPlayer$TONE_TYPE").field(toneType).get();
        } catch (ReflectException e) {
            LogInner.w(e, "反射调用出错");
        }
        return toneTypeInner;
    }

    // --------------------------------------------------------------------------------------------
    // 系统原先提供的方法  使用代理调用


    public void setDisplay(SurfaceHolder sh) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(sh);
        }

//        super.setDisplay(sh);
    }


    public void setSurface(Surface surface) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setSurface(surface);
        }

//        super.setSurface(surface);
    }


    public void setVideoScalingMode(int mode) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVideoScalingMode(mode);
        }
//        super.setVideoScalingMode(mode);
    }


    public void setDataSource(@NonNull Context context, @NonNull Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDataSource(context, uri);
        }
//        super.setDataSource(context, uri);
    }


    public void setDataSource(@NonNull Context context, @NonNull Uri uri, @Nullable Map<String, String> headers) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDataSource(context, uri, headers);
        }
//        super.setDataSource(context, uri, headers);
    }


    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {

        if (mMediaPlayer != null) {
            mMediaPlayer.setDataSource(path);
        }
//        super.setDataSource(path);
    }


    public void setDataSource(FileDescriptor fd) throws IOException, IllegalArgumentException, IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDataSource(fd);
        }
//        super.setDataSource(fd);
    }


    public void setDataSource(FileDescriptor fd, long offset, long length) throws IOException, IllegalArgumentException, IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDataSource(fd, offset, length);
        }
//        super.setDataSource(fd, offset, length);
    }

    @TargetApi(Build.VERSION_CODES.M)

    public void setDataSource(MediaDataSource dataSource) throws IllegalArgumentException, IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDataSource(dataSource);
        }
//        super.setDataSource(dataSource);
    }


    public void prepare() throws IOException, IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.prepare();
        }
//        super.prepare();
    }


    public void prepareAsync() throws IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.prepareAsync();
        }
//        super.prepareAsync();
    }


    public void start() throws IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
//        super.start();
    }


    public void stop() throws IllegalStateException {

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
//        super.stop();
    }


    public void pause() throws IllegalStateException {

        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
//        super.pause();
    }


    public void setWakeMode(Context context, int mode) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setWakeMode(context, mode);
        }
//        super.setWakeMode(context, mode);
    }


    public void setScreenOnWhilePlaying(boolean screenOn) {

        if (mMediaPlayer != null) {
            mMediaPlayer.setScreenOnWhilePlaying(screenOn);
        }
//        super.setScreenOnWhilePlaying(screenOn);
    }


    public int getVideoWidth() {
//        if (mMediaPlayer != null){
        return mMediaPlayer.getVideoWidth();
//        }
//        return super.getVideoWidth();
    }


    public int getVideoHeight() {
//        if (mMediaPlayer != null){
        return mMediaPlayer.getVideoHeight();
//        }
//        return super.getVideoHeight();
    }


    public boolean isPlaying() {
//        if (mMediaPlayer != null){
        return mMediaPlayer.isPlaying();
//        }
//        return super.isPlaying();
    }


    public void setPlaybackParams(@NonNull PlaybackParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMediaPlayer.setPlaybackParams(params);
        }
    }

    @NonNull

    public PlaybackParams getPlaybackParams() {
        return mMediaPlayer.getPlaybackParams();
    }


    public void setSyncParams(@NonNull SyncParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMediaPlayer.setSyncParams(params);
        }
    }

    @NonNull

    public SyncParams getSyncParams() {
        return mMediaPlayer.getSyncParams();
    }


    public void seekTo(int msec) throws IllegalStateException {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(msec);
        }
//        super.seekTo(msec);
    }

    @Nullable

    public MediaTimestamp getTimestamp() {
        return mMediaPlayer.getTimestamp();
    }


    public int getCurrentPosition() {
//        if (mMediaPlayer != null) {
        return mMediaPlayer.getCurrentPosition();
//        }
//        return super.getCurrentPosition();
    }


    public int getDuration() {
//        if (mMediaPlayer != null){
        return mMediaPlayer.getDuration();
//        }
//        return super.getDuration();
    }


    public void setNextMediaPlayer(MediaPlayer next) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.setNextMediaPlayer(next);
        }
    }


    public void release() {
        mMediaPlayer.release();
    }


    public void reset() {
        mMediaPlayer.reset();
    }

    public boolean isPlayerNull() {
        return mMediaPlayer == null;
    }


    public void setAudioStreamType(int streamtype) {
        mMediaPlayer.setAudioStreamType(streamtype);
    }


    public void setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaPlayer.setAudioAttributes(attributes);
        }
    }


    public void setLooping(boolean looping) {
        mMediaPlayer.setLooping(looping);
    }


    public boolean isLooping() {
        return mMediaPlayer.isLooping();
    }


    public void setVolume(float leftVolume, float rightVolume) {
        mMediaPlayer.setVolume(leftVolume, rightVolume);
    }


    public void setAudioSessionId(int sessionId) throws IllegalArgumentException, IllegalStateException {
        mMediaPlayer.setAudioSessionId(sessionId);
    }


    public int getAudioSessionId() {
        return mMediaPlayer.getAudioSessionId();
    }


    public void attachAuxEffect(int effectId) {
        mMediaPlayer.attachAuxEffect(effectId);
    }


    public void setAuxEffectSendLevel(float level) {
        mMediaPlayer.setAuxEffectSendLevel(level);
    }


    public MediaPlayer.TrackInfo[] getTrackInfo() throws IllegalStateException {
        return mMediaPlayer.getTrackInfo();
    }


    public void addTimedTextSource(String path, String mimeType) throws IOException, IllegalArgumentException, IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.addTimedTextSource(path, mimeType);
        }
    }


    public void addTimedTextSource(Context context, Uri uri, String mimeType) throws IOException, IllegalArgumentException, IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.addTimedTextSource(context, uri, mimeType);
        }
    }


    public void addTimedTextSource(FileDescriptor fd, String mimeType) throws IllegalArgumentException, IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.addTimedTextSource(fd, mimeType);
        }
    }


    public void addTimedTextSource(FileDescriptor fd, long offset, long length, String mime) throws IllegalArgumentException, IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.addTimedTextSource(fd, offset, length, mime);
        }
    }


    public int getSelectedTrack(int trackType) throws IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mMediaPlayer.getSelectedTrack(trackType);
        }

        return -1;
    }


    public void selectTrack(int index) throws IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.selectTrack(index);
        }
    }


    public void deselectTrack(int index) throws IllegalStateException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.deselectTrack(index);
        }
    }


    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
        mMediaPlayer.setOnPreparedListener(listener);
    }


    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(listener);
    }


    public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener) {
        mMediaPlayer.setOnBufferingUpdateListener(listener);
    }


    public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener listener) {
        mMediaPlayer.setOnSeekCompleteListener(listener);
    }


    public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener) {
        mMediaPlayer.setOnVideoSizeChangedListener(listener);
    }


    public void setOnTimedTextListener(MediaPlayer.OnTimedTextListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMediaPlayer.setOnTimedTextListener(listener);
        }
    }


    public void setOnTimedMetaDataAvailableListener(MediaPlayer.OnTimedMetaDataAvailableListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMediaPlayer.setOnTimedMetaDataAvailableListener(listener);
        }
    }


    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener(listener);
    }


    public void setOnInfoListener(MediaPlayer.OnInfoListener listener) {
        mMediaPlayer.setOnInfoListener(listener);
    }
}
