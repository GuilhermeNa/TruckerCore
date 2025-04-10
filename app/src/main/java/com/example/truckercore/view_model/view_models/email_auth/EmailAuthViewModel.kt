package com.example.truckercore.view_model.view_models.email_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.view_model.expressions.validateUserName
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.InvalidEmail
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.InvalidPassword
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.InvalidPasswordConfirmation
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.Network
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragError.Unknown
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess.UserCreatedAndEmailFailed
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.EmailAuthFragSuccess.UserCreatedAndEmailSent
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Error
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragState.Success
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val EMPTY_EMAIL_ERROR = "E-mail não pode estar vazio."
private const val EMAIL_WRONG_FORMAT = "Formato de e-mail inválido."
private const val EMPTY_PASSWORD_ERROR = "Senha não pode estar vazia."
private const val PASSWORD_WRONG_FORMAT = "Senha deve ter entre 6 e 12 caracteres."
private const val EMPTY_CONFIRMATION_ERROR = "Confirmação não pode estar vazia."
private const val INCOMPATIBLE_CONFIRMATION_ERROR = "As senhas não coincidem."
private const val NETWORK_ERROR = "Falha de conexão. Verifique sua internet."
private const val EMAIL_ALREADY_EXISTS = "E-mail já cadastrado."
private const val UNKNOWN_ERROR = "Erro desconhecido. Tente novamente."

class EmailAuthViewModel(
    private val args: EmailAuthVmArgs,
    private val authService: AuthService
) : ViewModel() {

    // State for managing the UI state of the fragment
    private val _fragmentState: MutableStateFlow<EmailAuthFragState> =
        MutableStateFlow(EmailAuthFragState.WaitingInput)
    val fragmentState get() = _fragmentState.asStateFlow()

    // Event Flow for handling events in the fragment
    private val _event: MutableSharedFlow<EmailAuthFragEvent> = MutableSharedFlow()
    val event get() = _event.asSharedFlow()

    // Helper class for checking the validity of entries
    private val validateEntries = ValidateEntries()

    // Error handling logic
    private val handleError = ErrorHandler()

    init {
        args.name.validateUserName()
    }

    //----------------------------------------------------------------------------------------------

    fun tryToAuthenticate(email: String, password: String, confirmation: String) {
        viewModelScope.launch {
            // Wait for view animation, can be removed without any problem
            delay(500)

            // Check if there is any fill error and return a Error State when it occurs
            val hashWithErrors = validateEntries(email, password, confirmation)
            if (hashWithErrors.isNotEmpty()) {
                setState(Error(hashWithErrors))
                return@launch
            }

            // Wait for view animation, can be removed without any problem
            delay(500)

            // Create a credential with hashed password and authenticate
            val credential = EmailAuthCredential(args.name, email, password)
            val newState = authenticateAndVerifyEmail(credential)
           // setState(newState)

        }
    }

    private suspend fun authenticateAndVerifyEmail(credential: EmailAuthCredential) =
        authService.createUserAndVerifyEmail(credential).let { response ->
          /*  when {
                response.userCreatedAndEmailSent -> Success(UserCreatedAndEmailSent)
                response.userCreatedAndEmailFailed -> Success(UserCreatedAndEmailFailed)
                else -> handleError.invoke(response.createUserError)
            }*/
        }

    fun setEvent(newEvent: EmailAuthFragEvent) {
        viewModelScope.launch {
            _event.emit(newEvent)
        }
    }

    fun setState(newState: EmailAuthFragState) {
        _fragmentState.value = newState
    }

    //----------------------------------------------------------------------------------------------
    // Helper Classes
    //----------------------------------------------------------------------------------------------
    private class ValidateEntries {

        operator fun invoke(
            email: String,
            password: String,
            confirmation: String
        ): HashMap<EmailAuthFragError, String> {
            val hashMap = hashMapOf<EmailAuthFragError, String>()

            if (email.isEmpty()) {
                val error = InvalidEmail
                val message = EMPTY_EMAIL_ERROR
                hashMap[error] = message
            } else if (!email.isEmailFormat()) {
                val error = InvalidEmail
                val message = EMAIL_WRONG_FORMAT
                hashMap[error] = message
            }

            if (password.isEmpty()) {
                val error = InvalidPassword
                val message = EMPTY_PASSWORD_ERROR
                hashMap[error] = message
            } else if (password.length !in 6..12) {
                val error = InvalidPassword
                val message = PASSWORD_WRONG_FORMAT
                hashMap[error] = message
            }

            if (confirmation.isEmpty()) {
                val error = InvalidPasswordConfirmation
                val message = EMPTY_CONFIRMATION_ERROR
                hashMap[error] = message
            } else if (confirmation != password) {
                val error = InvalidPasswordConfirmation
                val message = INCOMPATIBLE_CONFIRMATION_ERROR
                hashMap[error] = message
            }

            return hashMap
        }

    }

    private class ErrorHandler {

        operator fun invoke(error: Exception): Error {
            val errorMap = hashMapOf<EmailAuthFragError, String>()

            when (error) {
                is FirebaseNetworkException -> errorMap[Network] = NETWORK_ERROR
                is FirebaseAuthUserCollisionException -> errorMap[InvalidEmail] =
                    EMAIL_ALREADY_EXISTS

                else -> errorMap[Unknown] = UNKNOWN_ERROR
            }

            return Error(errorMap)

        }

    }

}

