package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface GetBusinessCentralByIdUseCase: BusinessCentralUseCase {

    suspend fun execute(user: User, id: String): Flow<Response<BusinessCentralDto>>

}