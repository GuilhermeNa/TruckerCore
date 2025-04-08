package com.example.truckercore.view.expressions

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.truckercore.view.helpers.ExitAppManager

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    val context = this.requireContext()
    Toast.makeText(context, message, length).show()
}

