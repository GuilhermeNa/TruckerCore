package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore.model.errors.exceptions.AppException
import com.example.truckercore.view.fragments.user_name.UserNameFragment

/**
 * Sealed class to represent various events that can be triggered in the [UserNameFragment].
 * These events are collected and handled by the [UserNameViewModel] to manage fragment interactions.
 */
sealed class UserNameEvent {

    sealed class UiEvent : UserNameEvent() {
        data class TextChanged(val text: String) : UiEvent()
        data object FabCLicked : UiEvent()
    }

    sealed class SystemEvent : UserNameEvent() {
        data class SystemCreationFailed(val e: AppException) : SystemEvent()
        data object SystemCreationSuccess : SystemEvent()
    }

}