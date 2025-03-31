package com.example.truckercore.view_model.view_models.email_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailAuthViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val _fragmentState: MutableStateFlow<EmailAuthFragState> =
        MutableStateFlow(EmailAuthFragState.Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    private val _nameField = MutableStateFlow(Field())
    val nameField get() = _nameField.asStateFlow()

    private val _event: MutableSharedFlow<EmailAuthFragEvent> = MutableSharedFlow()
    val event get() = _event.asSharedFlow()

    fun createUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val credential = EmailAuthCredential(email, password)
            val response = authService.createUserWithEmail(credential)

        }
    }

    fun setEvent(newEvent: EmailAuthFragEvent) {
        viewModelScope.launch {
            _event.emit(newEvent)
        }
    }

    fun setState(newState: EmailAuthFragState) {
        _fragmentState.value = newState
    }

    companion object {
        const val NAME = "nameField"
        const val SURNAME = "surname"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val CONFIRMATION = "confirmation"
    }

}

class Field(
    val clicked: Boolean = false,
    val text: String = "",
)