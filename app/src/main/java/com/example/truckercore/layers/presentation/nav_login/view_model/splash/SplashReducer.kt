package com.example.truckercore.layers.presentation.nav_login.view_model.splash

import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.effect.SplashEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.event.SplashEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.state.SplashState
import com.example.truckercore.layers.presentation.viewmodels.view_models.splash.effect.SplashDirection

class SplashReducer : Reducer<SplashEvent, SplashState, SplashEffect>() {

    override fun reduce(
        state: SplashState,
        event: SplashEvent
    ): ReducerResult<SplashState, SplashEffect> = when (event) {
        is SplashEvent.SystemEvent.Initialize -> handleInitEvent(state, event)
        is SplashEvent.SystemEvent.LoadUserTask -> handleLoadUserTaskEvent(state, event)
        is SplashEvent.TransitionEnd -> handleTransitionEvent(event)
    }

    private fun handleInitEvent(
        state: SplashState,
        event: SplashEvent.SystemEvent.Initialize
    ): ReducerResult<SplashState, SplashEffect> {
        val newState = state.updateName(event.appName).loading()
        val newEffect = SplashEffect.UiEffect.Transition.ToLoading
        return resultWithStateAndEffect(newState, newEffect)
    }

    private fun handleLoadUserTaskEvent(
        state: SplashState,
        event: SplashEvent.SystemEvent.LoadUserTask
    ): ReducerResult<SplashState, SplashEffect> = when (event) {
        SplashEvent.SystemEvent.LoadUserTask.Error -> {
            val newEffect = SplashEffect.UiEffect.Navigation.ToNotification
            resultWithEffect(newEffect)
        }

        is SplashEvent.SystemEvent.LoadUserTask.Success -> {
            val newState = state.loaded()
            val newEffect = SplashEffect.UiEffect.Transition.ToLoaded
            resultWithStateAndEffect(newState, newEffect)
        }
    }

    private fun handleTransitionEvent(
        event: SplashEvent.TransitionEnd
    ): ReducerResult<SplashState, SplashEffect> = when (event) {
        SplashEvent.TransitionEnd.ToLoading -> {
            val newEffect = SplashEffect.SystemEffect.ExecuteLoadUserTask
            resultWithEffect(newEffect)
        }

        is SplashEvent.TransitionEnd.ToLoaded -> {
            val newEffect = when (event.direction) {
                SplashDirection.WELCOME -> SplashEffect.UiEffect.Navigation.ToWelcome
                SplashDirection.LOGIN -> SplashEffect.UiEffect.Navigation.ToLogin
                SplashDirection.MAIN -> SplashEffect.UiEffect.Navigation.ToMain
                SplashDirection.CONTINUE_REGISTER -> SplashEffect.UiEffect.Navigation.ToContinue
            }
            resultWithEffect(newEffect)
        }
    }

}