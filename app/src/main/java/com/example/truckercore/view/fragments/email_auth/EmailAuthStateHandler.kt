package com.example.truckercore.view.fragments.email_auth

import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Lifecycle
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.model.configs.app_constants.Tag
import com.example.truckercore.view.dialogs.LoadingDialog
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError

private const val ERROR = "1"
private const val EMPTY = "0"

class EmailAuthStateHandler(private val binding: FragmentEmailAuthBinding) {

    private val context = binding.root.context
    private val view = binding.root.rootView
    private val dialog = LoadingDialog(context)

    // Helper Classes ------------------------------------------------------------------------------
    private val textViewErrorHandler = EmailAuthFragTextViewHandler(binding)
    private val transitionHandler = EmailAuthTransitionHandler(binding.fragEmailAuthMain)
    private val genericErrorHandler = EmailAuthGenericErrorHandler(view)

    //----------------------------------------------------------------------------------------------

    fun setCreatingState() {
        dialog.show()
    }

    fun setSuccessState() {
        dialog.dismiss()
    }

    fun setErrorState(
        errorMap: HashMap<EmailAuthFragError, String>,
        lifecycleState: Lifecycle.State
    ) {
        dialog.dismiss()

        if (errorMap.hasEditTextError()) {
            val transitionCode = getTransitionCode(errorMap)
            textViewErrorHandler(transitionCode, errorMap)
            transitionHandler(transitionCode, lifecycleState)
        } else {
            genericErrorHandler(errorMap)
        }

    }

    private fun HashMap<EmailAuthFragError, String>.hasEditTextError(): Boolean {
        return this.containsKey(EmailAuthFragError.InvalidEmail) ||
                this.containsKey(EmailAuthFragError.InvalidPassword) ||
                this.containsKey(EmailAuthFragError.InvalidPasswordConfirmation)
    }

    private fun getTransitionCode(errorMap: HashMap<EmailAuthFragError, String>): String {
        val code = StringBuilder()

        val v1 =
            if (errorMap.containsKey(EmailAuthFragError.InvalidEmail)) ERROR else EMPTY
        code.append(v1)

        val v2 =
            if (errorMap.containsKey(EmailAuthFragError.InvalidPassword)) ERROR else EMPTY
        code.append(v2)

        val v3 =
            if (errorMap.containsKey(EmailAuthFragError.InvalidPasswordConfirmation)) ERROR else EMPTY
        code.append(v3)

        return code.toString()
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

private class EmailAuthFragTextViewHandler(private val binding: FragmentEmailAuthBinding) {

    operator fun invoke(
        code: String,
        errorMap: HashMap<EmailAuthFragError, String>
    ) {
        val arrCode = code.toCharArray()
        val hasError = '1'

        if (arrCode[0] == hasError) {
            binding.fragEmailAuthEmailError.text = errorMap[EmailAuthFragError.InvalidEmail]
        }

        if (arrCode[1] == hasError) {
            binding.fragEmailAuthPasswordError.text = errorMap[EmailAuthFragError.InvalidPassword]
        }

        if (arrCode[2] == hasError) {
            binding.fragEmailAuthConfirmationError.text =
                errorMap[EmailAuthFragError.InvalidPasswordConfirmation]
        }

    }

}

private class EmailAuthGenericErrorHandler(private val view: View) {

    operator fun invoke(errorMap: HashMap<EmailAuthFragError, String>) {
        val errorMessage = when {
            errorMap.containsKey(EmailAuthFragError.Network) -> errorMap[EmailAuthFragError.Network]
                ?: "Falha ao carregar mensagem de erro."

            errorMap.containsKey(EmailAuthFragError.Unknown) -> errorMap[EmailAuthFragError.Unknown]
                ?: "Falha ao carregar mensagem de erro."

            else -> "Falha ao carregar erro."
        }
        view.showSnackBarRed(errorMessage)
    }

}

private class EmailAuthTransitionHandler(private val layout: MotionLayout) {

    private val transitionManager = EmailAuthFragTransitions()

    operator fun invoke(transitionCode: String, lifecycleState: Lifecycle.State) {
        transitionManager.getTransition(transitionCode)?.let { transition ->
            startTransition(lifecycleState, transition)
        } ?: notifyError(transitionCode)
    }

    private fun startTransition(lifecycleState: Lifecycle.State, transitionState: Int) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            layout.transitionToState(transitionState, 200)
        } else {
            layout.jumpToState(transitionState)
        }
    }

    private fun notifyError(transitionCode: String) {
        Log.e(
            Tag.ERROR.name,
            "EmailAuthTransitionHandler failed on loading transition: $transitionCode."
        )
        layout.showSnackBarRed("Falha ao carregar transição.")
    }

}

private class EmailAuthFragTransitions {

    companion object {
        private const val STATE_ERROR_1 = "100"
        private const val STATE_ERROR_2 = "010"
        private const val STATE_ERROR_3 = "001"
        private const val STATE_ERROR_4 = "110"
        private const val STATE_ERROR_5 = "101"
        private const val STATE_ERROR_6 = "011"
        private const val STATE_ERROR_7 = "111"
    }

    private val transitionsMap = hashMapOf(
        Pair(STATE_ERROR_1, R.id.frag_email_auth_state_1),
        Pair(STATE_ERROR_2, R.id.frag_email_auth_state_2),
        Pair(STATE_ERROR_3, R.id.frag_email_auth_state_3),
        Pair(STATE_ERROR_4, R.id.frag_email_auth_state_4),
        Pair(STATE_ERROR_5, R.id.frag_email_auth_state_5),
        Pair(STATE_ERROR_6, R.id.frag_email_auth_state_6),
        Pair(STATE_ERROR_7, R.id.frag_email_auth_state_7)
    )

    fun getTransition(transitionCode: String): Int? = transitionsMap[transitionCode]

}

