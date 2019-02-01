package com.eebbk.bfc.common.demo;

import android.provider.Settings;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eebbk.bfc.common.devices.DisplayUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxSeekBar;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by Simon on 2016/9/30.
 */
public class DisplayFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return R.layout.fragment_display;
    }

    @Override
    public String getTitle() {
        return "屏幕显示相关";
    }

    @Override
    public void init() {
        super.init();
        ((TextView) $(R.id.screen_lock_tv)).setText(DisplayUtils.isScreenLocked(getContext()) + "");
        ((TextView) $(R.id.screen_width_tv)).setText(DisplayUtils.getScreenWidth(getContext()) + "");
        ((TextView) $(R.id.screen_height_tv)).setText(DisplayUtils.getScreenHeight(getContext()) + "");
        ((TextView) $(R.id.screen_status_bar_height_tv)).setText(DisplayUtils.getStatusBarHeight(getContext()) + "");
        ((TextView) $(R.id.screen_navigation_bar_tv)).setText(DisplayUtils.isExistNavigationBar(getContext()) + "");
        ((TextView) $(R.id.screen_navigation_bar_height_tv)).setText(DisplayUtils.getNavigationBarHeight(getContext()) + "");
        ((TextView) $(R.id.screen_dpi_tv)).setText(DisplayUtils.getDpi(getContext()) + "");
        ((TextView) $(R.id.screen_density_tv)).setText(DisplayUtils.getDensity(getContext()) + "");

        RxTextView.textChanges($(R.id.dp2px_et))
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(charSequence -> Observable.just(charSequence)
                        .map(CharSequence::toString)
                        .map(Integer::parseInt)
                        .onErrorReturn(throwable -> 0))
                .map(dp -> DisplayUtils.dp2px(getContext(), dp))
                .map(String::valueOf)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((TextView) $(R.id.dp2px_tv)).setText(s));

        RxTextView.textChanges($(R.id.px2dp_et))
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(this::String2Int)
                .map(px -> DisplayUtils.px2dp(getContext(), px))
                .map(String::valueOf)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((TextView) $(R.id.px2dp_tv)).setText(s));

        RxTextView.textChanges($(R.id.sp2px_et))
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(this::String2Int)
                .map(sp -> DisplayUtils.sp2px(getContext(), sp))
                .map(String::valueOf)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((TextView) $(R.id.sp2px_tv)).setText(s));

        RxTextView.textChanges($(R.id.px2sp_et))
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(this::String2Int)
                .map(sp -> DisplayUtils.px2sp(getContext(), sp))
                .map(String::valueOf)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((TextView) $(R.id.px2sp_tv)).setText(s));

        RxView.clicks($(R.id.capture_screen_btn))
                .map(aVoid -> DisplayUtils.captureScreen(getActivity()))
                .subscribe(bitmap -> ((ImageView) $(R.id.capture_screen_iv)).setImageBitmap(bitmap));

        SeekBar brightnessSeekBar = $(R.id.screen_brightness_seekbar);
        brightnessSeekBar.setProgress( DisplayUtils.getScreenBrightness(getActivity()) );

        RxSeekBar.changes($(R.id.screen_brightness_seekbar))
                .subscribe(integer -> {
                    DisplayUtils.setScreenBrightness(getActivity(), Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC, integer);
                });

    }

    private int String2Int(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }
}
