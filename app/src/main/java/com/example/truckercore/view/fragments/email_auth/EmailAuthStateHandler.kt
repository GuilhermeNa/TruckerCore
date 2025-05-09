package com.example.truckercore.view.fragments.email_auth

import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Lifecycle
import com.example.truckercore.R
import com.example.truckercore._utils.expressions.onLifecycleState
import com.example.truckercore._utils.expressions.showSnackBarRed
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.model.configs.enums.Tag
import com.example.truckercore.view.dialogs.LoadingDialog
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthUserInputValidationResult

/**
 * EmailAuthStateHandler is responsible for managing the UI state of the EmailAuthFragment.
 * It handles displaying loading dialogs, error messages, and UI transitions based on validation results.
 *
 * This class isolates view logic from the fragment to make it more modular and testable.
 */
class EmailAuthStateHandler(
    private val fragment: EmailAuthFragment,
    private val binding: FragmentEmailAuthBinding
) {

    // The context of fragment
    private val context = binding.root.context

    // Dialog used to indicate a loading state during user creation
    private val dialog by lazy { LoadingDialog(context) }

    // Handles UI transitions using MotionLayout
    private val transitionHandler = EmailAuthTransitionHandler(fragment, binding.fragEmailAuthMain)

    /**
     * Shows a loading dialog to indicate that the user creation process is ongoing.
     */
    fun setCreatingState() {
        dialog.show()
    }

    /**
     * Dismisses the loading dialog once the user creation is complete.
     */
    fun setSuccessState() {
        dialog.dismiss()
    }

    /**
     * Displays validation errors on the UI and triggers the appropriate transition state.
     *
     * @param validationResult [EmailAuthUserInputValidationResult] containing validation errors for email, password, and confirmation.
     * @param lifecycleState The current lifecycle state, used to decide whether to animate or jump transitions.
     */
    fun setUserInputErrorState(
        validationResult: EmailAuthUserInputValidationResult,
        lifecycleState: Lifecycle.State
    ) {
        dialog.dismiss()
        bindErrorMessage(validationResult)
        transitionHandler.invoke(validationResult.errorCode)
    }

    private fun bindErrorMessage(validationResult: EmailAuthUserInputValidationResult) {
        val fields = listOf(
            Pair(binding.fragEmailAuthEmailError, validationResult.emailError),
            Pair(binding.fragEmailAuthPasswordError, validationResult.passwordError),
            Pair(binding.fragEmailAuthConfirmationError, validationResult.confirmationError)
        )
        fields.forEach { (view, error) ->
            error?.let { view.text = error }
        }
    }

    fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }

}

/**
 * EmailAuthTransitionHandler manages UI transitions using MotionLayout in the EmailAuthFragment.
 * It maps binary error codes to predefined MotionLayout states and triggers transitions accordingly.
 *
 * @param layout The MotionLayout associated with the fragment's main view.
 */
private class EmailAuthTransitionHandler(
    private val fragment: EmailAuthFragment,
    private val layout: MotionLayout
) {

    private val transitionsMap = hashMapOf(
        Pair(STATE_ERROR_1, R.id.frag_email_auth_state_1),
        Pair(STATE_ERROR_2, R.id.frag_email_auth_state_2),
        Pair(STATE_ERROR_3, R.id.frag_email_auth_state_3),
        Pair(STATE_ERROR_4, R.id.frag_email_auth_state_4),
        Pair(STATE_ERROR_5, R.id.frag_email_auth_state_5),
        Pair(STATE_ERROR_6, R.id.frag_email_auth_state_6),
        Pair(STATE_ERROR_7, R.id.frag_email_auth_state_7)
    )

    /**
     * Invokes the transition that matches the given error code, if available.
     *
     * @param transitionCode A 3-digit binary string representing which fields contain errors.
     * @param lifecycleState The lifecycle state used to determine whether to animate or jump to the state.
     */
    operator fun invoke(transitionCode: String) {
        getTransition(transitionCode)?.let { transition ->
            startTransition(transition)
        } ?: notifyError(transitionCode)
    }

    /**
     * Starts the UI transition to the given state.
     */
    private fun startTransition(transitionState: Int) {
        fragment.onLifecycleState(
            resumed = { layout.transitionToState(transitionState, 200) },
            anyOther = { layout.jumpToState(transitionState) }
        )
    }

    /**
     * Logs and displays a fallback error message when a transition code is not found.
     */
    private fun notifyError(transitionCode: String) {
        Log.e(
            Tag.ERROR.name,
            "EmailAuthTransitionHandler failed on loading transition: $transitionCode."
        )
        layout.showSnackBarRed("Falha ao carregar transição.")
    }

    /**
     * Retrieves the transition ID associated with the given binary error code.
     */
    private fun getTransition(transitionCode: String): Int? = transitionsMap[transitionCode]

    companion object {
        private const val STATE_ERROR_1 = "100"
        private const val STATE_ERROR_2 = "010"
        private const val STATE_ERROR_3 = "001"
        private const val STATE_ERROR_4 = "110"
        private const val STATE_ERROR_5 = "101"
        private const val STATE_ERROR_6 = "011"
        private const val STATE_ERROR_7 = "111"
    }

}



