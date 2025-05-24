package com.example.truckercore.view.fragments.email_auth

import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.classes.FieldState

class EmailAuthUiStateHandler(
    private val motionLayout: MotionLayout,
    private val emailError: TextView,
    private val passwordError: TextView,
    private val confirmationError: TextView,
    private val button: Button,
) {

    fun transitionToState(id: Int) {
        motionLayout.transitionToState(id)
    }

    fun jumpToState(id: Int) {
        motionLayout.jumpToState(id)
    }

    fun handleEmailField(fieldState: FieldState) {
        if (fieldState.status == FieldState.Input.ERROR) {
            if (emailError.text != fieldState.message) emailError.text = fieldState.message
        } else {
            if (emailError.text.isNotBlank()) emailError.text = ""
        }
    }

    fun handlePasswordField(fieldState: FieldState) {
        if (fieldState.status == FieldState.Input.ERROR) {
            if (passwordError.text != fieldState.message) passwordError.text = fieldState.message
        } else {
            if (passwordError.text.isNotBlank()) passwordError.text = ""
        }
    }

    fun handleConfirmationField(fieldState: FieldState) {
        if (fieldState.status == FieldState.Input.ERROR) {
            if (confirmationError.text != fieldState.message) confirmationError.text = fieldState.message
        } else {
            if (confirmationError.text.isNotBlank()) confirmationError.text = null
        }
    }

    fun handleCreateButton(buttonState: ButtonState) {
        when (buttonState.isEnabled) {
            true -> if (!button.isEnabled) button.isEnabled = true
            false -> if (button.isEnabled) button.isEnabled = false
        }
    }

}



