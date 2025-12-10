package com.example.truckercore.layers.presentation.nav_login.view_model.login.helpers

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.presentation.base.contracts.Event

sealed class LoginFragmentEvent : Event {

    data class Retry(val credential: EmailCredential) : LoginFragmentEvent()

    sealed class TextChange : LoginFragmentEvent() {
        data class Email(val text: String) : TextChange()
        data class Password(val text: String) : TextChange()
    }

    sealed class Click : LoginFragmentEvent() {
        data class Enter(val credential: EmailCredential) : Click()
        data object NewAccount : Click()
        data object ForgetPassword : Click()
    }

    sealed class LoginTask : LoginFragmentEvent() {
        data object Complete : LoginTask()
        data object Failure : LoginTask()
        data object NoConnection : LoginTask()
        data object InvalidCredential : LoginTask()
    }

}