package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore.view.ui_error.UiError
import kotlinx.coroutines.flow.MutableSharedFlow

class ForgetPasswordEffectManager {

    private val _effect = MutableSharedFlow<ForgetPasswordEffect>()
    val effect get() = _effect

    suspend fun setRecoverableError(uiError: UiError.Recoverable) {
        _effect.emit(ForgetPasswordEffect.RecoverableError(uiError.message))
    }

}