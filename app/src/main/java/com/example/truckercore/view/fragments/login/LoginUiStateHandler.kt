package com.example.truckercore.view.fragments.login

import androidx.fragment.app.Fragment
import com.example.truckercore._utils.expressions.hideKeyboardAndClearFocus
import com.example.truckercore.databinding.FragmentLoginBinding

class LoginUiStateHandler(private val binding: FragmentLoginBinding) {

    fun setEmailError() {
        val message = "Teste de erro ativo"
        binding.fragLoginEmailLayout.error = message
        binding.fragLoginEmailLayout.errorIconDrawable = null
    }

    fun setEmailSuccess() {
        val message = "Teste quando sucesso"
        binding.fragLoginEmailLayout.helperText = message
    }

    fun setEnterButton(enabled: Boolean) {
        binding.fragLoginEnterButton.isEnabled = enabled
    }

    fun hideKeyboardAndClearFocus(fragment: Fragment) {
        val focusableViews = arrayOf(
            binding.fragLoginEmailLayout,
            binding.fragLoginPasswordLayout
        )
        fragment.hideKeyboardAndClearFocus(*focusableViews)
    }

}