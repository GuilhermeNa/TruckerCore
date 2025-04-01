package com.example.truckercore.view.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.truckercore.R
import com.example.truckercore.model.configs.app_constants.Tag

class LoadingDialog(context: Context) :
    Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(R.layout.dialog_loading)
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

    companion object {
        const val TAG = "LoadingDialog"
    }

}