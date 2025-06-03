package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import kotlinx.coroutines.flow.asStateFlow

/**
 * WelcomeFragmentViewModel is responsible for managing the state and events of the WelcomeFragment.
 * It holds the data, state, and event management for the fragment, including interaction with the ViewModel's logic
 * related to the ViewPager position, page data, and fragment UI stages.
 */
class WelcomeViewModel(flavorService: FlavorService) : LoggerViewModel() {

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

}