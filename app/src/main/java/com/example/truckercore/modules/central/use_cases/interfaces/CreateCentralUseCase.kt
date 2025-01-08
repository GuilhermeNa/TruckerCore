package com.example.truckercore.modules.central.use_cases.interfaces

import com.example.truckercore.modules.central.dto.CentralDto

internal interface CreateCentralUseCase: CentralUseCase {

    fun execute(dto: CentralDto): String

}