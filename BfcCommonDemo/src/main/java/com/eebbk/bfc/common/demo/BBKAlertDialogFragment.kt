package com.eebbk.bfc.common.demo

import android.net.Uri
import android.widget.Button
import com.eebbk.bfc.common.BBKAlertDialog
import com.eebbk.bfc.common.app.ToastUtils

/**
 * 作者： jiazy
 * 日期： 2017/8/15.
 * 公司： 步步高教育电子有限公司
 * 描述： 测试BBKAlertDialog
 */

class BBKAlertDialogFragment : BaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_bbk_alertdialog
    }

    override fun getTitle(): String {
        return "BBKAlertDialog"
    }

    override fun init() {
        super.init()

        findView<Button>(R.id.setUninstall).setOnClickListener({
            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKDialogStyle(BBKAlertDialog.DialogStyleType.UNINSTALL)
                    .setMessage("要卸载吗？")
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })

        findView<Button>(R.id.setDeleteSingle).setOnClickListener({
            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKDialogStyle(BBKAlertDialog.DialogStyleType.DELETE_SINGLE)
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })

        findView<Button>(R.id.setDeleteMultiple).setOnClickListener({
            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKDialogStyle(BBKAlertDialog.DialogStyleType.DELETE_MULTIPLE)
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })

        findView<Button>(R.id.setNoNetwork).setOnClickListener({
            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKDialogStyle(BBKAlertDialog.DialogStyleType.NO_NETWORK)
                    .setPositiveButton("设置网络") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })

        findView<Button>(R.id.setMessageIcon).setOnClickListener({

            BBKAlertDialog.Builder(activity)
                    .setBBKMessageIcon(R.mipmap.ic_launcher)
                    .setMessage("测试setMessageIcon")
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
                    .show()
        })

        findView<Button>(R.id.setSound).setOnClickListener({

            val path = "android.resource://" + context.packageName + "/" + R.raw.tct_000_005//raw目录ok，assets目录无法播放.
            val uri = Uri.parse(path)

            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKSound(uri)
                    .setMessage("测试setSound")
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })

        findView<Button>(R.id.setSound2).setOnClickListener({
            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKSound(R.raw.tct_000_005)
                    .setMessage("测试setSound")
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })

        findView<Button>(R.id.setNoSpace).setOnClickListener({
            val builder = BBKAlertDialog.Builder(activity)
            builder.setBBKDialogStyle(BBKAlertDialog.DialogStyleType.NO_SPACE)
                    .setPositiveButton("确定") {dialog, which -> ToastUtils.getInstance(context).s("确定") }
                    .setNegativeButton("取消") {dialog, which -> ToastUtils.getInstance(context).s("取消")}
                    .create()
            builder.show()
        })
    }
}