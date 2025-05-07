package com.example.truckercore.view.fragments.login

import androidx.fragment.app.Fragment
import com.example.truckercore._utils.classes.FieldState
import com.example.truckercore._utils.expressions.hideKeyboardAndClearFocus
import com.example.truckercore.databinding.FragmentLoginBinding

class LoginUiStateHandler(private val binding: FragmentLoginBinding) {

    fun handlePassword(passwordState: FieldState) {
        passwordState.handle(
            onValid = { setPasswordSuccess() },
            onError = ::setPasswordError,
            onNeutral = { clearPassword() }
        )
    }

    private fun clearPassword() {
        with(binding.fragLoginPasswordLayout) {
            if (error == null && helperText == null) return@with
            else {
                error = null
                helperText = null
            }
        }
    }

    private fun setPasswordError(message: String) {
        with(binding.fragLoginPasswordLayout) {
            if (error == message) return@with
            else {
                helperText = null
                error = message
                errorIconDrawable = null
            }
        }
    }

    private fun setPasswordSuccess() {
        with(binding.fragLoginPasswordLayout) {
            if (helperText != null) return@with
            else {
                error = null
                helperText = "Senha válida"
            }
        }
    }

    fun handleEmail(emailState: FieldState) {
        emailState.handle(
            onValid = { setEmailSuccess() },
            onError = ::setEmailError,
            onNeutral = { clearEmail() }
        )
    }

    private fun clearEmail() {
        with(binding.fragLoginEmailLayout) {
            if (error == null && helperText == null) return@with
            else {
                error = null
                helperText = null
            }
        }
    }

    private fun setEmailError(message: String) {
        with(binding.fragLoginEmailLayout) {
            if (error == message) return@with
            else {
                helperText = null
                error = message
                errorIconDrawable = null
            }
        }
    }

    private fun setEmailSuccess() {
        with(binding.fragLoginEmailLayout) {
            if (helperText != null) return@with
            else {
                helperText = "Email válido"
                error = null
            }
        }
    }

    fun handleEnterButton(enabled: Boolean) {
        with(binding.fragLoginEnterButton) {
            isEnabled = when (enabled) {
                true -> if (isEnabled) return@with else true
                false -> if (!isEnabled) return@with else false
            }
        }
    }

    fun hideKeyboardAndClearFocus(fragment: Fragment) {
        val focusableViews = arrayOf(
            binding.fragLoginEmailLayout,
            binding.fragLoginPasswordLayout
        )
        fragment.hideKeyboardAndClearFocus(*focusableViews)
    }

}