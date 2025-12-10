package com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers

import com.example.truckercore.core.my_lib.expressions.isEmailFormat
import com.example.truckercore.core.my_lib.expressions.isPasswordFormat
import com.example.truckercore.layers.presentation.base.contracts.State

data class LoginFragmentState(
    val emailMsg: String? = null,
    val passMsg: String? = null,
    val status: LoginFragmentStatus = LoginFragmentStatus.WaitingInput
): State {

    private val isReadyToLogin get() = emailMsg == null && passMsg == null

    fun updateEmail(txt: String): LoginFragmentState {
        val newMsg = defineEmailStateMsg(txt)
        val newStatus = defineStatus(newEmailMsg = newMsg)
        return copy(emailMsg = newMsg, status = newStatus)
    }

    fun updatePassword(txt: String): LoginFragmentState {
        val newMsg = definePassStateMsg(txt)
        val newStatus = defineStatus(newPassMsg = newMsg)
        return copy(passMsg = newMsg, status = newStatus)
    }

    fun tryToLogin(): LoginFragmentState {
        if (!isReadyToLogin) throw IllegalStateException(ILLEGAL_STATE_MSG)
        return copy(status = LoginFragmentStatus.TryingLogin)
    }

    fun emailNotFound() = copy(
        emailMsg = EMAIL_NOT_FOUND_ERROR_MSG,
        status = LoginFragmentStatus.WaitingInput
    )

    fun wrongPassword() = copy(
        emailMsg = PASSWORD_INVALID_MSG,
        status = LoginFragmentStatus.WaitingInput
    )

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    private fun defineEmailStateMsg(input: String) = when {
        input.isBlank() -> EMAIL_BLANK_ERROR_MSG
        !input.isEmailFormat() -> EMAIL_WRONG_FORMAT_MSG
        else -> null
    }

    private fun definePassStateMsg(input: String) = when {
        input.isBlank() -> PASSWORD_BLANK_ERROR_MSG
        !input.isPasswordFormat() -> PASSWORD_WRONG_FORMAT_MSG
        else -> null
    }

    private fun defineStatus(newEmailMsg: String? = null, newPassMsg: String? = null) =
        if ((newEmailMsg ?: emailMsg) == null && (newPassMsg ?: passMsg) == null) {
            LoginFragmentStatus.ReadyToLogin
        } else LoginFragmentStatus.WaitingInput

    private companion object {

        private const val EMAIL_BLANK_ERROR_MSG = "Por favor, informe seu e-mail."
        private const val EMAIL_WRONG_FORMAT_MSG = "Formato de e-mail inválido."
        private const val EMAIL_NOT_FOUND_ERROR_MSG = "E-mail não encontrado."

        private const val PASSWORD_BLANK_ERROR_MSG = "Por favor, informe sua senha."
        private const val PASSWORD_WRONG_FORMAT_MSG = "A senha deve ser numérica e ter entre 6 e 12 dígitos."
        private const val PASSWORD_INVALID_MSG = "Senha incorreta."

        private const val ILLEGAL_STATE_MSG = "Não é possível continuar: dados inválidos."

    }

}