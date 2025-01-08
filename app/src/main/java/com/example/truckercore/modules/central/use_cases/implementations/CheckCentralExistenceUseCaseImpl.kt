package com.example.truckercore.modules.central.use_cases.implementations

import com.example.truckercore.modules.central.repository.CentralRepository
import com.example.truckercore.modules.central.use_cases.interfaces.CheckCentralExistenceUseCase
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal class CheckCentralExistenceUseCaseImpl(override val repository: CentralRepository) :
    CheckCentralExistenceUseCase {

    override suspend fun execute(id: String): Flow<Response<Boolean>> {
        repository.entityExists(id)
        TODO()
    }

}