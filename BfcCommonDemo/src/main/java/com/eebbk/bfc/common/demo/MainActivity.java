package com.eebbk.bfc.common.demo;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.eebbk.bfc.common.app.KeyboardUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
         {


    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.PHONE,Manifest.permission.READ_PHONE_STATE}, 100);
        }

        MainActivityPagerAdapter pagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        pagerAdapter.add(new BBKAlertDialogFragment());
        pagerAdapter.add(new StatusbarFragment());
        pagerAdapter.add(new DevicesFragment());
        pagerAdapter.add(new AppFragment());
        pagerAdapter.add(new StringFragment());
        pagerAdapter.add(new NetFragment());
        pagerAdapter.add(new DisplayFragment());
        pagerAdapter.add(new BBKStorageFragment());
//        pagerAdapter.add(new StorageFragment());
        pagerAdapter.add(new BitmapFragment());
        pagerAdapter.add(new ToastFragment());
        pagerAdapter.add(new IntentFragment());
        pagerAdapter.add(new SharePreferenceFragment());
        pagerAdapter.add(new DateFragment());
        pagerAdapter.add(new BBKWindowFragment());
        pagerAdapter.add(new BbkSystemPropertyFragment());
        pagerAdapter.add(new BbkMediaPlayerFragment());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     }

             @Override
    public void onBackPressed() {
        if (KeyboardUtils.isSoftkeyboardShow(this)) {
            KeyboardUtils.hideSoftKeyborad(this);
            return;
        }
        finish();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
