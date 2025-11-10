package com.example.truckercore.core.my_lib.expressions

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.navigateToDirection(direction: NavDirections) {
    val navController = this.findNavController()
    navController.navigate(direction)
}

fun Fragment.launchOnFragmentLifecycle(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block(this@repeatOnLifecycle)
        }
    }
}

fun Fragment.popBackstack() {
    findNavController().popBackStack()
}

fun Fragment.showToast(msg: String, duration: Int = LENGTH_SHORT) {
    Toast.makeText(this.requireContext(), msg, duration).show()
}