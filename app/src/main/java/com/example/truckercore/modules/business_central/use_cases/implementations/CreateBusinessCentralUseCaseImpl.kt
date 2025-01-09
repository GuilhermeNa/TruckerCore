package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase

internal class CreateBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository
) : CreateBusinessCentralUseCase {
    // TODO(Nao precisa enviar o usuário. a central será criada no inicio do App enquando não existe usuário)
    override fun execute(dto: BusinessCentralDto): String {
        CentralCreationValidator.execute(dto)
        return repository.create(dto)
    }

}