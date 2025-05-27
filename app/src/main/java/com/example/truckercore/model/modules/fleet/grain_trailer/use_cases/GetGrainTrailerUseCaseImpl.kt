package com.example.truckercore.model.modules.fleet.grain_trailer.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore._utils.expressions.getOrElse
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailer
import com.example.truckercore.model.modules.fleet.grain_trailer.mapper.GrainTrailerMapper
import com.example.truckercore.model.modules.fleet.grain_trailer.specification.GrainTrailerSpec

class GetGrainTrailerUseCaseImpl(
    private val dataRepository: DataRepository
) : GetGrainTrailerUseCase {

    override suspend fun invoke(spec: GrainTrailerSpec): AppResponse<GrainTrailer> {
        return runCatching { getGrainTrailer(spec) }
            .getOrElse { handleError(spec, it) }
    }

    private suspend fun getGrainTrailer(spec: GrainTrailerSpec): AppResponse<GrainTrailer> {
        val response = dataRepository.findOneBy(spec)
        val dto = response.getOrElse { return@getGrainTrailer it }
        val grainTrailer = GrainTrailerMapper.toEntity(dto)
        return AppResponse.Success(grainTrailer)
    }

    private fun handleError(spec: GrainTrailerSpec, e: Throwable): AppResponse.Error {
        val message = "An unknown error occurred while searching a GrainTrailer with spec: $spec."
        val domainException = DomainException.Unknown(message, e)
        AppLogger.e(getClassName(), message, e)
        return AppResponse.Error(domainException)
    }

}