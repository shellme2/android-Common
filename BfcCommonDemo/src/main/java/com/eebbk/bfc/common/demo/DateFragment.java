package com.eebbk.bfc.common.demo;

import android.text.TextUtils;
import android.widget.TextView;

import com.eebbk.bfc.common.app.ToastUtils;
import com.eebbk.bfc.common.tools.DateUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class DateFragment extends BaseFragment {
    @Override
    public String getTitle() {
        return "日期相关";
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_date;
    }

    @Override
    public void init() {
        super.init();

        RxTextView.textChanges((TextView) $(R.id.et_year)).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                String yearStr = charSequence.toString();

                int year = TextUtils.isEmpty(yearStr) ? 0 : Integer.parseInt(yearStr);

                ((TextView) $(R.id.tv_is_yeap_year)).setText(DateUtils.isLeapYear(year) + "");
            }
        });


        RxTextView.textChanges((TextView) $(R.id.et_date_format)).debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        String formateStr = charSequence.toString();

                        try {
                            ((TextView) $(R.id.tv_date_formate)).setText(DateUtils.getCurTimeString(formateStr) + "");
                        } catch (Exception e) {
                            ToastUtils.getInstance(getContext()).s("格式化报错, 请检查格式化时间的格式");
                        }
                    }
                });

        ArrayList<String> dateFormateList = new ArrayList();
        dateFormateList.add("HH:mm");
        dateFormateList.add("yyyy-MM-dd");
        dateFormateList.add("yyyy-MM-dd HH:mm");
        dateFormateList.add("yyyy-MM-dd HH:mm:ss");
        dateFormateList.add("yyyy.MM.dd G 'at' HH:mm:ss z ");
        dateFormateList.add("EEE, MMM d, ''yy");
        dateFormateList.add("K:mm a");
        dateFormateList.add("hh 'o''clock' a");
        dateFormateList.add("yyMMddHHmmssZ");

        MaterialSpinner spinner = $(R.id.sp_date_format);
        spinner.setItems(dateFormateList);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                ((TextView) $(R.id.tv_date_formate)).setText(DateUtils.getCurTimeString(item));
            }
        });

    }
}
