package com.example.truckercore.view_model.view_models.continue_register

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.expressions.logEvent
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.modules.user.manager.UserManager
import com.example.truckercore.view_model._shared._base.view_model.LoggerViewModel
import com.example.truckercore.view_model.view_models.continue_register.effect.ContinueRegisterEffectManager
import com.example.truckercore.view_model.view_models.continue_register.event.ContinueRegisterEvent
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterState
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterStateManager
import com.example.truckercore.view_model.view_models.continue_register.use_case.ContinueRegisterViewUseCase

/**
 * ViewModel responsible for managing the state of the Continue Register screen.
 *
 * It interacts with [AuthManager] to fetch the current user's email and verification status,
 * and with [UserManager] to check whether a user with the given email already exists.
 *
 * The ViewModel exposes a [StateFlow] of [ContinueRegisterState] to represent
 * different UI states: loading, success with data, or error.
 *
 * When initialized, it automatically attempts to load the required UI data.
 *
 * @property authService Provides authentication-related operations.
 * @property userService Provides user-related operations.
 */
class ContinueRegisterViewModel(
    private val continueRegisterViewUseCase: ContinueRegisterViewUseCase
) : LoggerViewModel() {

    private val stateManager = ContinueRegisterStateManager()
    val stateFlow = stateManager.stateFlow

    private val effectManager = ContinueRegisterEffectManager()
    val effectFlow = effectManager.effectFlow

    fun initialize() {
        onEvent(ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Executing)
    }

    fun onEvent(newEvent: ContinueRegisterEvent) {
        logEvent(this@ContinueRegisterViewModel, newEvent)
        when (newEvent) {
            is ContinueRegisterEvent.UiEvent -> handleUiEvent(newEvent)
            is ContinueRegisterEvent.SystemEvent -> handleSystemEvent(newEvent)
        }
    }

    private fun handleUiEvent(newEvent: ContinueRegisterEvent.UiEvent) {
        when (newEvent) {
            ContinueRegisterEvent.UiEvent.Click.FinishRegisterButton -> {

            }

            ContinueRegisterEvent.UiEvent.Click.NewRegisterButton ->
                effectManager.setNavigateToCreateEmailEffect()

        }
    }

    private fun handleSystemEvent(newEvent: ContinueRegisterEvent.SystemEvent) {
        when (newEvent) {
            ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Executing -> checkUserRegisterStatus()

            ContinueRegisterEvent.SystemEvent.CheckRegisterTask.Success -> {

            }

            ContinueRegisterEvent.SystemEvent.CheckRegisterTask.CriticalError -> {

            }

            is ContinueRegisterEvent.SystemEvent.CheckRegisterTask.RecoverableError -> {

            }
        }
    }

    private fun checkUserRegisterStatus() {

    }

    /**
     * Loads the required UI data by:
     * - Fetching the current user's email.
     * - Checking if the email is verified.
     * - Determining if a user with this email already exists.
     *
     * Updates the UI state accordingly.
     */
    private fun loadUiData() {
        /* viewModelScope.launch {
             val email = authService.getUserEmail().mapAppResponse(
                 onSuccess = { it },
                 onError = {
                     _uiState.value = ContinueRegisterUiState.Error
                     return@launch
                 }
             )

             val verifiedStatus = getVerifiedStatus()
             val userExists = getNameStatus(email)

             _uiState.value = ContinueRegisterUiState.Success(
                 ContinueRegisterUiModel(
                     email = email.value,
                     verified = verifiedStatus,
                     userExists = userExists
                 )
             )
         }*/
    }

    /*   private fun getVerifiedStatus() = authService.isEmailVerified()*/
    private suspend fun getNameStatus(email: Email) = false

    /* userService.hasUserWithEmail(email).isSuccess*/

    /**
     * Clears the currently signed-in user.
     * Typically used when user data is invalid or needs to be reset.
     */
    fun clearCurrentUser() {
        //  authService.signOut()
    }

}