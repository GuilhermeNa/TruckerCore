package com.example.truckercore.view.expressions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    val context = this.requireContext()
    Toast.makeText(context, message, length).show()
}

inline fun Fragment.onLifecycleState(
    resumed: () -> Unit = {},
    anyOther: () -> Unit = {}
) {
    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) resumed()
    else anyOther()
}

