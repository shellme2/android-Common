package com.eebbk.bfc.common;

import com.eebbk.bfc.common.app.SharedPreferenceUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by Simon on 2016/10/8.
 */
@Ignore
@RunWith(RobolectricTestRunner.class)
public class SPUtilsTest {
    private static final String IntKey1 = "IntKey1";
    private static final String IntKey2 = "IntKey2";

    private static final String LongKey1 = "LongKey1";
    private static final String LongKey2 = "LongKey2";

    private static final String FloatKey1 = "FloatKey1";
    private static final String FloatKey2 = "FloatKey2";

    private static final String BooleanKey1 = "BooleanKey1";
    private static final String BooleanKey2 = "BooleanKey2";

    private static final String StringKey1 = "StringKey1";
    private static final String StringKey2 = "StringKey2";

    SharedPreferenceUtils sharedPreferenceUtils;

    @Before
    public void setUp(){
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance( TestUtils.getContext() );
        sharedPreferenceUtils.put(IntKey1, 1);
        sharedPreferenceUtils.put(IntKey2, 2);
        long longData1 = 1;
        sharedPreferenceUtils.put(LongKey1, longData1);
        sharedPreferenceUtils.put(LongKey2, 2L);
        sharedPreferenceUtils.put(FloatKey1, 1.1f);
        sharedPreferenceUtils.put(FloatKey2, 2.1f);
        sharedPreferenceUtils.put(BooleanKey1, true);
        sharedPreferenceUtils.put(BooleanKey2, false);
        sharedPreferenceUtils.put(StringKey1, "str1");
        sharedPreferenceUtils.put(StringKey2, "str2");
    }

    @Test
    public void testPut(){
        sharedPreferenceUtils.put(IntKey1, 0);
    }


    @Test
    public void testGet(){
        int intValue1 = sharedPreferenceUtils.get(IntKey1, -1);
        System.out.println( "intValue1= " + intValue1 );
        Assert.assertEquals(1, intValue1);

        long longValue = sharedPreferenceUtils.get(LongKey1, -2L);
        System.out.println( "longValue= " + longValue );
        Assert.assertEquals(1, longValue);

        float floatValue1 = sharedPreferenceUtils.get(FloatKey1, -1f);
        System.out.println( "floatValue1= " + floatValue1 );
        Assert.assertEquals(1.1f, floatValue1, 0.01);

        boolean booleanValue = sharedPreferenceUtils.get(BooleanKey1, false);
        System.out.println( "booleanValue= " + booleanValue );
        Assert.assertEquals(true, booleanValue);

        String strValue = sharedPreferenceUtils.get(StringKey1, "");
        System.out.println( "strValue= " + strValue );
        Assert.assertEquals("str1", strValue);

        String strValue0 = sharedPreferenceUtils.get("none", null);
        System.out.println( "strValue0= " + strValue0 + "  isNull=" + (null == strValue0));
        Assert.assertEquals(null, strValue0);

    }


}
