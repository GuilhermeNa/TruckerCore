package com.example.truckercore.view.expressions

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.truckercore.view.helpers.ExitAppManager

private const val PRESS_AGAIN_TO_EXIT = "Pression novamente para sair"

fun Fragment.getBackPressCallback(exitManager: ExitAppManager): OnBackPressedCallback {
    val activity = this.requireActivity()

    return object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val canExit = exitManager.handleExitAttempt()

            if (canExit) activity.finish()
            else showToast(PRESS_AGAIN_TO_EXIT, Toast.LENGTH_LONG)

        }
    }
}

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    val context = this.requireContext()
    Toast.makeText(context, message, length).show()
}

