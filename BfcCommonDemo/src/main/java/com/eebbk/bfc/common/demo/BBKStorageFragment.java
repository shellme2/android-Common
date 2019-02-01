package com.eebbk.bfc.common.demo;

import android.util.Log;
import android.widget.TextView;

import com.eebbk.bfc.common.file.BBKStorageUtils;

import java.io.File;

/**
 * Created by Simon on 2016/9/30.
 */
public class BBKStorageFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return R.layout.fragment_bbk_storage;
    }

    @Override
    public String getTitle() {
        return "BBK存储相关";
    }

    @Override
    public void init() {
        super.init();
        File flashA = BBKStorageUtils.getFlashA();
        ((TextView) $(R.id.flash_a_tv)).setText(flashA == null ? null : flashA.getAbsolutePath());
        ((TextView) $(R.id.flash_a_state_tv)).setText(BBKStorageUtils.getFlashAStorageState());
        ((TextView) $(R.id.external_storage_tv)).setText(BBKStorageUtils.getExternalStorage().getAbsolutePath());
        ((TextView) $(R.id.external_storage_state_tv)).setText(BBKStorageUtils.getExternalStorageState());

        ((TextView) $(R.id.is_support_sdcard_tv)).setText(BBKStorageUtils.isSupportSDCard() + "");
        ((TextView) $(R.id.sd_card_tv)).setText(BBKStorageUtils.getSDCard().getAbsolutePath());
        ((TextView) $(R.id.sd_card_state_tv)).setText(BBKStorageUtils.getSDCardStorageState());

        $(R.id.create_file_btn).setOnClickListener(v -> {
            File file = new File(BBKStorageUtils.getExternalStorage(), "test");
            try {
                file.createNewFile();
            } catch (Exception e) {
                Log.e("zy", "", e);
            }
        });
    }
}
