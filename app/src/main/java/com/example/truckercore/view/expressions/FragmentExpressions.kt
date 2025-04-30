package com.example.truckercore.view.expressions

import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    val context = this.requireContext()
    Toast.makeText(context, message, length).show()
}

