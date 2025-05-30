package com.example.truckercore._utils.classes.ui_component

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class ViewBinder {

    fun bindTextInput(textInput: TextInputComponent, view: TextInputLayout) {
        if (textInput.errorText != view.error) {
            view.error = textInput.errorText
        }
        if (textInput.helperText != view.helperText) {
            view.helperText = textInput.helperText
        }
        handleVisibility(textInput, view)
    }

    fun bindButton(button: ButtonComponent, view: Button) {
        if (button.isEnabled && !view.isEnabled) {
            view.isEnabled = true
        }
        handleVisibility(button, view)
    }

    private fun handleVisibility(uiComponent: UiComponent, view: View) {
        when {
            shouldSetVisible(uiComponent, view) -> view.visibility = VISIBLE
            shouldSetInvisible(uiComponent, view) -> view.visibility = INVISIBLE
            shouldSetGone(uiComponent, view) -> view.visibility = VISIBLE
        }
    }

    private fun shouldSetVisible(uiComponent: UiComponent, view: View): Boolean {
        return (uiComponent.visibility == Visibility.VISIBLE) && view.visibility != VISIBLE
    }

    private fun shouldSetInvisible(uiComponent: UiComponent, view: View): Boolean {
        return (uiComponent.visibility == Visibility.INVISIBLE) && view.visibility != INVISIBLE
    }

    private fun shouldSetGone(uiComponent: UiComponent, view: View): Boolean {
        return (uiComponent.visibility == Visibility.GONE) && view.visibility != GONE
    }

}