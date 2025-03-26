package com.example.truckercore.view.expressions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Extension function for Fragment to hide the keyboard.
 * This function will call the `hideKeyboard` function in the Activity context.
 */
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

/**
 * Extension function for Activity to hide the keyboard.
 * This function will attempt to hide the keyboard by calling the `hideKeyboard` function with the current focused view.
 */
fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

/**
 * Extension function for Context to hide the keyboard.
 * This function hides the soft keyboard if it is currently visible by calling the InputMethodManager's hideSoftInputFromWindow method.
 *
 * @param view The view from which the keyboard will be hidden.
 */
private fun Context.hideKeyboard(view: View) {
    if (isKeyboardVisible()) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Extension function for Context to check if the keyboard is visible.
 *
 * @return True if the keyboard is currently visible, otherwise false.
 */
private fun Context.isKeyboardVisible(): Boolean {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val isKeyboardOpen = inputMethodManager.isAcceptingText
    return isKeyboardOpen
}