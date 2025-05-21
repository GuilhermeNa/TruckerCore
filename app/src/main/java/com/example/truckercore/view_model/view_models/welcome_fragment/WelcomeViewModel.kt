package com.example.truckercore.view_model.view_models.welcome_fragment

import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.view_model._base.BaseViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeUiState.Stage
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeUiState.Success
import kotlinx.coroutines.flow.asStateFlow

private const val GENERIC_ERROR_MESSAGE =
    "Um erro ocorreu durante o carregamento de recursos de imagem ou texto."

/**
 * WelcomeFragmentViewModel is responsible for managing the state and events of the WelcomeFragment.
 * It holds the data, state, and event management for the fragment, including interaction with the ViewModel's logic
 * related to the ViewPager position, page data, and fragment UI stages.
 */
class WelcomeViewModel(
    private val flavorService: FlavorService
) : BaseViewModel() {

    // Fragment State --------------------------------------------------------------
    // MutableStateFlow that holds the current state of the fragment.
    private val stateManager = WelcomeUiStateManager(flavorService.getWelcomePagerData())
    val state get() = stateManager.state.asStateFlow()

    // ViewPager last position ------------------------------------------------------
    // Holds the last position of the ViewPager to manage UI state.
    private var _lastPagerPos: Int = 0
    val pagerPos get() = _lastPagerPos

    //----------------------------------------------------------------------------------------------
    // Initialization block to setup the initial fragment state based on the provided flavor.
    // If an error occurs, it updates the fragment state with an error message.
    //----------------------------------------------------------------------------------------------

    fun onEvent(event: WelcomeEvent) {
       if(event is WelcomeEvent.PagerChanged) {

       }
    }

    /**
     * Notifies the ViewModel that the ViewPager's position has changed.
     * This updates the UI stage based on the new position.
     */
    fun notifyPagerChanged(position: Int) {

        // Helper function to get the current fragment data
        fun getData() = (_fragmentState.value as Success).data

        // Helper function to determine the UI stage based on the position
        fun getActualStage(position: Int): Stage {
            val firstPage = 0
            val lastPage = getData().size - 1
            return when (position) {
                firstPage -> Stage.UserInFirsPage
                lastPage -> Stage.UserInLastPage
                else -> Stage.UserInIntermediatePages
            }
        }

        // Update the last position of the pager
        _lastPagerPos = position

        // Create a new Success state with updated pager position and stage
        val newState = Success(getData(), getActualStage(position))
        updateFragmentState(newState)
    }

    fun isInLastPage(): Boolean {

    }
}