package com.eebbk.bfc.common.compat

import android.content.Context
import android.provider.Settings
import android.support.test.InstrumentationRegistry
import org.junit.Assert

/**
 * Created by Simon on 2017/7/18.
 */
class BBKSystemPropertyUtilsTest {
    @org.junit.Before
    @Throws(Exception::class)
    fun setUp() {
        Settings.System.putInt(context.contentResolver, BBKSystemPropertyUtils.JUNIOR_MARK, 1)
    }

    @org.junit.After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @org.junit.Test
    fun getJuniorMarkValue() {
        val juniorMark = BBKSystemPropertyUtils.getJuniorMarkValue(context)

        // 不知道当前系统具体的是中学或者小学, 在运行之前  直接写死值为 1
        Assert.assertEquals(juniorMark, 1)
    }

    private val context: Context
        get() = InstrumentationRegistry.getContext()

}