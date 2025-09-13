package com.example.truckercore.model.modules.aggregation.transport_unit.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore._shared.expressions.getOrNull
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnit
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.aggregation.transport_unit.factory.TransportUnitFactory
import com.example.truckercore.model.modules.fleet.dolly.data.Dolly
import com.example.truckercore.model.modules.fleet.dolly.specification.DollySpec
import com.example.truckercore.model.modules.fleet.dolly.use_cases.GetDollyUseCase
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVan
import com.example.truckercore.model.modules.fleet.dry_van.specification.DryVanSpec
import com.example.truckercore.model.modules.fleet.dry_van.use_cases.GetDryVanUseCase
import com.example.truckercore.model.modules.fleet.dump.data.Dump
import com.example.truckercore.model.modules.fleet.dump.specification.DumpSpec
import com.example.truckercore.model.modules.fleet.dump.use_cases.GetDumpUseCase
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailer
import com.example.truckercore.model.modules.fleet.grain_trailer.specification.GrainTrailerSpec
import com.example.truckercore.model.modules.fleet.grain_trailer.use_cases.GetGrainTrailerUseCase
import com.example.truckercore.model.modules.fleet.truck.TruckSpec
import com.example.truckercore.model.modules.fleet.truck.data.Truck
import com.example.truckercore.model.modules.fleet.truck.use_cases.GetTruckUseCase

class GetTransportUnitUseCaseImpl(
    private val getTruckUseCase: GetTruckUseCase,
    private val getGrainTrailerUseCase: GetGrainTrailerUseCase,
    private val getDryVanUseCase: GetDryVanUseCase,
    private val getDumpUseCase: GetDumpUseCase,
    private val getDollyUseCase: GetDollyUseCase
) : GetTransportUnitUseCase {

    companion object {
        private const val ERROR_MESSAGE =
            "An error occurred while searching for a Transport Unit with ID:"
    }

    override suspend fun invoke(transportUnitId: TransportUnitID): AppResponse<TransportUnit> {
        return try {
            val truck = getTruck(transportUnitId) ?: return AppResponse.Empty

            val transportUnit = TransportUnitFactory(
                transportUnitId = transportUnitId,
                truck = truck,
                grainTrailer = getGrainTrailer(transportUnitId),
                dryVan = getDryVan(transportUnitId),
                dump = getDump(transportUnitId),
                dolly = getDolly(transportUnitId)
            )

            AppResponse.Success(transportUnit)

        } catch (e: Exception) {
            val message = "$ERROR_MESSAGE $transportUnitId"
            AppLogger.e(getClassName(), message)
            val error = DomainException.Unknown(message, e)
            AppResponse.Failure(error)
        }
    }

    private suspend fun getTruck(transportUnitId: TransportUnitID): Truck? {
        val spec = TruckSpec(transportUnitId = transportUnitId)
        val response = getTruckUseCase(spec)
        return response.getOrNull()
    }

    private suspend fun getDolly(transportUnitId: TransportUnitID): Dolly? {
        val spec = DollySpec(transportUnitId = transportUnitId)
        val response = getDollyUseCase(spec)
        return response.getOrNull()
    }

    private suspend fun getDryVan(transportUnitId: TransportUnitID): DryVan? {
        val spec = DryVanSpec(transportUnitId = transportUnitId)
        val response = getDryVanUseCase(spec)
        return response.getOrNull()
    }

    private suspend fun getDump(transportUnitId: TransportUnitID): Dump? {
        val spec = DumpSpec(transportUnitId = transportUnitId)
        val response = getDumpUseCase(spec)
        return response.getOrNull()
    }

    private suspend fun getGrainTrailer(transportUnitId: TransportUnitID): GrainTrailer? {
        val spec = GrainTrailerSpec(transportUnitId = transportUnitId)
        val response = getGrainTrailerUseCase(spec)
        return response.getOrNull()
    }

}