package com.example.truckercore.view_model.view_models.email_auth.effect

import kotlinx.coroutines.flow.MutableSharedFlow

class EmailAuthEffectManager {

    private val _effect: MutableSharedFlow<EmailAuthEffect> = MutableSharedFlow()
    val effect get() = _effect


    suspend fun setClearFocusAndHideKeyboardEffect() {
        setEffect(EmailAuthEffect.ClearFocusAndHideKeyboard)
    }

    suspend fun setNavigateToLoginEffect() {
        setEffect(EmailAuthEffect.NavigateToLogin)
    }

    private suspend fun setEffect(newEffect: EmailAuthEffect) {
        _effect.emit(newEffect)
    }

}