package com.example.truckercore.view_model.view_models.user_name

import com.example.truckercore.view.ui_error.UiError
import kotlinx.coroutines.flow.MutableSharedFlow

class UserNameEffectManager {

    private val _effect = MutableSharedFlow<UserNameEffect>()
    val effect get() = _effect

    suspend fun setRecoverableErrorEffect(uiError: UiError.Recoverable) {
        _effect.emit(UserNameEffect.RecoverableError(uiError.message))
    }

}