package com.example.truckercore.view_model.view_models.continue_register

import androidx.lifecycle.ViewModel
import com.example.truckercore._shared.classes.Email
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.modules.user.manager.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel responsible for managing the state of the Continue Register screen.
 *
 * It interacts with [AuthManager] to fetch the current user's email and verification status,
 * and with [UserManager] to check whether a user with the given email already exists.
 *
 * The ViewModel exposes a [StateFlow] of [ContinueRegisterUiState] to represent
 * different UI states: loading, success with data, or error.
 *
 * When initialized, it automatically attempts to load the required UI data.
 *
 * @property authService Provides authentication-related operations.
 * @property userService Provides user-related operations.
 */
class ContinueRegisterViewModel(
    private val authService: AuthManager
    //private val userService: UserService
) : ViewModel() {

    private val _uiState: MutableStateFlow<ContinueRegisterUiState> =
        MutableStateFlow(ContinueRegisterUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    init {
        loadUiData()
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

    private fun getVerifiedStatus() = authService.isEmailVerified()

    private suspend fun getNameStatus(email: Email) = false
    /* userService.hasUserWithEmail(email).isSuccess*/

    /**
     * Clears the currently signed-in user.
     * Typically used when user data is invalid or needs to be reset.
     */
    fun clearCurrentUser() {
        authService.signOut()
    }

}