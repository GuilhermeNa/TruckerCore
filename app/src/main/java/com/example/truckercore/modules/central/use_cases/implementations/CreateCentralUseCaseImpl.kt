package com.example.truckercore.modules.central.use_cases.implementations

import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.modules.central.repository.CentralRepository
import com.example.truckercore.modules.central.use_cases.interfaces.CreateCentralUseCase
import com.example.truckercore.modules.central.validator.CentralCreationValidator

internal class CreateCentralUseCaseImpl(override val repository: CentralRepository) :
    CreateCentralUseCase {

    override fun execute(dto: CentralDto): String {
        CentralCreationValidator.execute(dto)
        return repository.create(dto)
    }

}