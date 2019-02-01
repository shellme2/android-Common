package com.eebbk.bfc.common;

import android.content.Context;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

/**
 * Created by Simon on 2016/10/8.
 */

@Ignore
@RunWith(RobolectricTestRunner.class)
public class TestUtils {

    public static Context getContext(){
        return RuntimeEnvironment.application;
    }
}
