package com.example.truckercore.layers.presentation.viewmodels.base.abstractions

import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase

class MainViewModel(
    private val isUserLogged: HasLoggedUserUseCase
): BaseViewModel() {

    fun hasLoggedUser(): Boolean {
        isUserLogged()
    }

}