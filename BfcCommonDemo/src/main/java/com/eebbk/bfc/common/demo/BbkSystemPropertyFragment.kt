package com.eebbk.bfc.common.demo

import android.widget.TextView
import com.eebbk.bfc.common.compat.BBKSystemPropertyUtils

/**
 * Created by Simon on 2017/7/11.
 */
class BbkSystemPropertyFragment : BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_bbk_system_property;
    }

    override fun getTitle(): String {
        return "系统相关属性"
    }

    override fun init() {
        super.init()

        findView<TextView>(R.id.tv_is_junior).setText(" ${BBKSystemPropertyUtils.getJuniorMarkValue(context)}")
    }
}