package com.example.truckercore.view.fragments.forget_password

import android.widget.Button
import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.classes.FieldState
import com.google.android.material.textfield.TextInputLayout

class ForgetPasswordUiStateHandler(
    private val emailLayout: TextInputLayout,
    private val button: Button
) {

    fun handleButton(buttonState: ButtonState) {
        when (buttonState.isEnabled) {
            true -> enableButton()
            false -> disableButton()
        }
    }

    private fun enableButton() {
        if (!button.isEnabled) button.isEnabled = true
    }

    private fun disableButton() {
        if (button.isEnabled) button.isEnabled = false
    }

    fun handleEmailLayout(emailField: FieldState) {
        emailField.handle(
            onValid = { hideEmailError() },
            onError = ::showEmailError,
            onNeutral = { hideEmailError() }
        )
    }

    private fun showEmailError(message: String) {
        if (emailLayout.error == null) {
            emailLayout.error = message
            emailLayout.errorIconDrawable = null
        }
    }

    private fun hideEmailError() {
        if (emailLayout.error != null) {
            emailLayout.error = null
            emailLayout.errorIconDrawable = null
        }
    }

}