package com.eebbk.bfc.common.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eebbk.bfc.common.app.BitmapUtils;
import com.eebbk.bfc.common.app.ToastUtils;
import com.eebbk.bfc.common.devices.IntentUtils;
import com.eebbk.bfc.common.file.EncryptUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Simon on 2016/9/30.
 */
public class BitmapFragment extends BaseFragment {
    static final int REQUEST_CODE_PIC = 100;

    ImageView mDisplayImageView;
    Bitmap mDisplayBitmap;

    @Override
    int getLayoutRes() {
        return R.layout.fragment_bitmap;
    }

    @Override
    public String getTitle() {
        return "Bitmap相关";
    }

    @Override
    public void init() {
        super.init();
        mDisplayImageView = $(R.id.image_display_iv);


        RxView.clicks($(R.id.get_pic_btn))
                .map(aVoid -> IntentUtils.getPickIntentWithGallery())
                .doOnNext(intent -> startActivityForResult(intent, REQUEST_CODE_PIC))
                .doOnError(throwable -> ToastUtils.getInstance(getContext()).s("没有相应的app去现在图片 请先安装软件"))
                .retry()
                .subscribe();

        RxView.clicks($(R.id.round_corner_btn))
                .filter(aVoid -> mDisplayImageView != null)
                .flatMap(aVoid -> Observable.just(BitmapUtils.toRoundCorner(mDisplayBitmap, 20, true)))
                .doOnNext(bitmap -> mDisplayBitmap = bitmap)
                .subscribe(mDisplayImageView::setImageBitmap);

        RxView.clicks($(R.id.grey_btn)).subscribe(aVoid -> {
            if (mDisplayBitmap == null) return;
            mDisplayBitmap = BitmapUtils.toGray(mDisplayBitmap, false);
            mDisplayImageView.setImageBitmap(mDisplayBitmap);
        });

        RxView.clicks($(R.id.rotate_btn)).subscribe(aVoid -> {
            if (mDisplayBitmap == null) return;
            mDisplayBitmap = BitmapUtils.rotate(mDisplayBitmap, 90, mDisplayBitmap.getWidth() / 2, mDisplayBitmap.getHeight() / 2, true);
            mDisplayImageView.setImageBitmap(mDisplayBitmap);
        });

        RxView.clicks($(R.id.blur_btn))
                .filter(aVoid -> mDisplayBitmap != null)
                .flatMap(aVoid -> Observable.just(BitmapUtils.toBlur(getContext(), mDisplayBitmap, 10)))
                .doOnNext(bitmap -> mDisplayBitmap = bitmap)
                .subscribe(mDisplayImageView::setImageBitmap);


        RxView.clicks($(R.id.add_reflection_btn))
                .debounce(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    if (mDisplayBitmap == null) return;
                    mDisplayBitmap = BitmapUtils.addReflection(mDisplayBitmap, mDisplayBitmap.getHeight() / 3, true);
                    mDisplayImageView.setImageBitmap(mDisplayBitmap);
                });
    }


    @OnClick({R.id.img_round_btn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_round_btn:
                if (mDisplayBitmap == null) return;
                mDisplayBitmap = BitmapUtils.toRound(mDisplayBitmap, true);
                mDisplayImageView.setImageBitmap(mDisplayBitmap);
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CODE_PIC) {
            Uri picUri = data.getData();
            String path = FileUtils.getUriReallyPath(getContext(), picUri);

            mDisplayBitmap = BitmapUtils.getBitmap(path, mDisplayImageView.getWidth(), mDisplayImageView.getHeight());

            ((TextView) $(R.id.pic_realy_path_tv)).setText(path);
            try {
                ((TextView) $(R.id.md5_tv)).setText(EncryptUtils.md5Hex(new File(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            mDisplayImageView.setImageBitmap(mDisplayBitmap);
        }
    }
}
