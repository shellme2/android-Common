package com.eebbk.bfc.common.demo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_set_color), withText("设置颜色"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_set_transparent), withText("状态栏透明"), isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_set_translucent), withText("状态栏半透明"), isDisplayed()));
        appCompatButton3.perform(click());

        pressBack();

        ViewInteraction appCompatTextView = onView(
                allOf(withText("DeviceUtils"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withText("AppUtils"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.uninstall_specify_app_btn), withText("卸载指定app"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.detail_specify_app_btn), withText("app应用信息界面"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.camera_capture_btn), withText("拍照"), isDisplayed()));
        appCompatButton6.perform(click());

        pressBack();

    }

}
