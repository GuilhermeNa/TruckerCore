package com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers

import com.example.truckercore.layers.presentation.base.reducer.Reducer
import com.example.truckercore.layers.presentation.base.reducer.ReducerResult

/**
 * Reducer responsible for managing UI events and updating the [WelcomeFragmentState].
 *
 * It receives UI events represented by [WelcomeFragmentEvent],
 * and based on those, determines whether the [WelcomeFragmentState]
 * should change and/or a [WelcomeFragmentEffect] should be triggered.
 *
 * @see WelcomeFragmentEvent For the list of possible events.
 * @see WelcomeFragmentState For the current UI state representation.
 * @see WelcomeFragmentEffect For the possible side effects (navigation, animations, etc.).
 */
class WelcomeFragmentReducer :
    Reducer<WelcomeFragmentEvent, WelcomeFragmentState, WelcomeFragmentEffect>() {

    /**
     * Defines how the state and effects should change based on the received event.
     *
     * @param state The current UI state.
     * @param event The incoming event (e.g., button click, pager swipe).
     * @return A [ReducerResult] containing the updated state and/or triggered effect.
     */
    override fun reduce(
        state: WelcomeFragmentState,
        event: WelcomeFragmentEvent
    ): ReducerResult<WelcomeFragmentState, WelcomeFragmentEffect> = when (event) {
        is WelcomeFragmentEvent.Click -> handleClickEvent(state, event)
        is WelcomeFragmentEvent.PagerSwiped -> handlePagerEvent(state, event.position)
    }

    /**
     * Handles click-related events in the Welcome Fragment.
     * Depending on the clicked element, the state is updated and/or an effect is emitted.
     *
     * @param state The current UI state.
     * @param event The click event being processed.
     * @return A [ReducerResult] containing the new state and/or triggered effect.
     */
    private fun handleClickEvent(
        state: WelcomeFragmentState,
        event: WelcomeFragmentEvent.Click
    ): ReducerResult<WelcomeFragmentState, WelcomeFragmentEffect> = when (event) {

        // Triggered when the "Skip" button is clicked.
        WelcomeFragmentEvent.Click.SkipButton -> {
            resultWithEffect(WelcomeFragmentEffect.Navigation.ToEmailAuth)
        }

        // Triggered when the left FAB (Floating Action Button) is clicked.
        WelcomeFragmentEvent.Click.LeftFab -> {
            if (state.isFirstPage()) {
                noChange()
            } else {
                val newState = state.paginateBack()
                val newEffect = WelcomeFragmentEffect.Pagination.Back
                resultWithStateAndEffect(newState, newEffect)
            }
        }

        // Triggered when the right FAB (Floating Action Button) is clicked.
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

    /**
     * Handles swipe events on the pager (page changed by user gesture).
     * Updates the state to reflect the new pager position.
     *
     * @param state The current UI state.
     * @param position The new pager position (index) after the swipe.
     * @return A [ReducerResult] containing the updated state.
     */
    private fun handlePagerEvent(
        state: WelcomeFragmentState,
        position: Int
    ): ReducerResult<WelcomeFragmentState, WelcomeFragmentEffect> {
        val newState = state.updatePage(position)
        return resultWithState(newState)
    }

}
