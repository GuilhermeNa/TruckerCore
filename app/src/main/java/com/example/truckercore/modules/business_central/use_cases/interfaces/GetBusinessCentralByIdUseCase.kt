package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface GetBusinessCentralByIdUseCase {

    suspend fun execute(user: User, id: String): Flow<Response<BusinessCentral>>

}