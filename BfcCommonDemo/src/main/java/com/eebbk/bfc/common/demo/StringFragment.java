package com.eebbk.bfc.common.demo;

import android.widget.TextView;

import com.eebbk.bfc.common.file.EncryptUtils;
import com.eebbk.bfc.common.tools.StringUtils;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Simon on 2016/9/30.
 * 保存数据到系统的settings中; 只有系统的app才能使用
 */
public class StringFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return R.layout.fragment_string;
    }

    @Override
    public String getTitle() {
        return "字符串相关";
    }

    @Override
    public void init() {
        super.init();

        RxTextView.textChanges((TextView) $(R.id.string_input_et))
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        String str = charSequence.toString();

                        ((TextView) $(R.id.string_is_empty_tv)).setText(StringUtils.isEmpty(str) + "");
//                        ((TextView) $(R.id.string_is_json_tv)).setText(StringUtils.isJson(str) + "");
                        ((TextView) $(R.id.string_is_phone_num_tv)).setText(StringUtils.isMobileNum(str) + "");
                        ((TextView) $(R.id.string_is_email_tv)).setText(StringUtils.isEmail(str) + "");
                        ((TextView) $(R.id.string_is_ip_tv)).setText(StringUtils.isIp(str) + "");
                        ((TextView) $(R.id.string_is_idcard_tv)).setText(StringUtils.isIdCard(str) + "");

                        ((TextView) $(R.id.string_url_encode_tv)).setText(StringUtils.encodeUrl(str) + "");
                        ((TextView) $(R.id.string_url_decode_tv)).setText(StringUtils.decodeUrl(str) + "");


                        ((TextView) $(R.id.md5_tv)).setText(EncryptUtils.md5Hex(str) + "");

                    }
                });
    }
}
