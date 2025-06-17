package com.example.truckercore._shared.expressions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

/**
 * Navigates to the specified destination using a NavController.
 *
 * This function leverages the Navigation component in Android to handle navigation within the app.
 * It is typically used within a Fragment to navigate to another screen.
 *
 * @param direction The [NavDirections] object containing the navigation direction to a destination.
 */
fun Fragment.navigateToDirection(direction: NavDirections) {
    val navController = Navigation.findNavController(this.requireView())
    navController.navigate(direction)
}

fun Fragment.navigateToDirection(direction: Int) {
    val navController = Navigation.findNavController(this.requireView())
    navController.navigate(direction)
}

fun Fragment.navController(): NavController {
    return Navigation.findNavController(this.requireView())
}

fun Fragment.navigateToActivity(
    clazz: Class<*>, finishActual: Boolean = false,
    intent: Intent.() -> Unit = {}
) {
    val context = this.requireContext()
    Intent(context, clazz).apply {
        intent()
        startActivity(this)
    }
    if (finishActual) this.requireActivity().finish()
}

fun Fragment.navigateToActivity(intent: Intent, finishActual: Boolean = false) {
    startActivity(intent)
    if (finishActual) this.requireActivity().finish()
}

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    val context = this.requireContext()
    Toast.makeText(context, message, length).show()
}

fun Fragment.showGreenSnackBar(text: String) {
    Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor("#3ED745"))
        .setTextColor(Color.parseColor("#FFFFFF"))
        .show()
}

fun Fragment.showRedSnackBar(text: String) {
    Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor("#FF0000"))
        .setTextColor(Color.parseColor("#FFFFFF"))
        .show()
}

fun Fragment.popBackStack() {
    val navController = Navigation.findNavController(this.requireView())
    navController.popBackStack()
}

inline fun Fragment.doIfResumedOrElse(
    resumed: () -> Unit = {},
    orElse: () -> Unit = {}
) {
    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) resumed()
    else orElse()
}

inline fun Fragment.doIfResumed(
    run: () -> Unit = {}
) {
    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) run()
}

inline fun Fragment.doIfRecreating(run: () -> Unit) {
    if (this.lifecycle.currentState != Lifecycle.State.RESUMED) run()
}

/**
 * Extension function for Fragment to hide the keyboard.
 * This function will call the `hideKeyboard` function in the Activity context.
 */
fun Fragment.hideKeyboard() {
    val inputMethodManager =
        this.requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val isKeyboardOpen = inputMethodManager.isAcceptingText

    if (isKeyboardOpen) inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
}

fun Fragment.isKeyboardVisible(): Boolean {
    val inputMethodManager =
        this.requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isAcceptingText
}

fun Fragment.hideKeyboardAndClearFocus(vararg view: View) {
    this.hideKeyboard()
    view.forEach { v -> if (v.hasFocus()) v.clearFocus() }
}
