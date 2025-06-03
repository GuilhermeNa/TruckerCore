package com.example.truckercore.model.modules.fleet.dolly.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.fleet.dolly.data.Dolly
import com.example.truckercore.model.modules.fleet.dolly.mapper.DollyMapper
import com.example.truckercore.model.modules.fleet.dolly.specification.DollySpec

class GetDollyUseCaseImpl(
    private val dataRepository: DataRepository
): GetDollyUseCase {

    override suspend fun invoke(spec: DollySpec): AppResponse<Dolly> =
        runCatching { getDolly(spec) }
            .getOrElse { handleError(spec, it) }

    private suspend fun getDolly(spec: DollySpec): AppResponse<Dolly> {
        val response = dataRepository.findOneBy(spec)
        val dto = response.getOrElse { return@getDolly it }
        val truck = DollyMapper.toEntity(dto)
        return AppResponse.Success(truck)
    }

    private fun handleError(spec: DollySpec, e: Throwable): AppResponse.Error {
        val message = "An unknown error occurred while searching a Dolly with spec: $spec."
        val domainException = DomainException.Unknown(message, e)
        AppLogger.e(getClassName(), message, e)
        return AppResponse.Error(domainException)
    }

}