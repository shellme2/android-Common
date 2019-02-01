package com.eebbk.bfc.common.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2016/9/30.
 */
public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
        mData = new ArrayList<>();
    }

    final List<BaseFragment> mData;

    public void add(BaseFragment fragment){
        mData.add(fragment);
    }


    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData == null? 0 : mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getTitle();
    }
}
