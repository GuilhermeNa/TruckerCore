package com.example.truckercore.business_admin.view_model.login.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.example.truckercore.view_model.SplashFragState
import com.example.truckercore.view_model.SplashFragState.Error
import com.example.truckercore.view_model.SplashFragState.UserLoggedIn
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

    private fun updateFragmentState(newState: SplashFragState) {
        _fragmentState.value = newState
    }

    //----------------------------------------------------------------------------------------------

    fun initialize() {
        viewModelScope.launch {
            when (isFirstAccess()) {
                true -> handleFirstAccess()
                false -> handleDefaultAccess()
            }
        }
    }

    private suspend fun isFirstAccess(): Boolean =
        PreferenceDataStore
            .getInstance()
            .getIsFirstAppAccess(application.applicationContext)

    private suspend fun handleFirstAccess() {
        updateFragmentState(SplashFragState.FirstAccess)

        PreferenceDataStore
            .getInstance()
            .setIsFirstAppAccess(application.applicationContext, true)

    }

    private suspend fun handleDefaultAccess() {
        when (authService.thereIsLoggedUser()) {
            true -> handleLoggedUser()
            false -> handleUserNotFound()
        }
    }

    private suspend fun handleLoggedUser() {
        authService.getSessionInfo().single().let { response ->
            when (response) {
                is Response.Success -> updateFragmentState(hasSystemAccess(response.data))
                is Response.Empty -> updateFragmentState(UserLoggedIn.ProfileIncomplete)
                is Response.Error -> updateFragmentState(Error(response.exception))
            }
        }
    }

    private fun hasSystemAccess(sessionInfo: SessionInfo): UserLoggedIn {
        val user = sessionInfo.user
        val central = sessionInfo.central
        return if (permissionService.canAccessSystem(user, central)) {
            UserLoggedIn.SystemAccessAllowed
        } else {
            UserLoggedIn.SystemAccessDenied
        }
    }

    private fun handleUserNotFound() {
        updateFragmentState(SplashFragState.UserNotFound)
    }

    fun getErrorTitle() = "Erro de inicialização"

    fun getErrorMessage() = "Houve alguma falha no carregamento de dados, entre em contato com o " +
            "distribuidor do App para mais informações."

}