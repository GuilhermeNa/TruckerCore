package com.example.truckercore.view_model.view_models.continue_register

import com.example.truckercore._utils.classes.contracts.UiState

/**
 * Represents the UI state for the Continue Register screen.
 *
 * This sealed class defines all possible states that the UI can be in:
 *
 * - [Loading]: Indicates that the data is currently being loaded.
 * - [Success]: Contains the [ContinueRegisterUiModel] with the data to be displayed.
 * - [Error]: Indicates that an error occurred while loading the data.
 *
 * Used to drive UI rendering in a reactive and type-safe way.
 */
sealed class ContinueRegisterUiState : UiState {

    /** The initial or in-progress state when data is being loaded. */
    data object Loading : ContinueRegisterUiState()

    /** The successful state containing the UI model with all necessary data. */
    data class Success(val data: ContinueRegisterUiModel) : ContinueRegisterUiState()

    /** The error state indicating that data loading failed. */
    data object Error : ContinueRegisterUiState()

}