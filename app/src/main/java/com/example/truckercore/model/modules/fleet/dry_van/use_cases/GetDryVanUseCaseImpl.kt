package com.example.truckercore.model.modules.fleet.dry_van.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVan
import com.example.truckercore.model.modules.fleet.dry_van.mapper.DryVanMapper
import com.example.truckercore.model.modules.fleet.dry_van.specification.DryVanSpec

class GetDryVanUseCaseImpl(
    private val dataRepository: DataRepository
) : GetDryVanUseCase {

    override suspend fun invoke(spec: DryVanSpec): AppResponse<DryVan> =
        runCatching { getDryVan(spec) }
            .getOrElse { handleError(spec, it) }

    private suspend fun getDryVan(spec: DryVanSpec): AppResponse<DryVan> {
        val response = dataRepository.findOneBy(spec)
        val dto = response.getOrElse { return@getDryVan it }
        val truck = DryVanMapper.toEntity(dto)
        return AppResponse.Success(truck)
    }

    private fun handleError(
        spec: DryVanSpec,
        e: Throwable
    ): AppResponse.Failure {
        val message = "An unknown error occurred while searching a DryVan with spec: $spec."
        val domainException = DomainException.Unknown(message, e)
        AppLogger.e(getClassName(), message, e)
        return AppResponse.Failure(domainException)
    }

}