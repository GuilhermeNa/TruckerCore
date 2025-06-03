package com.example.truckercore.model.modules.fleet.dump.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.fleet.dump.data.Dump
import com.example.truckercore.model.modules.fleet.dump.mapper.DumpMapper
import com.example.truckercore.model.modules.fleet.dump.specification.DumpSpec

class GetDumpUseCaseImpl(
    private val dataRepository: DataRepository
) : GetDumpUseCase {

    override suspend fun invoke(spec: DumpSpec): AppResponse<Dump> =
        runCatching { getDump(spec) }
            .getOrElse { handleError(spec, it) }

    private suspend fun getDump(spec: DumpSpec): AppResponse<Dump> {
        val response = dataRepository.findOneBy(spec)
        val dto = response.getOrElse { return@getDump it }
        val dump = DumpMapper.toEntity(dto)
        return AppResponse.Success(dump)
    }

    private fun handleError(spec: DumpSpec, e: Throwable): AppResponse.Error {
        val message = "An unknown error occurred while searching a Dump with spec: $spec."
        val domainException = DomainException.Unknown(message, e)
        AppLogger.e(getClassName(), message, e)
        return AppResponse.Error(domainException)
    }

}