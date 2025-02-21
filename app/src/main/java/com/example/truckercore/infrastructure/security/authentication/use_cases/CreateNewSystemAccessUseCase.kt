package com.example.truckercore.infrastructure.security.authentication.use_cases

import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface CreateNewSystemAccessUseCase {

    fun execute(requirements: NewAccessRequirements): Flow<Response<Unit>>

}