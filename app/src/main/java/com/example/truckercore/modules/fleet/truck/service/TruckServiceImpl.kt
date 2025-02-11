package com.example.truckercore.modules.fleet.truck.service

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.truck.configs.TruckFetchConfig
import com.example.truckercore.modules.fleet.truck.configs.TruckWithDetails
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.GetTruckByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class TruckServiceImpl(
    private val getTruck: GetTruckByIdUseCase,
    private val getTrailers: GetTrailerByTruckIdUseCase,
    private val getLicensing: GetLicensingUseCase
) : TruckService {

    override suspend fun getTruck(user: User, truckId: String) = getTruck.execute(user, truckId)

    override suspend fun getTruckWithDetails(
        user: User,
        truckId: String,
        details: TruckFetchConfig
    ): Flow<Response<TruckWithDetails>> = flow {
        val parentIds = mutableListOf(truckId)
        val trailers = mutableListOf<Trailer>()
        val licensing = mutableListOf<Licensing>()

        val truckResponse = getTruck.execute(user, truckId).single()

        if (truckResponse is Response.Empty) {
            emit(handleTruckEmpty(truckId))
            return@flow
        }

        if (truckResponse is Response.Error) {
            emit(handleTruckError(truckResponse, truckId))
            return@flow
        }

        val truck = (truckResponse as Response.Success).data

        if (details.shouldAddTrailers) {
            trailers.addAll(fetchTrailers(user, truckId))
            trailers.mapNotNull { it.id }.let { parentIds.addAll(it) }
        }
        if (details.shouldAddLicensing) {
            licensing.addAll(fetchLicensing(user, parentIds))
        }

        emit(Response.Success(TruckWithDetails(truck, trailers, licensing)))

    }.catch { t ->
        emit(Response.Error(t as Exception))
    }

    private fun handleTruckError(truckResponse: Response.Error, truckId: String): Response.Error {
        logError(
            context = javaClass,
            exception = truckResponse.exception,
            message = "Error while fetching truck with id: $truckId."
        )
        return Response.Error(truckResponse.exception)
    }

    private fun handleTruckEmpty(truckId: String): Response.Empty {
        logInfo(
            context = javaClass,
            message = "No truck found for id: $truckId."
        )
        return Response.Empty
    }

    private suspend fun fetchTrailers(user: User, truckId: String): List<Trailer> =
        when (val response = getTrailers.execute(user, truckId).single()) {
            is Response.Success -> response.data
            is Response.Empty -> handleTrailersEmpty(truckId)
            is Response.Error -> handleTrailersError(response, truckId)
        }

    private fun handleTrailersError(response: Response.Error, truckId: String): Nothing {
        logError(
            context = javaClass,
            exception = response.exception,
            message = "Error while fetching trailers with truckId: $truckId."
        )
        throw response.exception
    }

    private fun handleTrailersEmpty(truckId: String): List<Trailer> {
        logInfo(
            context = javaClass,
            message = "No trailers found for truckId: $truckId."
        )
        return emptyList()
    }

    private suspend fun fetchLicensing(user: User, parentIds: List<String>): List<Licensing> = TODO()
    /*    when (val response = getLicensing.execute(user, *parentIds.toTypedArray()).single()) {
            is Response.Success -> response.data
            is Response.Empty -> handleLicensingEmpty(parentIds)
            is Response.Error -> handleLicensingError(response, parentIds)
        }*/

    private fun handleLicensingError(response: Response.Error, parentIds: List<String>): Nothing {
        logError(
            context = javaClass,
            exception = response.exception,
            message = "Error while fetching licensing with parentIds: $parentIds."
        )
        throw response.exception
    }

    private fun handleLicensingEmpty(parentIds: List<String>): List<Licensing> {
        logInfo(
            context = javaClass,
            message = "No licensing found for parentIds: $parentIds."
        )
        return emptyList()
    }

}