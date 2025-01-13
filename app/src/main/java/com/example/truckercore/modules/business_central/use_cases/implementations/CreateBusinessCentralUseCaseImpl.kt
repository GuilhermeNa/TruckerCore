package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.shared.services.ValidatorService
import javax.inject.Named

internal class CreateBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val validator: ValidatorService,
    private val mapper: BusinessCentralMapper
) : CreateBusinessCentralUseCase {

    override suspend fun execute(entity: BusinessCentral): String {
        validator.validateForCreation(entity)
        val dto = mapper.toDto(entity)
        TODO()
     //   return repository.create(dto)
    }

}