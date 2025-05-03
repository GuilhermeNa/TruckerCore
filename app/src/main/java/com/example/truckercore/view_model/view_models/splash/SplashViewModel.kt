package com.example.truckercore.view_model.view_models.splash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.infrastructure.security.service.PermissionService
import com.example.truckercore.model.modules.authentication.service.AuthService
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authService: AuthService,
    private val preferences: PreferencesRepository,
    private val permissionService: PermissionService,
    private val application: Application
) : ViewModel() {

    private var _fragmentState: MutableStateFlow<SplashUiState> =
        MutableStateFlow(SplashUiState.Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    //----------------------------------------------------------------------------------------------
    // Fluxo do ViewModel
    //
    // Primeiro acesso -> navega p/ WelcomeFrag
    //
    // Segundo acesso(+):
    //      !KeepLogged -> navega p/ Login
    //       KeepLogged ->
    //          Cadastro pendente -> navega p/ ContinueFrag
    //          Cadastro valido -> entra sistema
    //
    //

    fun run() {
        viewModelScope.launch {
            updateFragmentState(SplashUiState.FirstAccess)
            when (isFirstAccess()) {
                true -> handleFirstAccess()
                false -> handleDefaultAccess()
            }
        }
    }

    private suspend fun isFirstAccess(): Boolean {

    }

    private suspend fun handleFirstAccess() {
        updateFragmentState(SplashUiState.FirstAccess)

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
        updateFragmentState(SplashUiState.UserNotFound)
    }

    fun getErrorTitle() = "Erro de inicialização"

    fun getErrorMessage() = "Houve alguma falha no carregamento de dados, entre em contato com o " +
            "distribuidor do App para mais informações."

    private fun updateFragmentState(newState: SplashUiState) {
        _fragmentState.value = newState
    }


}