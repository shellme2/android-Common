package com.eebbk.bfc.common.demo;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.bfc.common.devices.MediaLibraryUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.common.devices.IntentUtils;
import com.eebbk.bfc.common.inner.LogInner;
import com.eebbk.bfc.common.tools.StringUtils;
import com.eebbk.bfc.common.app.ToastUtils;

import java.io.File;

import butterknife.OnClick;


public class IntentFragment extends BaseFragment {
    private static final int RequestCode_pick_pic = 300;
    private static final String TAG = "IntentFragment";

    @Override
    public String getTitle() {
        return "IntentUtils";
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_intent;
    }

    @Override
    public void init() {
        super.init();
    }

    @OnClick({R.id.go_home_btn, R.id.go_setting_btn, R.id.call_btn,R.id.go_call_btn, R.id.send_msg_btn, R.id.camera_capture_btn,
    R.id.share_text_btn, R.id.share_pic_btn, R.id.pick_file_btn, R.id.pick_pic_btn, R.id.install_file_btn, R.id.btn_scan_media, R.id.btn_delete_media})
    public void onClick(View v) {
        String phoneNum = ((EditText) $(R.id.phone_num_et)).getText().toString();
        String msg = ((EditText) $(R.id.msg_et)).getText().toString();

        try {
            switch (v.getId()) {
                case R.id.go_home_btn:
                    startActivity(IntentUtils.getGoHomeIntent());
                    break;
                case R.id.go_setting_btn:
                    startActivity(IntentUtils.getGoSettingsIntent());
                    break;
                case R.id.call_btn:
                    Intent intent = IntentUtils.getCallPhoneIntent(phoneNum);
                    if (IntentUtils.isActivityAvailable(getContext(), intent)){
                        startActivity( intent );
                    }else {
                        ToastUtils.getInstance(getContext()).s("无法处理该intent");
                    }
                    break;
                case R.id.go_call_btn:
                    startActivity( IntentUtils.getDialIntent(phoneNum) );
                    break;
                case R.id.send_msg_btn:
                    startActivity( IntentUtils.getSendSmsIntent(phoneNum, msg) );
                    break;
                case R.id.camera_capture_btn:
                    startActivity( IntentUtils.getCaptureIntent(null) );
                    break;
                case R.id.pick_pic_btn:
                    startActivityForResult( IntentUtils.getPickIntentWithGallery() , RequestCode_pick_pic);
                    break;
                case R.id.share_text_btn:
                    startActivity( IntentUtils.getShareTextIntent("分享的文字, 测试代码,我写死了 - -") );
                    break;
                case R.id.share_pic_btn:
                    if (TextUtils.isEmpty(mFilePath)){
                        ToastUtils.getInstance(getContext()).s("先去选择一张图片");
                        return;
                    }
                    startActivity( IntentUtils.getShareImageIntent("分享图片", mFilePath) );
                    break;
                case R.id.pick_file_btn:
    //                getActivity().startActivityForResult( IntentUtils.getPickFileIntent("image/*") , 123);
                    startActivityForResult( IntentUtils.getPickFileIntent(IntentUtils.MINE_TYPE_ALL) , 123);
                    break;
                case R.id.install_file_btn:
                    if (StringUtils.isEmpty(mFilePath)){
                        ToastUtils.getInstance(getContext()).s("先去选择一个apk文件");
                        return;
                    }
                    File apkFile = new File(mFilePath);
                    startActivity( IntentUtils.getInstallAppIntent( apkFile ) );
                    break;
                case R.id.btn_scan_media:
                    MediaLibraryUtils.scanFile(getContext(), mFilePath);
                    break;
                case R.id.btn_delete_media:
                    MediaLibraryUtils.deleteFromMediaLibrary(getContext(), mFilePath);
                    break;
            }
        } catch (Exception e) {
            LogInner.e(TAG, e, "");
            ToastUtils.getInstance(getContext()).s("捕获异常, 可能是没有对应的apk进行处理");
        }

    }

    private Uri mPicUri;
    private String mFilePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        Uri retUri =  data.getData();
        if (retUri != null){
            ((TextView)$(R.id.result_uri_tv)).setText( retUri.toString() );
            mFilePath = FileUtils.getUriReallyPath(getContext(), retUri);
            ((TextView)$(R.id.result_path_tv)).setText( mFilePath + "");
        }
        switch (requestCode){
            case RequestCode_pick_pic:
                mPicUri = retUri;
                break;
        }

    }
}
