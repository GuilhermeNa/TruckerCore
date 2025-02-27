package com.example.truckercore.infrastructure.security.authentication.use_cases

import com.example.truckercore.infrastructure.security.authentication.entity.LoggedUserDetails
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface GetLoggedUserDetailsUseCase {

    fun execute(firebaseUid: String): Flow<Response<LoggedUserDetails>>

}