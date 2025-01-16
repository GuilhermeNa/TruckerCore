package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper
) : CreateBusinessCentralUseCase {

    override suspend fun execute(entity: BusinessCentral): Flow<Response<String>> = flow {
        validatorService.validateForCreation(entity)
        val dto = mapper.toDto(entity)
        emit(repository.create(dto).single())
    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}