package com.example.truckercore.view.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.truckercore.R
import com.example.truckercore.model.configs.constants.Tag

class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setBackgroundTransparentAndHideNavigationBar()
        setContentView(R.layout.dialog_loading)
    }

    private fun setBackgroundTransparentAndHideNavigationBar() {
        window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    fun dismissIfShowing() {
        if (isShowing) dismiss()
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith(
            "Log.w(Tag.WARN.name, \"onBackPressed: is disabled for LoadingDialogFragment\")",
            "android.util.Log",
            "com.example.truckercore.model.configs.app_constants.Tag"
        )
    )
    override fun onBackPressed() {
        Log.w(Tag.WARN.name, "onBackPressed: is disabled for LoadingDialogFragment")
    }

}