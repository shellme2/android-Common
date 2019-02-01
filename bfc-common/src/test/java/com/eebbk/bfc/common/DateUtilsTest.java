package com.eebbk.bfc.common;

import com.eebbk.bfc.common.tools.DateUtils;

import org.junit.Test;

/**
 * Created by Simon on 2016/10/9.
 */

public class DateUtilsTest {

    @Test
    public void testLeapYear(){
        System.out.println( DateUtils.isLeapYear(2016) );
        System.out.println( DateUtils.isLeapYear(2015) );
    }

    @Test
    public void testFormat(){
        System.out.println(  DateUtils.format(System.currentTimeMillis(), "EEE yy-MM-dd  h点  mm分") );
        System.out.println(  DateUtils.format(System.currentTimeMillis(), "HH") );
    }

    @Test
    public void currentTime(){
        System.out.println( DateUtils.getCurTimeString("yyyy") );
    }
}
