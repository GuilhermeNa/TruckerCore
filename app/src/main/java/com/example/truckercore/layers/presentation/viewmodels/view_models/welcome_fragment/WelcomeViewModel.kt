package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.config.flavor.FlavorService
import com.example.truckercore.layers.data.repository.preferences.PreferencesRepository
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.EffectManager
import com.example.truckercore.layers.presentation.viewmodels.base._base.managers.StateManager
import com.example.truckercore.layers.presentation.viewmodels.base.abstractions.BaseViewModel
import com.example.truckercore.layers.presentation.viewmodels.base.expressions.handleResult
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.effect.WelcomeFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.event.WelcomeFragmentEvent
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.reducer.WelcomeFragmentReducer
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.state.WelcomeFragmentState
import kotlinx.coroutines.launch

/**
 * WelcomeFragmentViewModel is responsible for managing the state and events of the WelcomeFragment.
 * It holds the data, state, and event management for the fragment, including interaction with the ViewModel's logic
 * related to the ViewPager position, page data, and fragment UI stages.
 */
class WelcomeViewModel(
    private val preferences: PreferencesRepository,
    flavorService: FlavorService
) : BaseViewModel() {

    private val initialUiState by lazy {
        WelcomeFragmentState(
            data = flavorService.getWelcomeFragmentPagerData()
        )
    }

    private val stateManager = StateManager(initialUiState)
    val stateFlow get() = stateManager.stateFlow

    private val effectManager = EffectManager<WelcomeFragmentEffect>()
    val effectFlow get() = effectManager.effectFlow

    private val reducer = WelcomeFragmentReducer()

    //----------------------------------------------------------------------------------------------

    fun onEvent(event: WelcomeFragmentEvent) {
        reducer.reduce(stateManager.currentState(), event).handleResult(
            state = stateManager::update,
            effect = effectManager::trySend
        )
    }

    fun currentPagerPosition() = stateManager.currentState().pagerPos

    fun markFirstAccessCompleteOnPreferences() {
        viewModelScope.launch {
            preferences.setFirstAccessComplete()
        }
    }

}