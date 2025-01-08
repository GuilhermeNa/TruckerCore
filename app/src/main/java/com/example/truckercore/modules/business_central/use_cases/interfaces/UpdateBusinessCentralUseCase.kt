package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.user.entity.User

internal interface UpdateBusinessCentralUseCase {

    suspend fun execute(user: User, dto: BusinessCentralDto)

}