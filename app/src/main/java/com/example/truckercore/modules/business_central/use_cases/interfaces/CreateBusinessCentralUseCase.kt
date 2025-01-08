package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto

internal interface CreateBusinessCentralUseCase: BusinessCentralUseCase {

    fun execute(dto: BusinessCentralDto): String

}