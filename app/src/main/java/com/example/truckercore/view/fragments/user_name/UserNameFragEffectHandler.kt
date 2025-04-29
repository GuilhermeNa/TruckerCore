package com.example.truckercore.view.fragments.user_name

import com.example.truckercore.model.infrastructure.app_exception.AppException
import com.example.truckercore.model.shared.utils.expressions.handleOnUi
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.showToast

class UserNameFragEffectHandler(private val fragment: UserNameFragment) {

    /**
     * Handles the effect when the profile is successfully updated.
     * Navigates to the next screen.
     */
    fun handleProfileUpdatedEffect() {
        val direction = UserNameFragmentDirections.actionUserNameFragmentToVerifyingEmailFragment()
        fragment.navigateTo(direction)
    }

    /**
     * Handles errors that occur during the profile update process.
     * Displays a message or starts a new activity for fatal errors.
     *
     * @param error The exception thrown during the profile update.
     */
    fun handleProfileUpdateFailedEffect(error: AppException) {
        val ec = error.errorCode
        ec.handleOnUi(
            onRecoverable = { fragment.showToast(ec.userMessage) },
            onFatalError = {
                val intent = NotificationActivity.newInstance(
                    context = fragment.requireContext(),
                    errorHeader = ec.name,
                    errorBody = ec.userMessage
                )
                fragment.requireActivity().startActivity(intent)
                fragment.requireActivity().finish()
            }
        )
    }

}