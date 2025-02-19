package com.example.truckercore.infrastructure.security.authentication.repository

import com.example.truckercore.infrastructure.security.authentication.entity.UserRegistration
import com.example.truckercore.shared.utils.sealeds.Response

internal interface AuthenticationRepository {

    fun registerSingleUser(userReg: UserRegistration): Response<Unit>

}