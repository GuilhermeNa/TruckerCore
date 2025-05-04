package com.example.truckercore.view_model.view_models.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.configs.build.FlavorService
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.security.service.PermissionService
import com.example.truckercore.model.modules.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private typealias FirstAnimEvent = SplashEvent.UiEvent.FirstAnimComplete
private typealias SecondAnimEvent = SplashEvent.UiEvent.SecondAnimComplete

class SplashViewModel(
    private val authService: AuthService,
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
    private val _effect = MutableSharedFlow<SplashEffect>()
    val effect get() = _effect.asSharedFlow()

    //----------------------------------------------------------------------------------------------
    fun onEvent(event: SplashEvent) {
        when(event) {
            is FirstAnimEvent -> {
                setState(Loading)
                loadData()
            }
            is SecondAnimEvent -> TODO()


            SplashEvent.SystemEvent.EnterSystem -> TODO()
            SplashEvent.SystemEvent.FinishRegistration -> TODO()
            SplashEvent.SystemEvent.LoginRequired -> TODO()
            SplashEvent.SystemEvent.UserInFirstAccess -> TODO()
        }
    }

    fun run() {
        viewModelScope.launch {
            updateFragmentState(SplashEffect.FirstAccess)
            when (isFirstAccess()) {
                true -> handleFirstAccess()
                false -> handleDefaultAccess()
            }
        }
    }

    private suspend fun isFirstAccess(): Boolean {

    }

    private suspend fun handleFirstAccess() {
        updateFragmentState(SplashEffect.FirstAccess)

    }

    private suspend fun handleDefaultAccess() {
        authService.thereIsLoggedUser().let { result ->
            when (result) {
                is AppResult.Success -> {
                    if (result.data) handleLoggedUser()
                    else handleUserNotFound()
                }

                else -> {}
            }
        }
    }

    private suspend fun handleLoggedUser() {
        /*        authService.getSessionInfo().single().let { response ->
                    when (response) {
                        is AppResponse.Success -> updateFragmentState(hasSystemAccess(response.data))
                        is AppResponse.Empty -> updateFragmentState(UserLoggedIn.ProfileIncomplete)
                        is AppResponse.Error -> updateFragmentState(Error(response.exception))
                    }
                }*/
    }
    /*
        private fun hasSystemAccess(sessionInfo: SessionInfo): UserLoggedIn {
            val user = sessionInfo.user
            val central = sessionInfo.central
            return if (permissionService.canAccessSystem(user, central)) {
                UserLoggedIn.SystemAccessAllowed
            } else {
                UserLoggedIn.SystemAccessDenied
            }
        }*/

    private fun handleUserNotFound() {
        updateFragmentState(SplashEffect.UserNotFound)
    }

    fun getErrorTitle() = "Erro de inicialização"

    fun getErrorMessage() = "Houve alguma falha no carregamento de dados, entre em contato com o " +
            "distribuidor do App para mais informações."

    private fun updateFragmentState(newState: SplashEffect) {
        _uiState.value = newState
    }

}