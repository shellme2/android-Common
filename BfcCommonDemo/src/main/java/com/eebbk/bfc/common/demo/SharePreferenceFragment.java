package com.eebbk.bfc.common.demo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.bfc.common.app.SharedPreferenceUtils;
import com.eebbk.bfc.common.app.SystemStoreUtils;
import com.eebbk.bfc.common.app.ToastUtils;

/**
 * Created by Simon on 2016/10/17.
 */

public class SharePreferenceFragment extends BaseFragment {

    EditText mKeyEditText;
    EditText mValueEditText;

    TextView mSpValueTextView;
    TextView mSettingValueTextView;

    @Override
    int getLayoutRes() {
        return R.layout.fragment_shared_preference;
    }

    @Override
    public String getTitle() {
        return "sp/system保存";
    }

    @Override
    public void init() {
        super.init();
        mKeyEditText = $(R.id.et_sp_key);
        mValueEditText = $(R.id.et_sp_value);

        mSpValueTextView = $(R.id.tv_sp_value);
        mSettingValueTextView = $(R.id.tv_setting_value);

        $(R.id.btn_sp_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mKeyEditText.getText().toString();
                String value = mValueEditText.getText().toString();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    ToastUtils.getInstance(getContext()).s("key or value is null/empty");
                    return;
                }

                SharedPreferenceUtils.getInstance(getContext()).put(key, value);
            }
        });

        $(R.id.btn_setting_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mKeyEditText.getText().toString();
                String value = mValueEditText.getText().toString();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    ToastUtils.getInstance(getContext()).s("key or value is null/empty");
                    return;
                }

                try {
                    SystemStoreUtils.put(getContext(), key, value);
                } catch (Exception e) {
                   ToastUtils.getInstance(getContext()).s("保存异常; 非系统app由于权限问题,无法保存");
                }
            }
        });


        $(R.id.btn_sp_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mKeyEditText.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    ToastUtils.getInstance(getContext()).s("key is null/empty");
                    return;
                }

                SharedPreferenceUtils.getInstance(getContext()).remove(key);
            }
        });

        $(R.id.btn_setting_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mKeyEditText.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    ToastUtils.getInstance(getContext()).s("key is null/empty");
                    return;
                }

                try {
                    SystemStoreUtils.remove(getContext(), key);
                } catch (Exception e) {
                    ToastUtils.getInstance(getContext()).s("修改异常; 非系统app由于权限问题,无法操作");
                }
            }
        });

        $(R.id.btn_get_value).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mKeyEditText.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    ToastUtils.getInstance(getContext()).s("key is null/empty");
                    return;
                }

                mSpValueTextView.setText(SharedPreferenceUtils.getInstance(getContext()).get(key, ""));

                mSettingValueTextView.setText(SystemStoreUtils.get(getContext(), key));
            }
        });
    }
}
