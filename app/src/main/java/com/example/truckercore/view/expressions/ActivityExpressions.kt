package com.example.truckercore.view.expressions

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String, length: Int) {
    Toast.makeText(this, message, length).show()
}