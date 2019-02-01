package com.eebbk.bfc.common.compat

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.eebbk.bfc.common.demo.R
import com.eebbk.bfc.common.inner.LogInner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Simon on 2017/7/17.
 */

@RunWith(AndroidJUnit4::class)
class BBKMediaPlayerTest {

    private var mBBKMediaPlayer: BBKMediaPlayer? = null

    @Before
    fun setUp() {
        val mediaPlayer = MediaPlayer()
        mBBKMediaPlayer = BBKMediaPlayer.create(mediaPlayer)

        mBBKMediaPlayer?.reset()
    }

    @After
    fun tearDown() {
        mBBKMediaPlayer?.release()
    }


    @Test
    fun setDataSource() {
        mBBKMediaPlayer?.let {
            it.setDataSource(context, musicUri)
            it.prepare()
            it.start()
        }

        sleepCurrentThread(15 * 1000)

        LogInner.d("setDataSource over")
    }


    @Test
    fun setLooping() {
        playMusic()

        mBBKMediaPlayer?.setLooping(10 * 1000, 20 * 1000, 5)

        sleepCurrentThread(60 * 1000)
    }

    @Test
    fun setSpeed() {
        playMusic()

        mBBKMediaPlayer?.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_2_DOT_0)
        sleepCurrentThread(10 * 1000)
        mBBKMediaPlayer?.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_0_DOT_5)
        sleepCurrentThread(10 * 1000)
        mBBKMediaPlayer?.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_25)
        sleepCurrentThread(10 * 1000)
        mBBKMediaPlayer?.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_0_DOT_75)
        sleepCurrentThread(10 * 1000)
        mBBKMediaPlayer?.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_75)
        sleepCurrentThread(10 * 1000)
        mBBKMediaPlayer?.setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_0_DOT_3)
        sleepCurrentThread(30 * 1000)

    }


    private fun playMusic(): BBKMediaPlayer? {
        mBBKMediaPlayer?.let {
            it.reset()
            it.setDataSource(context, musicUri)
            it.prepare()
            it.start()
        }

        return mBBKMediaPlayer
    }

    // 测试没有界面  退出后  音乐播放会自动停掉  设置一个睡眠等待  阻止测试代码的退出
    private fun sleepCurrentThread(time: Long = 10 * 1000) {
        Thread.sleep(time)
    }

    private val musicUri: Uri
        get() = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.a)

    private val context: Context
        get() = InstrumentationRegistry.getTargetContext()
}
