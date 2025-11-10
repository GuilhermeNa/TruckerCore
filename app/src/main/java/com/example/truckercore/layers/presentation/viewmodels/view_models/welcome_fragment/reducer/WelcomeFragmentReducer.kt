package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.reducer

import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.BaseReducer
import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.ReducerResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.effect.WelcomeFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.event.WelcomeFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.state.WelcomeFragmentState

class WelcomeFragmentReducer :
    BaseReducer<WelcomeFragmentEvent, WelcomeFragmentState, WelcomeFragmentEffect>() {

    override fun reduce(
        state: WelcomeFragmentState,
        event: WelcomeFragmentEvent
    ): ReducerResult<WelcomeFragmentState, WelcomeFragmentEffect> = when (event) {
        is WelcomeFragmentEvent.Click -> handleClickEvent(state, event)
        is WelcomeFragmentEvent.PagerChanged -> handlePagerEvent(state, event.position)
    }

    private fun handlePagerEvent(
        state: WelcomeFragmentState,
        position: Int
    ): ReducerResult<WelcomeFragmentState, WelcomeFragmentEffect> {
        val newState = state.updatePage(position)
        return resultWithState(newState)
    }

    private fun handleClickEvent(
        state: WelcomeFragmentState,
        event: WelcomeFragmentEvent.Click
    ): ReducerResult<WelcomeFragmentState, WelcomeFragmentEffect> = when (event) {

        WelcomeFragmentEvent.Click.JumpButton -> {
            resultWithEffect(WelcomeFragmentEffect.Navigation.ToEmailAuth)
        }

        WelcomeFragmentEvent.Click.LeftFab -> {
            if (state.isFirstPage()) noChange() else {
                val newState = state.paginateBack()
                val newEffect = WelcomeFragmentEffect.Pagination.Back
                resultWithStateAndEffect(newState, newEffect)
            }
        }

        WelcomeFragmentEvent.Click.RightFab -> {
            if (state.isLastPage()) {
                resultWithEffect(WelcomeFragmentEffect.Navigation.ToEmailAuth)
            } else {
                val newState = state.paginateForward()
                val newEffect = WelcomeFragmentEffect.Pagination.Forward
                resultWithStateAndEffect(newState, newEffect)
            }
        }

    }

}