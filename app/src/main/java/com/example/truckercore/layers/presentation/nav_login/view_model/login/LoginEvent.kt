package com.example.truckercore.layers.presentation.nav_login.view_model.login

import com.example.truckercore.domain._shared.helpers.ViewError

sealed class LoginEvent : com.example.truckercore.presentation.viewmodels._shared._contracts.Event {

    sealed class UiEvent : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent() {
        sealed class Typing: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent() {
            data class EmailField(val text: String) : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing()
            data class PasswordField(val text: String) : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Typing()
        }
        sealed class Click: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent() {
            data class CheckBox(val isChecked: Boolean): com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click()
            data object Background : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click()
            data object EnterButton : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click()
            data object NewAccountButton : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click()
            data object RecoverPasswordButton : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.UiEvent.Click()
        }
    }

    sealed class SystemEvent : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent() {
        sealed class LoginTask: com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent() {
            data object Executing : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask()
            data object Success : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask()
            data class CriticalError(val e: ViewError.Critical) : com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask()
            data class RecoverableError(val e: ViewError.Recoverable): com.example.truckercore.presentation.viewmodels.view_models.login.LoginEvent.SystemEvent.LoginTask()
        }
    }

}