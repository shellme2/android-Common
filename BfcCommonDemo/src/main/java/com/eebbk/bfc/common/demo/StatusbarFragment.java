package com.eebbk.bfc.common.demo;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eebbk.bfc.common.app.StatusBarUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxSeekBar;

import java.util.Random;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Simon on 2016/9/30.
 */
public class StatusbarFragment extends BaseFragment {

    int mColor;
    int mAlpha = 0;

    @Override
    int getLayoutRes() {
        return R.layout.fragment_statusbar;
    }

    @Override
    public String getTitle() {
        return "状态栏设置";
    }

    @Override
    public void init() {
        super.init();

        mColor = getResources().getColor(R.color.colorPrimary);

        CheckBox darkModeCompoundButton = $(R.id.ck_dark_statusbar_text_color);
        darkModeCompoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StatusBarUtils.enableDarkMode(getActivity(), isChecked);

            }
        });


        RxView.clicks($(R.id.btn_set_color)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Random random = new Random();
                mColor = 0xff000000 | random.nextInt(0xffffff);
                StatusBarUtils.setColor(getActivity(), mColor, mAlpha);
            }
        });

        SeekBar seekBar = $(R.id.sb_change_alpha);
        seekBar.setMax(255);

        RxSeekBar.changes(seekBar)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mAlpha = integer;
                        StatusBarUtils.setColor(getActivity(), mColor, mAlpha);
                        ((TextView)$(R.id.tv_statusbar_alpha)).setText( integer + "" );
                    }
                });

        RxView.clicks($(R.id.btn_set_transparent)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                StatusbarTransparentActivity.startThis(getActivity(), true);
            }
        });

        RxView.clicks($(R.id.btn_set_translucent)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                StatusbarTransparentActivity.startThis(getActivity(), false);
            }
        });
    }
}
