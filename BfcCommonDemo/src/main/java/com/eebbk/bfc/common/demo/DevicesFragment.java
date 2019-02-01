package com.eebbk.bfc.common.demo;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.bfc.common.devices.DeviceUtils;
import com.jakewharton.rxbinding.view.RxView;

public class DevicesFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return R.layout.fragment_devices;
    }


    @Override
    public void init() {
        super.init();
        TextView sdKTextView = $(R.id.sdk_tv);
        sdKTextView.setText(DeviceUtils.getSDK() + "");

        TextView imeiTextView = $(R.id.imei_tv);
        imeiTextView.setText(DeviceUtils.getIMEI(getContext()));

        TextView macTextView = $(R.id.mac_tv);
        macTextView.setText(DeviceUtils.getMac(getContext()));

        TextView manufacturerTextView = $(R.id.manufacturer_tv);
        manufacturerTextView.setText(DeviceUtils.getManufacturer());

        TextView modelTextView = $(R.id.model_tv);
        modelTextView.setText(DeviceUtils.getModel());

        ((TextView) $(R.id.is_phone_tv)).setText(DeviceUtils.isPhone(getContext()) + "");

        ((TextView) $(R.id.total_memory_tv)).setText(DeviceUtils.getMemoryTotalSize(getContext()) / 1024 / 1024 + "MB");
        ((TextView) $(R.id.available_memory_tv)).setText(DeviceUtils.getMemoryAvailableSize(getContext()) / 1024 / 1024 + "MB");

        TextView batteryLevelTextView = $(R.id.battery_level_tv);
        batteryLevelTextView.setText(DeviceUtils.getBatteryLevel(getContext()) + "");

        ((TextView) $(R.id.is_charge_tv)).setText(DeviceUtils.isCharge(getContext()) + "");

        ((TextView) $(R.id.tv_cpu_core)).setText(DeviceUtils.getCpuCoreSize() + "");
        ((TextView) $(R.id.tv_machine_id)).setText(DeviceUtils.getMachineId(getContext()) + "");
        ((TextView) $(R.id.tv_system_version)).setText(DeviceUtils.getSystemVersion() + "");

        RxView.clicks($(R.id.btn_open_setting))
                .subscribe(aVoid -> DeviceUtils.openSetting(getContext()));

        RxView.clicks($(R.id.vibrate_btn))
                .map(aVoid -> ((EditText) $(R.id.vibrate_et)).getText().toString())
                .filter(s -> !TextUtils.isEmpty(s))
                .map(Long::parseLong)
                .subscribe(aLong -> DeviceUtils.vibrate(getContext(), aLong),
                        Throwable::printStackTrace);

    }

    @Override
    public String getTitle() {
        return "设备信息";
    }

}
