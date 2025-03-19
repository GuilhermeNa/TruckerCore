package com.example.truckercore.business_admin.view_model.login.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.view_model.SplashFragState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BaSplashFragmentViewModel(
    private val authService: AuthService
) : ViewModel() {

    private var _fragState: MutableStateFlow<SplashFragState> =
        MutableStateFlow(SplashFragState.Initial)
    val fragState get() = _fragState.asStateFlow()

    fun searchForLoggedUser() {
        viewModelScope.launch {
            authService.thereIsLoggedUser()

        }
    }


}