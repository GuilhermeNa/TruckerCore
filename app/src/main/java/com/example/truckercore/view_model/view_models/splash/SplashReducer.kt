package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.view_model._shared._base.reducer.BaseReducer
import com.example.truckercore.view_model._shared._base.reducer.ReducerResult
import com.example.truckercore.view_model.view_models.splash.effect.SplashEffect
import com.example.truckercore.view_model.view_models.splash.event.SplashEvent
import com.example.truckercore.view_model.view_models.splash.state.SplashState
import com.example.truckercore.view_model.view_models.splash.use_case.SplashDirection

class SplashReducer : BaseReducer<SplashEvent, SplashState, SplashEffect>() {

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
        SplashEvent.SystemEvent.LoadUserTask.CriticalError -> {
            val newEffect = SplashEffect.UiEffect.Navigate.ToNotification
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
                SplashDirection.WELCOME -> SplashEffect.UiEffect.Navigate.ToWelcome
                SplashDirection.LOGIN -> SplashEffect.UiEffect.Navigate.ToLogin
                SplashDirection.MAIN -> SplashEffect.UiEffect.Navigate.ToMain
                SplashDirection.CONTINUE_REGISTER -> SplashEffect.UiEffect.Navigate.ToContinue
            }
            resultWithEffect(newEffect)
        }
    }

}