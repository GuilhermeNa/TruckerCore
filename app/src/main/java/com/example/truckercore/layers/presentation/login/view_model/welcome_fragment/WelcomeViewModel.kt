package com.example.truckercore.layers.presentation.login.view_model.welcome_fragment

import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.core.my_lib.expressions.launchOnViewModelScope
import com.example.truckercore.core.my_lib.ui_components.FabComponent
import com.example.truckercore.core.my_lib.ui_components.Visibility
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.core.my_lib.expressions.handle
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomeFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomeFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomeFragmentReducer
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomeFragmentState

/**
 * ViewModel responsible for managing the UI logic of the Welcome screen.
 *
 * It coordinates state and effects through [WelcomeFragmentReducer],
 * exposes reactive flows for UI observation, and handles persistence-related
 * actions such as marking the first access as complete.
 *
 * @property preferences Repository used to persist onboarding progress (e.g., first access).
 * @property flavorService Provides flavor-dependent data for the welcome pager.
 */
class WelcomeViewModel(
    private val preferences: PreferencesRepository,
    flavorService: FlavorService
) : BaseViewModel() {

    /**
     * Lazily initializes the initial UI state for the Welcome screen.
     *
     * The pager starts at position 0 with the FAB initially invisible,
     * and the data for the pager is retrieved from [flavorService].
     */
    private val initialUiState by lazy {
        WelcomeFragmentState(
            pagerPos = WelcomeFragmentState.INITIAL_PAGER_POS,
            fab = FabComponent(visibility = Visibility.INVISIBLE, isEnabled = true),
            data = flavorService.getWelcomeFragmentPagerData()
        )
    }

    /**
     * Manages the current [WelcomeFragmentState] and provides reactive updates to the UI.
     */
    private val stateManager = StateManager(initialUiState)
    val stateFlow get() = stateManager.stateFlow

    /**
     * Handles transient one-off effects (e.g., navigation, animations).
     */
    private val effectManager = EffectManager<WelcomeFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    /**
     * The reducer that processes events and produces new states or effects.
     */
    private val reducer = WelcomeFragmentReducer()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------

    /**
     * Called whenever a new [WelcomeFragmentEvent] occurs in the UI.
     *
     * The event is delegated to the [reducer], and the resulting state or effect
     * is propagated via [stateManager] and [effectManager].
     *
     * @param event The event emitted from the Welcome Fragment (e.g., button click, pager swipe).
     */
    fun onEvent(event: WelcomeFragmentEvent) {
        val result = reducer.reduce(stateManager.currentState(), event)
        result.handle(stateManager::update, effectManager::trySend)
    }

    /**
     * Returns the current pager position from the active [WelcomeFragmentState].
     *
     * @return The current pager index as an [Int].
     */
    fun currentPagerPosition(): Int = stateManager.currentState().pagerPos

    /**
     * Marks that the user has completed the first access (onboarding) process
     * by updating the stored preference asynchronously.
     */
    fun markFirstAccessCompleteOnPreferences() {
        launchOnViewModelScope {
            preferences.setFirstAccessComplete()
        }
    }

}