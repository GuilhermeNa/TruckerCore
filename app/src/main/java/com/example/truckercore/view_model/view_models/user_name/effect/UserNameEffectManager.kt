package com.example.truckercore.view_model.view_models.user_name.effect

import com.example.truckercore.view_model._shared._base.managers.EffectManager
import com.example.truckercore.view_model._shared.helpers.ViewError
import kotlinx.coroutines.flow.MutableSharedFlow

class UserNameEffectManager: EffectManager<UserNameEffect>() {

    suspend fun setRecoverableErrorEffect(uiError: ViewError.Recoverable) {
        _effect.emit(UserNameEffect.RecoverableError(uiError.message))
    }

}