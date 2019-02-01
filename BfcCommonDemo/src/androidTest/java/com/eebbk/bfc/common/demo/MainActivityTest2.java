package com.eebbk.bfc.common.demo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ck_dark_statusbar_text_color), withText("状态栏字体颜色 变深色"), isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.ck_dark_statusbar_text_color), withText("状态栏字体颜色 变深色"), isDisplayed()));
        appCompatCheckBox2.perform(click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.ck_dark_statusbar_text_color), withText("状态栏字体颜色 变深色"), isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.ck_dark_statusbar_text_color), withText("状态栏字体颜色 变深色"), isDisplayed()));
        appCompatCheckBox4.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_set_color), withText("设置颜色"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_set_color), withText("设置颜色"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.vibrate_btn), withText("震动"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.open_wifi_sw), withText("打开/关闭 wifi"), isDisplayed()));
        switch_.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.open_wifi_sw), withText("打开/关闭 wifi"), isDisplayed()));
        switch_2.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.long_toast_btn), withText("长toast"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.long_toast_btn), withText("长toast"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.cacle_btn), withText("取消"), isDisplayed()));
        appCompatButton6.perform(click());

    }

}
