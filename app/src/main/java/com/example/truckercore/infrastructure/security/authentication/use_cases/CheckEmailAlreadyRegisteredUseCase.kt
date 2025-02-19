package com.example.truckercore.infrastructure.security.authentication.use_cases

import com.example.truckercore.shared.utils.sealeds.Response

internal interface CheckEmailAlreadyRegisteredUseCase {

    fun execute(email: String): Response<Unit>

}