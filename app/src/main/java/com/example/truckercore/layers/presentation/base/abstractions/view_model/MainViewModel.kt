package com.example.truckercore.layers.presentation.base.abstractions.view_model

import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase

class MainViewModel(
    private val isUserLogged: HasLoggedUserUseCase
): BaseViewModel() {

/*    fun hasLoggedUser(): Boolean {
       return isUserLogged()
    }*/

}