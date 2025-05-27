package com.example.truckercore.model.modules.fleet.truck.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore._utils.expressions.getOrElse
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.fleet.truck.TruckSpec
import com.example.truckercore.model.modules.fleet.truck.data.Truck
import com.example.truckercore.model.modules.fleet.truck.mapper.TruckMapper

class GetTruckUseCaseImpl(
    private val dataRepository: DataRepository
) : GetTruckUseCase {

    override suspend fun invoke(spec: TruckSpec): AppResponse<Truck> {
        return runCatching { getTruck(spec) }
            .getOrElse { handleError(spec, it) }
    }

    private suspend fun getTruck(spec: TruckSpec): AppResponse<Truck> {
        val response = dataRepository.findOneBy(spec)
        val dto = response.getOrElse { return@getTruck it }
        val truck = TruckMapper.toEntity(dto)
        return AppResponse.Success(truck)
    }

    private fun handleError(
        spec: TruckSpec,
        e: Throwable
    ): AppResponse.Error {
        val message = "An unknown error occurred while searching a Truck with spec: $spec."
        val domainException = DomainException.Unknown(message, e)
        AppLogger.e(getClassName(), message, e)
        return AppResponse.Error(domainException)
    }

}