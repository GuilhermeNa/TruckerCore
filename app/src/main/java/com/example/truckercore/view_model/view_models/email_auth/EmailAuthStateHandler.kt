package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.Network
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.EmailAlreadyExists
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.Unknown
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.InvalidEmail
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.InvalidPassword
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.InvalidPasswordConfirmation
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view.dialogs.LoadingDialog

class EmailAuthStateHandler(private val binding: FragmentEmailAuthBinding) {

    private val context = binding.root.context
    private val dialog = LoadingDialog(context)

    fun setCreatingState() {
        dialog.show()
    }

    fun setSuccessState() {
        dialog.dismiss()
    }

    fun setErrorState(errorMap: HashMap<EmailAuthFragError, String>) {
        dialog.dismiss()

        errorMap.containsKey(Network)
        errorMap.containsKey(EmailAlreadyExists)
        errorMap.containsKey(Unknown)
        errorMap.containsKey(InvalidEmail)
        errorMap.containsKey(InvalidPassword)
        errorMap.containsKey(InvalidPasswordConfirmation)
    }

    fun clearLayoutFocus() {
        val selectableLayouts = hashSetOf(
            binding.fragEmailAuthEmailLayout,
            binding.fragEmailAuthPasswordLayout,
            binding.fragEmailAuthConfirmPasswordLayout
        )
        selectableLayouts.forEach { layout ->
            if (layout.hasFocus()) layout.clearFocus()
        }
    }

}