package com.example.truckercore.view.expressions

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBarRed(text: String) {
    this.view?.showSnackBarRed(text)
}

fun View.showSnackBarRed(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor("#FF0000"))
        .setTextColor(Color.parseColor("#FFFFFF"))
        .show()
}