package com.example.truckercore.layers.presentation.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.truckercore.R
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.logger.AppLogger

/**
 * A custom dialog used to display a non-cancelable loading indicator.
 *
 * This dialog:
 * - Has a transparent background
 * - Hides the system navigation bar
 * - Prevents dismissal via the back button
 *
 * @param context The context in which the dialog is displayed.
 */
class LoadingDialog(context: Context) : Dialog(context) {

    /**
     * Called when the dialog is being created.
     * Initializes the dialog behavior and UI.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Prevent the dialog from being canceled by the user
        setCancelable(false)

        // Make the background transparent and hide the navigation bar
        setBackgroundTransparentAndHideNavigationBar()

        // Set the dialog layout
        setContentView(R.layout.dialog_loading)
    }

    /**
     * Configures the dialog window to:
     * - Use a transparent background
     * - Hide the system navigation bar for a cleaner UI
     */
    private fun setBackgroundTransparentAndHideNavigationBar() {
        window?.run {
            // Set the dialog background to transparent
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // Hide the navigation bar
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    /**
     * Disables the back button behavior for this dialog.
     *
     * This method is deprecated but overridden to prevent the dialog
     * from being dismissed when the back button is pressed.
     * Instead, a warning log is generated.
     */
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith(
            "AppLogger.w(getTag, \"onBackPressed: is disabled for LoadingDialogFragment\")",
            "android.util.Log",
            "com.example.truckercore.model.configs.app_constants.Tag"
        )
    )
    override fun onBackPressed() {
        // Log a warning indicating that the back button is disabled
        AppLogger.w(getTag, "onBackPressed: is disabled for LoadingDialogFragment")
    }

}