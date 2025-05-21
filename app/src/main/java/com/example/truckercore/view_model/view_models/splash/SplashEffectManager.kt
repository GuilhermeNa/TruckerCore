package com.example.truckercore.view_model.view_models.splash

import kotlinx.coroutines.flow.MutableSharedFlow

class SplashEffectManager {

    private val _effect : MutableSharedFlow<SplashEffect> = MutableSharedFlow()
    val effect get() = _effect

    suspend fun setTransitionToLoading() {
        _effect.emit(SplashEffect.TransitionToLoading)
    }

    suspend fun setTransitionToNavigation() {
        _effect.emit(SplashEffect.TransitionToNavigation)
    }

}