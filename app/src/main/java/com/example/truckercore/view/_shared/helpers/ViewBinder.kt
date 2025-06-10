package com.example.truckercore.view._shared.helpers

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import com.example.truckercore.view_model._shared.components.Visibility
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.view_model._shared._contracts.UiComponent
import com.google.android.material.textfield.TextInputLayout

object ViewBinder {

    fun bindTextInput(textInput: TextInputComponent, view: TextInputLayout) {
        if (textInput.errorText != view.error) {
            view.error = textInput.errorText
            view.helperText = null
            if(view.endIconDrawable != null) {
                view.errorIconDrawable = null
            }
        }
        if (textInput.helperText != view.helperText) {
            view.helperText = textInput.helperText
            view.error = null
        }
        handleVisibility(textInput, view)
    }

    fun bindButton(button: ButtonComponent, view: Button) {
        handleEnable(button, view)
        handleVisibility(button, view)
    }

    private fun handleEnable(component: UiComponent, view: View) {

        fun shouldBeEnabled(component: UiComponent, view: View): Boolean {
            return component.isEnabled && !view.isEnabled
        }

        fun shouldBeDisabled(component: UiComponent, view: View): Boolean {
            return !component.isEnabled && view.isEnabled
        }

        when {
            shouldBeEnabled(component, view) -> view.isEnabled = true
            shouldBeDisabled(component, view) -> view.isEnabled = false
        }
    }

    private fun handleVisibility(component: UiComponent, view: View) {

        fun shouldSetVisible(component: UiComponent, view: View): Boolean {
            return (component.visibility == Visibility.VISIBLE) && view.visibility != VISIBLE
        }

        fun shouldSetInvisible(component: UiComponent, view: View): Boolean {
            return (component.visibility == Visibility.INVISIBLE) && view.visibility != INVISIBLE
        }

        fun shouldSetGone(component: UiComponent, view: View): Boolean {
            return (component.visibility == Visibility.GONE) && view.visibility != GONE
        }

        when {
            shouldSetVisible(component, view) -> view.visibility = VISIBLE
            shouldSetInvisible(component, view) -> view.visibility = INVISIBLE
            shouldSetGone(component, view) -> view.visibility = GONE
        }
    }

}