package com.eebbk.bfc.common.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.bfc.common.app.ToastUtils;
import com.eebbk.bfc.common.devices.NetUtils;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Simon on 2016/9/30.
 */
public class NetFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return R.layout.fragment_net;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SwipeRefreshLayout swipeRefreshLayout = $(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public String getTitle() {
        return "网络相关";
    }

    @Override
    public void init() {
        super.init();
        ((TextView) $(R.id.ip_address_tv)).setText(NetUtils.getIpAddress());
        ((TextView) $(R.id.net_connected_tv)).setText(NetUtils.isConnected(getContext()) + "");
        ((TextView) $(R.id.wifi_connected_tv)).setText(NetUtils.isWifiConnected(getContext()) + "");
        ((TextView) $(R.id.mobile_net_connected_tv)).setText(NetUtils.isMobileDataConnected(getContext()) + "");
        ((TextView) $(R.id.wifi_open_tv)).setText(NetUtils.isWifiEnable(getContext()) + "");

        RxView.clicks($(R.id.go_wifi_setting)).subscribe(aVoid -> {
            NetUtils.openWifiSetting(getContext());
        });

        RxView.clicks($(R.id.go_mobile_data_setting)).subscribe(aVoid -> {
            NetUtils.openMobileDataSettings(getContext());
        }, throwable -> ToastUtils.getInstance(getContext()).s("无法进入移动网络设置界面"));

        ((CompoundButton) $(R.id.open_wifi_sw)).setChecked(NetUtils.isWifiEnable(getContext()));

        ((CompoundButton) $(R.id.open_wifi_sw)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NetUtils.enableWifi(getContext(), isChecked);
            }
        });

        TextView netTypeTv = $(R.id.tv_network_type);

        switch (NetUtils.getNetWorkType(getContext())) {
            case NetUtils.NETWORK_2G:
                netTypeTv.setText(" 2G");
                break;
            case NetUtils.NETWORK_3G:
                netTypeTv.setText(" 3G");
                break;
            case NetUtils.NETWORK_4G:
                netTypeTv.setText(" 4G");
                break;
            case NetUtils.NETWORK_WIFI:
                netTypeTv.setText(" wifi");
                break;
            case NetUtils.NETWORK_BLUETOOTH:
                netTypeTv.setText(" 蓝牙");
                break;
            case NetUtils.NETWORK_UNKNOWN:
                netTypeTv.setText(" 未知");
                break;
            case NetUtils.NETWORK_NO:
                netTypeTv.setText(" 无连接");
                break;
        }

        RxView.clicks($(R.id.ping_btn))
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String ip = ((EditText) $(R.id.ping_ip_et)).getText().toString();
                        boolean isPingSuccess = NetUtils.ping(ip, 3, 5);
                        ToastUtils.getInstance(getContext()).s("ping: " + isPingSuccess);
                    }
                });
    }
}
