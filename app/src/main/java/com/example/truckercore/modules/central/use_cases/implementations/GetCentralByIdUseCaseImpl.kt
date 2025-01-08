package com.example.truckercore.modules.central.use_cases.implementations

import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.modules.central.repository.CentralRepository
import com.example.truckercore.modules.central.use_cases.interfaces.GetCentralByIdUseCase
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal class GetCentralByIdUseCaseImpl(override val repository: CentralRepository) :
    GetCentralByIdUseCase {

    override suspend fun execute(id: String): Flow<Response<CentralDto>> {

        repository.fetchById(id)
    }

}