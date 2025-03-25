package com.example.truckercore.view_model.states

import com.example.truckercore.view_model.enums.ErrorType
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

/**
 * Represents the different states of the WelcomeFragment.
 * This sealed class is used to manage and represent the state of the fragment at different points
 * in its lifecycle or UI interactions. It can represent an initial state, a successful state with data,
 * or an error state.
 */
sealed class WelcomeFragState {

    /**
     * Represents the initial state of the fragment.
     * Typically used when the fragment is being created or initialized.
     */
    data object Initial : WelcomeFragState()

    /**
     * Represents the success state of the fragment.
     * It contains the data to be displayed (a list of `WelcomePagerData`) and the current UI stage
     * (e.g., the current page or fragment view).
     */
    data class Success(
        val data: List<WelcomePagerData>,
        val uiStage: Stage
    ): WelcomeFragState()

    /**
     * Enum class that defines the different stages of the fragment's UI.
     * This is used to track the user's position in the ViewPager and adjust the UI accordingly.
     */
    enum class Stage {
        UserInFirsPage,
        UserInIntermediatePages,
        UserInLastPage
    }

    /**
     * Represents the error state of the fragment.
     * It contains an `ErrorType` that indicates the type of error and a message providing further details.
     */
    data class Error(
        val type: ErrorType,
        val message: String
    ): WelcomeFragState()

}