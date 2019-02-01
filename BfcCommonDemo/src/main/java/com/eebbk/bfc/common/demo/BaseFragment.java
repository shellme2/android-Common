package com.eebbk.bfc.common.demo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eebbk.bfc.common.demo.app.App;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;

/**
 * Created by Simon on 2016/9/30.
 */
public abstract class BaseFragment extends Fragment {
    protected View mFragmentRootView;

    abstract @LayoutRes
    int getLayoutRes();

    public void init() {
    }

    public String getTitle() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mFragmentRootView = view;
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    <T extends View> T $(View v, @IdRes int id) {
        return (T) v.findViewById(id);
    }

    <T extends View> T $(@IdRes int id) {
        return (T) mFragmentRootView.findViewById(id);
    }

    <T extends View> T findView(@IdRes int id) {
        return (T) mFragmentRootView.findViewById(id);
    }
}
