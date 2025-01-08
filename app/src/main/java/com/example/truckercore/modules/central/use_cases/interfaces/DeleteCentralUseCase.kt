package com.example.truckercore.modules.central.use_cases.interfaces

import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface DeleteCentralUseCase: CentralUseCase {

    fun execute(id: String): Flow<Response<CentralDto>>

}