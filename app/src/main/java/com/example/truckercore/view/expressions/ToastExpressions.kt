package com.example.truckercore.view.expressions

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBarRed(text: String) {
    Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor("#FF0000"))
        .setTextColor(Color.parseColor("#FFFFFF"))
        .show()
}