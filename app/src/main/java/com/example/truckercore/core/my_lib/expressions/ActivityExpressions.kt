package com.example.truckercore.core.my_lib.expressions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.closeKeyboard() {
    val view = currentFocus ?: View(this)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = window.decorView.rootView
    val rect = android.graphics.Rect()
    rootView.getWindowVisibleDisplayFrame(rect)

    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom

    // se mais de 15% da tela estiver ocupada pelo teclado, consideramos que ele está aberto
    return keypadHeight > screenHeight * 0.15
}