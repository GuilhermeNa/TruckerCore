package com.example.truckercore.modules.central.use_cases.implementations

import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.modules.central.repository.CentralRepository
import com.example.truckercore.modules.central.use_cases.interfaces.DeleteCentralUseCase
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal class DeleteCentralUseCaseImpl(override val repository: CentralRepository) :
    DeleteCentralUseCase {

    override fun execute(id: String): Flow<Response<CentralDto>> {
        TODO("Todos os filhos devem ser removidos")
    }

}