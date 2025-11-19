package com.example.truckercore.layers.presentation.nav_login.view_model.welcome_fragment.state

import com.example.truckercore.core.my_lib.ui_components.FabComponent
import com.example.truckercore.layers.presentation.base.contracts.State
import com.example.truckercore.core.my_lib.ui_components.Visibility
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.data.WelcomePagerData

/**
 * Represents the UI state for a WelcomeFragment,
 * which likely displays a series of welcome or onboarding pages (pager).
 *
 * @property pagerPos The current position (index) of the pager.
 * @property fab The current Floating Action Button (FAB) state.
 * @property data The list of data items representing each page in the pager.
 */
data class WelcomeFragmentState(
    val pagerPos: Int,
    val fab: FabComponent,
    val data: List<WelcomePagerData>
) : State {

    // The last valid index position in the pager (0-based index)
    private val lastPos: Int get() = (data.size - 1)

    /**
     * Checks if the pager is currently showing the last page.
     * @return true if the current page is the last one, false otherwise.
     */
    fun isLastPage() = pagerPos == lastPos

    /**
     * Checks if the pager is currently showing the first page.
     * @return true if the current page is the first one, false otherwise.
     */
    fun isFirstPage() = pagerPos == INITIAL_PAGER_POS

    /**
     * Returns a new state with the pager updated to a new position.
     * @param newPos The new pager position to set.
     * @return A copy of the state with the updated pager position.
     */
    fun updatePage(newPos: Int): WelcomeFragmentState =
        copy(pagerPos = newPos, fab = updateFabComponent(newPos))

    /**
     * Moves the pager one page forward, unless it’s already on the last page.
     * @return The updated state after pagination forward.
     *         If already at the last page, returns the current state unchanged.
     */
    fun paginateForward(): WelcomeFragmentState {
        if (isLastPage()) return this

        val newPos = pagerPos + 1
        return copy(pagerPos = newPos, fab = updateFabComponent(newPos))
    }

    /**
     * Moves the pager one page backward, unless it’s already on the first page.
     * @return The updated state after pagination backward.
     *         If already at the first page, returns the current state unchanged.
     */
    fun paginateBack(): WelcomeFragmentState {
        if (isFirstPage()) return this

        val newPos = pagerPos - 1
        return copy(pagerPos = newPos, fab = updateFabComponent(newPos))

    }

    /**
     * Determines the correct FAB component based on the pager position.
     * @param pagerPosition The pager position to evaluate.
     * @return A new [FabComponent] instance with visibility depending on the pager position.
     */
    private fun updateFabComponent(pagerPosition: Int): FabComponent {
        return if (pagerPosition == INITIAL_PAGER_POS) {
            FabComponent(visibility = Visibility.INVISIBLE)
        } else {
            FabComponent(visibility = Visibility.VISIBLE)
        }
    }

    companion object {
        // The initial position of the Pager
        const val INITIAL_PAGER_POS = 0
    }

}