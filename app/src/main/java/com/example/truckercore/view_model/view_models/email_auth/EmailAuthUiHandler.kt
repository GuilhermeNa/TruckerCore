package com.example.truckercore.view_model.view_models.email_auth

import android.content.res.ColorStateList
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view.expressions.setBottomMargin
import com.google.android.material.textfield.TextInputLayout

class EmailAuthUiHandler(private val binding: FragmentEmailAuthBinding) {

    fun setNameHelper(selected: Boolean) {
        val passLayout = binding.fragEmailAuthNameLayout

        if (selected && (!passLayout.isHelperTextEnabled)) {
            passLayout.setBottomMargin(8)
            passLayout.setHelperText(message = "Ex.: ${"José"}, ${"João"}...")
        } else {
            passLayout.setBottomMargin(0)
            passLayout.setHelperText(enabled = false)
        }

    }

    private fun TextInputLayout.setHelperText(enabled: Boolean = true, message: String? = "") {
        isHelperTextEnabled = enabled
        if (enabled) {
            helperText = message
            setHelperTextColor(
                ColorStateList.valueOf(
                    resources.getColor(R.color.white, null)
                )
            )
        }
    }

/*    fun setSurnameHelper(selected: Boolean) {
        val passLayout = binding.fragEmailAuthSurnameLayout

        if (selected && (!passLayout.isHelperTextEnabled)) {
            passLayout.setBottomMargin(8)
            passLayout.setHelperText(message = "Ex.: ${"Silva"}, ${"Freire"}...")
        } else {
            passLayout.setBottomMargin(0)
            passLayout.setHelperText(enabled = false)
        }

    }

    fun setPasswordHelper(selected: Boolean) {
        val passLayout = binding.fragEmailAuthPasswordLayout

        if (selected && (!passLayout.isCounterEnabled || !passLayout.isHelperTextEnabled)) {
            passLayout.setBottomMargin(8)
            passLayout.setHelperText(message = "A senha deve ter de 6 a 12 números")
            passLayout.setCounter(enabled = true)
        } else {
            passLayout.setBottomMargin(0)
            passLayout.setHelperText(enabled = false)
            passLayout.setCounter(enabled = false)
        }

    }*/

    private fun TextInputLayout.setCounter(enabled: Boolean) {
        isCounterEnabled = enabled
    }

    fun setRegisterButton(enabled: Boolean) {
        binding.fragEmailAuthRegisterButton.isEnabled = enabled
    }

    fun clearLayoutFocus() {
        val selectableLayouts = hashSetOf(
            binding.fragEmailAuthNameLayout,
            binding.fragEmailAuthSurnameLayout,
            binding.fragEmailAuthEmailLayout,
            binding.fragEmailAuthPasswordLayout,
            binding.fragEmailAuthConfirmPasswordLayout
        )
        selectableLayouts.forEach { layout ->
            if (layout.hasFocus()) layout.clearFocus()
        }
    }

/*    fun setNameError(enabled: Boolean) {
        val motionLayout = binding.fragEmailAuthMain
        //motionLayout.setTransition(R.id.frag_email_auth_start, R.id.frag_email_auth_motion_1)

        motionLayout.transitionToState(R.id.frag_email_auth_state_3)

        if (enabled) {
            motionLayout.transitionToEnd()
        } else
            motionLayout.transitionToStart()
    }*/

}