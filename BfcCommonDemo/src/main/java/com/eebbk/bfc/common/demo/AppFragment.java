package com.eebbk.bfc.common.demo;

import android.text.TextUtils;
import android.widget.TextView;

import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.devices.ShellUtils;
import com.eebbk.bfc.common.inner.LogInner;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Simon on 2016/9/30.
 */
public class AppFragment extends BaseFragment {

    @Override
    int getLayoutRes() {
        return R.layout.fragment_app;
    }

    @Override
    public String getTitle() {
        return "App相关信息";
    }

    @Override
    public void init() {
        super.init();
        ((TextView) $(R.id.app_package_name_tv)).setText(AppUtils.getPackageName(getContext()));
        ((TextView) $(R.id.app_name_tv)).setText(AppUtils.getAppName(getContext()));
        ((TextView) $(R.id.app_version_name_tv)).setText(AppUtils.getVersionName(getContext()));
        ((TextView) $(R.id.app_version_code_tv)).setText(AppUtils.getVersionCode(getContext()) + "");


        ((TextView) $(R.id.app_signature_tv)).setText(AppUtils.getSignatureMd5(getContext()));

        RxTextView.textChanges($(R.id.app_package_name_et))
                .debounce(1, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(packageName -> {
                    ((TextView) $(R.id.app_specify_name_tv)).setText(AppUtils.getAppName(getContext(), packageName));
                    ((TextView) $(R.id.app_specify_version_name_tv)).setText(AppUtils.getVersionName(getContext(), packageName));
                    ((TextView) $(R.id.app_specify_version_code_tv)).setText(AppUtils.getVersionCode(getContext(), packageName) + "");
                    ((TextView) $(R.id.app_specify_signature_tv)).setText(AppUtils.getSignatureMd5(getContext(), packageName));
                    ((TextView) $(R.id.app_specify_exist_tv)).setText(AppUtils.isAppInstalled(getContext(), packageName) + "");
                    ((TextView) $(R.id.app_specify_is_system_tv)).setText(AppUtils.isSystemApp(getContext(), packageName) + "");
                });


        RxView.clicks($(R.id.uninstall_specify_app_btn))
                .map(aVoid -> ((TextView) $(R.id.app_package_name_et)).getText().toString())
                .subscribe(packageName -> AppUtils.uninstallApp(getContext(), packageName));


        RxView.clicks($(R.id.uninstall_specify_app_silence_btn)).subscribe(aVoid -> {
            final String packageName = ((TextView) $(R.id.app_package_name_et)).getText().toString();
//            boolean isUninstallSuccess = AppUtils.uninstallAppSilent(getContext(), packageName, false);
//            ToastUtils.getInstance(getContext()).s("静默卸载成功?: " + isUninstallSuccess);
        });

        RxView.clicks($(R.id.detail_specify_app_btn))
                .map(aVoid -> ((TextView) $(R.id.app_package_name_et)).getText().toString())
                .filter(s -> !TextUtils.isEmpty(s))
                .subscribe(packageName -> AppUtils.openAppInfoSettings(getContext(), packageName));

        RxView.clicks($(R.id.start_app_btn))
                .map(aVoid -> ((TextView) $(R.id.app_package_name_et)).getText().toString())
                .subscribe(packageName -> AppUtils.startApp(getContext(), packageName));

        RxView.clicks($(R.id.hide_app_btn))
                .map(aVoid -> ((TextView) $(R.id.app_package_name_et)).getText().toString())
//                .subscribe(s -> AppUtils.hideAppIcon(getContext(), s))
        ;

        RxView.clicks($(R.id.run_monkey_btn))
                .map(aVoid -> ((TextView) $(R.id.app_package_name_et)).getText().toString())
                .subscribe(s -> {
                    boolean isRoot = ShellUtils.isRoot();
                    ShellUtils.CommandResult result = ShellUtils.execCmd("adb reboot", isRoot, true);
                    LogInner.d(result.toString());
                });

    }
}
