package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore._utils.expressions.getName
import com.example.truckercore._utils.expressions.launch
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.security.service.PermissionService
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._base.BaseViewModel
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel(
    private val authService: AuthManager,
    private val preferences: PreferencesRepository,
    private val permissionService: PermissionService
) : BaseViewModel() {

    // State
    private val stateManager = SplashUiStateManager()
    val uiState get() = stateManager.uiState.asStateFlow()

    private val effectManager = SplashEffectManager()
    val effect get() = effectManager.effect.asSharedFlow()

    init {
        onEvent(SplashEvent.UiEvent.Initialized)
    }
    //----------------------------------------------------------------------------------------------
    fun onEvent(event: SplashEvent) {
        AppLogger.d(getName(), "Event: $event")

        when (event) {
            SplashEvent.UiEvent.Initialized -> {
                launch { effectManager.setTransitionToLoading() }
            }

            SplashEvent.UiEvent.TransitionToLoadingComplete -> {
                stateManager.setLoadingState()
             //   loadUserInfo()
            }

            SplashEvent.UiEvent.TransitionToNavigation -> {
               // stateManager.setLoadedState()
            }

            SplashEvent.SystemEvent.UserInfoLoaded -> {
             //   stateManager.
            }

           // is LoadedAnimConcludedEvent -> effectHolder?.let { setEffect(it) }

        }
    }

/*
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
                //  if (e is AppExceptionOld) setEffect(SplashEffect.Error(e.errorCode))
                //  else throw UnknownError("An unknown error occurred while loading user info.")
            }
        }
    }
*/

    private fun registerComplete(): Boolean {
        TODO("Not yet implemented")
    }

    private fun registerIncomplete(): Boolean {
        TODO("Not yet implemented")
    }

    private suspend fun isFirstAccess(): Boolean {
        return preferences.isFirstAccess()
    }

    private fun activeSession() = authService.thereIsLoggedUser()

    /*    private fun setEffect(newEffect: SplashEffect) {
            viewModelScope.launch {
                _effect.emit(newEffect)
            }
        }*/

}