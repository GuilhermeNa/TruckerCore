package com.example.truckercore.layers.presentation.base.contracts

import com.example.truckercore.layers.data.base.outcome.expressions.get
import com.example.truckercore.layers.domain.use_case.authentication.HasLoggedUserUseCase

interface SecureFragment {

    val isUserLoggedUseCase: HasLoggedUserUseCase

    fun doIfUserNotFound(action: () -> Unit) {
        if (!isUserLoggedUseCase().get()) action()
    }

}