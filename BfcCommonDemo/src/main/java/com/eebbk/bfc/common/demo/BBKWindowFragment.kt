package com.eebbk.bfc.common.demo

import android.view.View
import com.eebbk.bfc.common.compat.BBKWindowUtils

/**
 * Created by Simon on 2017/7/11.
 */

class BBKWindowFragment : BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_bbk_window
    }

    override fun getTitle(): String {
        return "BbkWindow相关"
    }

    override fun init() {
        super.init()

        findView<View>(R.id.btn_disable_home_key).setOnClickListener({
            BBKWindowUtils.disableHomeKey(activity)
        })

        findView<View>(R.id.btn_enable_home_key).setOnClickListener({
            BBKWindowUtils.enableHomeKey(activity)
        })

        findView<View>(R.id.btn_disable_app_switch_key).setOnClickListener({
            BBKWindowUtils.disableAppSwitchKey(activity)
        })

        findView<View>(R.id.btn_enable_app_switch_key).setOnClickListener({
            BBKWindowUtils.enableAppSwitchKey(activity)
        })

        findView<View>(R.id.btn_disable_pull_statusbar).setOnClickListener({
            BBKWindowUtils.disablePullStatusBar(activity)
        })

        findView<View>(R.id.btn_enable_pull_statusbar).setOnClickListener({
            BBKWindowUtils.enablePullStatusBar(activity)
        })

    }

}
