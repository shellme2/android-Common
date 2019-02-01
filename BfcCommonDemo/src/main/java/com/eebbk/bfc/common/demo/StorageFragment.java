package com.eebbk.bfc.common.demo;

import android.widget.TextView;

import com.eebbk.bfc.common.file.StorageUtils;

/**
 * Created by Simon on 2016/9/30.
 */
public class StorageFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return  R.layout.fragment_storage ;
    }

    @Override
    public String getTitle() {
        return "存储相关";
    }

    @Override
    public void init() {
        super.init();
        ((TextView)$(R.id.exist_sdcard_tv)).setText(StorageUtils.isExternalStorageRemoveable() + "");
        ((TextView)$(R.id.available_external_storage_tv)).setText(StorageUtils.isExternalStroageAvailable() + "");
        ((TextView)$(R.id.external_storage_path_tv)).setText(StorageUtils.getExternalStorageFile().getAbsolutePath() + "");
        ((TextView)$(R.id.external_storage_size_tv)).setText(StorageUtils.getExternalStorageTotalSize()/1024/1024/1024 + "GB");
        ((TextView)$(R.id.external_storage_free_size_tv)).setText(StorageUtils.getExternalStorageAvailableSize()/1024/1024/1024 + "GB");
        ((TextView)$(R.id.internal_storage_path_tv)).setText(StorageUtils.getDataFile().getAbsolutePath() + "");
        ((TextView)$(R.id.internal_storage_free_size_tv)).setText(StorageUtils.getDataAvailableSize()/1024/1024/1024 + "GB");

        ((TextView)$(R.id.sdcard_path_tv)).setText( StorageUtils.getSdCardPath() +"");

    }
}
