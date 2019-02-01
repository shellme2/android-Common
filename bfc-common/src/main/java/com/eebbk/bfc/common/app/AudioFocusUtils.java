package com.eebbk.bfc.common.app;

import android.content.Context;
import android.media.AudioManager;

/**
 * @author hesn
 * @function 音频焦点
 * @date 17-4-6
 * @company 步步高教育电子有限公司
 */

public class AudioFocusUtils {

    private static AudioManager mAudioManager;

    /**
     * 释放音频焦点
     */
    public static void abandonAudioFocus(){
        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(afChangeListener);
            mAudioManager = null;
        }
    }

    /**
     * 获取音频焦点
     * @param context context
     */
    public static void requestAudioFocus(Context context) {
        if(mAudioManager == null){
            mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        }

        mAudioManager.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent
                // focus.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                AudioManager.AUDIOFOCUS_GAIN);
    }

    private static AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    break;
                case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                    break;
                default:
                    break;
            }
        }
    };

}
