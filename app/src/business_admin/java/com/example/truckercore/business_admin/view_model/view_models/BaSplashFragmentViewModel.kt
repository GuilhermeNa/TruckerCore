package com.example.truckercore.business_admin.view_model.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.modules.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.example.truckercore.view_model.states.SplashFragState
import com.example.truckercore.view_model.states.SplashFragState.Error
import com.example.truckercore.view_model.states.SplashFragState.UserLoggedIn
import com.example.truckercore.view_model.datastore.PreferenceDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class BaSplashFragmentViewModel(
    private val authService: AuthService,
    private val permissionService: PermissionService,
    private val application: Application
) : AndroidViewModel(application) {

    private var _fragmentState: MutableStateFlow<SplashFragState> =
        MutableStateFlow(SplashFragState.Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    //----------------------------------------------------------------------------------------------

    fun run() {
        viewModelScope.launch {
            updateFragmentState(SplashFragState.FirstAccess)
            when (isFirstAccess()) {
                true -> handleFirstAccess()
                false -> handleDefaultAccess()
            }
        }
    }

    private suspend fun isFirstAccess(): Boolean =
        PreferenceDataStore
            .getInstance()
            .getFirstAccessStatus(application.applicationContext)

    private suspend fun handleFirstAccess() {
        updateFragmentState(SplashFragState.FirstAccess)

        PreferenceDataStore
            .getInstance()
            .setAppAlreadyAccessed(application.applicationContext)

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
        updateFragmentState(SplashFragState.UserNotFound)
    }

    fun getErrorTitle() = "Erro de inicialização"

    fun getErrorMessage() = "Houve alguma falha no carregamento de dados, entre em contato com o " +
            "distribuidor do App para mais informações."

    private fun updateFragmentState(newState: SplashFragState) {
        _fragmentState.value = newState
    }


}