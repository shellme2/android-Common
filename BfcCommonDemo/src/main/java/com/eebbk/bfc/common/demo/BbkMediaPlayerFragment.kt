package com.eebbk.bfc.common.demo

import android.net.Uri
import android.view.View
import com.eebbk.bfc.common.compat.BBKMediaPlayer
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Created by Simon on 2017/7/13.
 */

class BbkMediaPlayerFragment : BaseFragment() {

    override fun getTitle(): String {
        return "MediaPlayer"
    }

    internal override fun getLayoutRes(): Int {
        return R.layout.fragment_bbk_media_player
    }

    internal var mMediaPlayer = BBKMediaPlayer()

    override fun init() {

        findView<View>(R.id.btn_media_player_start).setOnClickListener({
            mMediaPlayer.reset()

            try {
                mMediaPlayer.setDataSource(context, musicUri)
            } catch (e: IOException) {
            }

            mMediaPlayer.setOnPreparedListener { mp -> mp.start() }
            mMediaPlayer.prepareAsync()
        })

        findView<View>(R.id.btn_media_player_data_byte).setOnClickListener({ v ->
            try {
                val `in` = context.contentResolver.openInputStream(musicUri)
                val audioData = ByteArray(`in`!!.available())
                val length = `in`.read(audioData)
                `in`.close()

                mMediaPlayer.reset()

                //                BBKMediaPlayer.create(mMediaPlayer).setDataSource(audioData, length);
                mMediaPlayer.setDataSource(audioData, length)

                mMediaPlayer.prepare()
                mMediaPlayer.start()
            } catch (e: FileNotFoundException) {
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })

        findView<View>(R.id.btn_media_player_loop_5).setOnClickListener({ v ->
            //            BBKMediaPlayer.create(mMediaPlayer).setLooping(0, 10 * 1000, 5);
            mMediaPlayer.setLooping(10, 25 * 1000, 5)
        })

        findView<View>(R.id.btn_speed_0dot5).setOnClickListener({ v ->
            //            BBKMediaPlayer.create(mMediaPlayer).setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_0_DOT_5);
            mMediaPlayer.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_0_DOT_5)
        })

        findView<View>(R.id.btn_speed_1).setOnClickListener({ v ->
            //            BBKMediaPlayer.create(mMediaPlayer).setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_0);
            mMediaPlayer.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_0)
        })

        findView<View>(R.id.btn_speed_1dot25).setOnClickListener({ v ->
            //            BBKMediaPlayer.create(mMediaPlayer).setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_25);
            mMediaPlayer.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_25)
        })

        findView<View>(R.id.btn_speed_1dot5).setOnClickListener({ v ->
            //            BBKMediaPlayer.create(mMediaPlayer).setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_5);
            mMediaPlayer.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_5)
        })

        findView<View>(R.id.btn_speed_2).setOnClickListener({ v ->
            //            BBKMediaPlayer.create(mMediaPlayer).setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_2_DOT_0);
            mMediaPlayer.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_2_DOT_0)
        })

        findView<View>(R.id.btn_test).setOnClickListener({ v ->
            mMediaPlayer.reset()
            try {
                mMediaPlayer.setDataSource(context, musicUri)
                mMediaPlayer.prepare()
                mMediaPlayer.start()

                //                BBKMediaPlayer bbkMediaPlayer = BBKMediaPlayer.create(mMediaPlayer);
                val bbkMediaPlayer = mMediaPlayer
                bbkMediaPlayer.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_5)
                bbkMediaPlayer.setLooping(0, 15 * 1000, 3)
                bbkMediaPlayer.setPlayTone(BBKMediaPlayer.TONE_TYPE.TONE_INDEX_NONE)
                bbkMediaPlayer.enableTimedTextTrackIndex(0)

            } catch (e: IOException) {
            }
        })
    }


    private val musicUri: Uri
        get() = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.a)
}
