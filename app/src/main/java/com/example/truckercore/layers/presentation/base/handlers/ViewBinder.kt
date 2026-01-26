package com.example.truckercore.layers.presentation.base.handlers

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import com.example.truckercore.core.my_lib.ui_components.FabComponent
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.core.my_lib.ui_components.UiComponent
import com.example.truckercore.core.my_lib.ui_components.Visibility
import com.example.truckercore.core.my_lib.ui_components.ButtonComponent
import com.example.truckercore.core.my_lib.ui_components.TextComponent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

/**
 * Centralized object responsible for binding UI state components
 * to their corresponding Android views.
 *
 * The [ViewBinder] ensures that view updates are performed only
 * when necessary, preventing redundant UI operations and keeping
 * the UI in sync with the current state.
 */
object ViewBinder {

    /**
     * Binds a [TextInputComponent] to a [TextInputLayout].
     *
     * Handles:
     * - Error text
     * - Helper text
     * - Visibility state
     *
     * Error and helper text are mutually exclusive.
     *
     * @param textInput Component representing the input state
     * @param view Material [TextInputLayout] to be updated
     */
    fun bindTextInput(textInput: TextInputComponent, view: TextInputLayout) {

        // Update error text only when it has changed
        if (textInput.errorText != view.error) {
            view.error = textInput.errorText
            view.helperText = null

            // Remove error icon if an end icon is present
            if (view.endIconDrawable != null) {
                view.errorIconDrawable = null
            }
        }

        // Update helper text only when it has changed
        if (textInput.helperText != view.helperText) {
            view.helperText = textInput.helperText
            view.error = null
        }

        // Apply visibility state
        handleVisibility(textInput, view)
    }

    /**
     * Binds a [ButtonComponent] to a standard [Button].
     *
     * Handles:
     * - Enabled / disabled state
     * - Visibility state
     *
     * @param button Component representing the button state
     * @param view Android [Button] to be updated
     */
    fun bindButton(button: ButtonComponent, view: Button) {
        handleEnable(button, view)
        handleVisibility(button, view)
    }

    /**
     * Binds a [FabComponent] to a [FloatingActionButton].
     *
     * Handles:
     * - Enabled / disabled state
     * - Visibility state
     *
     * @param button Component representing the FAB state
     * @param view Android [FloatingActionButton] to be updated
     */
    fun bindFab(button: FabComponent, view: FloatingActionButton) {
        handleEnable(button, view)
        handleVisibility(button, view)
    }

    /**
     * Updates the enabled state of a view based on the provided [UiComponent].
     *
     * The view is only updated if the desired state differs from
     * the current state.
     */
    private fun handleEnable(component: UiComponent, view: View) {

        fun shouldBeEnabled(component: UiComponent, view: View): Boolean =
            component.isEnabled && !view.isEnabled

        fun shouldBeDisabled(component: UiComponent, view: View): Boolean =
            !component.isEnabled && view.isEnabled

        when {
            shouldBeEnabled(component, view) -> view.isEnabled = true
            shouldBeDisabled(component, view) -> view.isEnabled = false
        }
    }

    /**
     * Updates the visibility of a view based on the provided [UiComponent].
     *
     * The view is only updated if the desired visibility differs
     * from the current state.
     */
    private fun handleVisibility(component: UiComponent, view: View) {

        fun shouldSetVisible(component: UiComponent, view: View): Boolean =
            component.visibility == Visibility.VISIBLE && view.visibility != VISIBLE

        fun shouldSetInvisible(component: UiComponent, view: View): Boolean =
            component.visibility == Visibility.INVISIBLE && view.visibility != INVISIBLE

        fun shouldSetGone(component: UiComponent, view: View): Boolean =
            component.visibility == Visibility.GONE && view.visibility != GONE

        when {
            shouldSetVisible(component, view) -> view.visibility = VISIBLE
            shouldSetInvisible(component, view) -> view.visibility = INVISIBLE
            shouldSetGone(component, view) -> view.visibility = GONE
        }
    }

    /**
     * Binds a [TextComponent] to a [TextView].
     *
     * @param component Component representing dynamic text
     * @param view Android [TextView] to be updated
     */
    fun bindText(component: TextComponent, view: TextView) {
        view.text = component.text
    }

    /**
     * Binds a static text value to a [TextView].
     *
     * @param text Text content to display
     * @param view Android [TextView] to receive the text
     */
    fun bindText(text: String, view: TextView) {
        view.text = text
    }

}