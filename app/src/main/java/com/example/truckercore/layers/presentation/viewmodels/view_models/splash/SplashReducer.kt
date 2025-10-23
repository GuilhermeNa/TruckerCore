package com.example.truckercore.layers.presentation.viewmodels.view_models.splash

import com.example.truckercore.domain.view_models.splash.effect.SplashEffect
import com.example.truckercore.domain.view_models.splash.use_case.SplashDirection

class SplashReducer : com.example.truckercore.presentation.viewmodels._shared._base.reducer.BaseReducer<com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent, com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState, SplashEffect>() {

    override fun reduce(
        state: com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState,
        event: com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent
    ): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState, SplashEffect> = when (event) {
        is com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.Initialize -> handleInitEvent(state, event)
        is com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask -> handleLoadUserTaskEvent(state, event)
        is com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd -> handleTransitionEvent(event)
    }

    private fun handleInitEvent(
        state: com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState,
        event: com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.Initialize
    ): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState, SplashEffect> {
        val newState = state.updateName(event.appName).loading()
        val newEffect = SplashEffect.UiEffect.Transition.ToLoading
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun handleLoadUserTaskEvent(
        state: com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState,
        event: com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask
    ): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState, SplashEffect> = when (event) {
        com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask.CriticalError -> {
            val newEffect = SplashEffect.UiEffect.Navigate.ToNotification
            resultWithEffect(newEffect)
        }

        is com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.SystemEvent.LoadUserTask.Success -> {
            val newState = state.loaded()
            val newEffect = SplashEffect.UiEffect.Transition.ToLoaded
            resultWithStateAndEffect(newState, newEffect)
        }
    }

    private fun handleTransitionEvent(
        event: com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd
    ): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<com.example.truckercore.presentation.viewmodels.view_models.splash.state.SplashState, SplashEffect> = when (event) {
        com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd.ToLoading -> {
            val newEffect = SplashEffect.SystemEffect.ExecuteLoadUserTask
            resultWithEffect(newEffect)
        }

        is com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent.TransitionEnd.ToLoaded -> {
            val newEffect = when (event.direction) {
                SplashDirection.WELCOME -> SplashEffect.UiEffect.Navigate.ToWelcome
                SplashDirection.LOGIN -> SplashEffect.UiEffect.Navigate.ToLogin
                SplashDirection.MAIN -> SplashEffect.UiEffect.Navigate.ToMain
                SplashDirection.CONTINUE_REGISTER -> SplashEffect.UiEffect.Navigate.ToContinue
            }
            resultWithEffect(newEffect)
        }
    }

}