package com.eebbk.bfc.common.demo;



import android.widget.EditText;

import com.eebbk.bfc.common.app.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;


public class ToastFragment extends BaseFragment {
    @Override
    public String getTitle() {
        return "Toast相关";
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_toast;
    }

    @Override
    public void init() {
        super.init();

        RxView.clicks( $(R.id.short_toast_btn) ).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String str = ((EditText)$(R.id.et_toast_input)).getText().toString();

                ToastUtils.getInstance(getContext()).s(str);
            }
        });

        RxView.clicks( $(R.id.long_toast_btn) ).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String str = ((EditText)$(R.id.et_toast_input)).getText().toString();
                ToastUtils.getInstance(getContext()).l(str);
            }
        });

        RxView.clicks( $(R.id.cacle_btn) ).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ToastUtils.getInstance(getContext()).cancel();
            }
        });

    }
}
