package com.example.truckercore.view_model.view_models.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.model.errors.AppExceptionOld
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.security.service.PermissionService
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.model.shared.utils.expressions.mapAppResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private typealias RegistrationCompletedEffect = SplashEffect.AlreadyAccessed.AuthenticatedUser.RegistrationCompleted
private typealias AwaitingRegistrationEffect = SplashEffect.AlreadyAccessed.AuthenticatedUser.AwaitingRegistration
private typealias LoginRequiredEffect = SplashEffect.AlreadyAccessed.RequireLogin
private typealias FirstAccessEffect = SplashEffect.FirstTimeAccess

private typealias OpenAnimConcludedEvent = SplashEvent.UiEvent.FirstAnimComplete
private typealias InfoLoadedEvent = SplashEvent.SystemEvent.UserInfoLoaded
private typealias LoadedAnimConcludedEvent = SplashEvent.UiEvent.SecondAnimComplete

private typealias LoadingState = SplashUiState.Loading
private typealias LoadedState = SplashUiState.Loaded

class SplashViewModel(
    private val authService: AuthManager,
    private val preferences: PreferencesRepository,
    private val permissionService: PermissionService,
    private val flavorService: FlavorService
) : ViewModel() {

    private val appFlavor = flavorService.getFlavor()

    // State
    private var _uiState: MutableStateFlow<SplashUiState> =
        MutableStateFlow(SplashUiState.Initial(appFlavor))
    val uiState get() = _uiState.asStateFlow()

    // Effect
    private val _effect = MutableSharedFlow<SplashEffect>(replay = 1)
    val effect get() = _effect.asSharedFlow()
    private var effectHolder: SplashEffect? = null

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: SplashEvent) {
        when (event) {
            is OpenAnimConcludedEvent -> {
                setState(LoadingState(appFlavor))
                loadUserInfo()
            }

            is InfoLoadedEvent -> setState(LoadedState(appFlavor))

            is LoadedAnimConcludedEvent -> effectHolder?.let { setEffect(it) }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            try {
                effectHolder = when {
                    isFirstAccess() -> FirstAccessEffect(appFlavor)
                    !activeSession() -> LoginRequiredEffect
                    registerIncomplete() -> AwaitingRegistrationEffect
                    registerComplete() -> RegistrationCompletedEffect
                    else -> throw NotImplementedError("")
                }
                onEvent(InfoLoadedEvent)

            } catch (e: Exception) {
                if (e is AppExceptionOld) setEffect(SplashEffect.Error(e.errorCode))
                else throw UnknownError("An unknown error occurred while loading user info.")
            }
        }
    }

    private fun registerComplete(): Boolean {
        TODO("Not yet implemented")
    }

    private fun registerIncomplete(): Boolean {
        TODO("Not yet implemented")
    }

    private suspend fun isFirstAccess(): Boolean {
        return preferences.isFirstAccess()
    }

    private fun activeSession(): Boolean {
        val result = authService.thereIsLoggedUser()
        return result.mapAppResult(
            onSuccess = { it },
            onError = { throw it }
        )
    }

    private fun setState(newState: SplashUiState) {
        _uiState.value = newState
    }

    private fun setEffect(newEffect: SplashEffect) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

}