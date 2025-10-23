package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment

import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.expressions.logEvent
import com.example.truckercore.data.infrastructure.repository.preferences.contracts.PreferencesRepository
import com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * WelcomeFragmentViewModel is responsible for managing the state and events of the WelcomeFragment.
 * It holds the data, state, and event management for the fragment, including interaction with the ViewModel's logic
 * related to the ViewPager position, page data, and fragment UI stages.
 */
class WelcomeViewModel(
    private val preferences: PreferencesRepository,
    flavorService: com.example.truckercore.core.config.flavor.FlavorService
) : com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel() {

    private val stateManager = WelcomeUiStateManager(flavorService.getWelcomePagerData())
    val state get() = stateManager.state.asStateFlow()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: WelcomeEvent) {
        logEvent(this@WelcomeViewModel, event)

        if (event is WelcomeEvent.PagerChanged) {
            stateManager.updatePagerPosition(event.position)
        }
    }

    fun isLastPage() = stateManager.isLastPage()

    fun pagerPos() = stateManager.pagerPos()

    fun setAppAlreadyAccessed() {
        viewModelScope.launch {
            preferences.setFirstAccessComplete()
        }
    }

}