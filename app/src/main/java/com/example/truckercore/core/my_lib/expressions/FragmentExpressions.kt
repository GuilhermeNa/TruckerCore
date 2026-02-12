package com.example.truckercore.core.my_lib.expressions

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.navigateToDirection(direction: NavDirections) {
    val navController = this.findNavController()
    navController.navigate(direction)
}

fun Fragment.launchAndRepeatOnFragmentStartedLifeCycle(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block(this@repeatOnLifecycle)
        }
    }
}

fun Fragment.popBackstack() {
    runCatching {
        findNavController().popBackStack()
    }.getOrElse {
        parentFragmentManager.popBackStack()
    }
}

fun Fragment.showToast(msg: String, duration: Int = LENGTH_SHORT) {
    Toast.makeText(this.requireContext(), msg, duration).show()
}

fun Fragment.showSuccessSnackbar(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), msg, duration).apply {
        setBackgroundTint(Color.GREEN)
        setTextColor(Color.WHITE)
    }.show()
}

fun Fragment.showWarningSnackbar(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), msg, duration).apply {
        setBackgroundTint(Color.RED)
        setTextColor(Color.WHITE)
    }.show()
}

@SuppressLint("SourceLockedOrientationActivity")
fun Fragment.lockOrientationPortrait() {
    requireActivity().requestedOrientation =
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Fragment.unlockOrientation() {
    requireActivity().requestedOrientation =
        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}