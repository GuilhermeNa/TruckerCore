package com.example.truckercore.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.AggregateTruckWithDetailsUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.GetTruckUseCase
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

internal class AggregateTruckWithDetailsUseCaseImpl(
    private val getTruck: GetTruckUseCase,
    private val getTrailerWithDetails: AggregateTrailerWithDetailsUseCase,
    private val getLicensingWithFiles: AggregateLicensingWithFilesUseCase
) : AggregateTruckWithDetailsUseCase {

    override fun execute(documentParams: DocumentParameters) =
        getSingleTruckWithDetailsFLow(documentParams)

    private fun getSingleTruckWithDetailsFLow(documentParams: DocumentParameters) = combine(
        getTruck.execute(documentParams),
        getTrailerWithDetails.execute(getTrailerQueryParams(documentParams)),
        getLicensingWithFiles.execute(getLicensingQueryParams(documentParams))
    ) { truckResponse, trailerWithDetailsResponse, licensingWithFilesResponse ->
        if (truckResponse !is Response.Success) return@combine Response.Empty

        val truck = truckResponse.data

        val trailersWithDetails =
            if (trailerWithDetailsResponse is Response.Success) trailerWithDetailsResponse.data
            else emptyList()

        val licensingWithFiles =
            if (licensingWithFilesResponse is Response.Success) licensingWithFilesResponse.data
            else emptyList()

        Response.Success(
            TruckWithDetails(
                truck = truck,
                trailersWithDetails = trailersWithDetails,
                licensingWithFiles = licensingWithFiles
            )
        )
    }

    private fun getLicensingQueryParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    private fun getTrailerQueryParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.TRUCK_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters) =
        getListOfTruckWithDetailsFlow(queryParams)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getListOfTruckWithDetailsFlow(queryParams: QueryParameters) =
        getTruck.execute(queryParams).flatMapConcat { truckResponse ->
            if (truckResponse !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
            val truckList = truckResponse.data
            val truckIds = truckList.mapNotNull { it.id }

            combine(
                getTrailerWithDetails.execute(getTrailerQueryParams(queryParams, truckIds)),
                getLicensingWithFiles.execute(getLicensingQueryParams(queryParams, truckIds))
            ) { trailerWithDetailsResponse, licensingWithFilesResponse ->
                val trailersWithDetails =
                    if (trailerWithDetailsResponse is Response.Success) {
                        trailerWithDetailsResponse.data.groupBy { it.trailer.truckId }
                    } else emptyMap()

                val licensingWithFiles =
                    if (licensingWithFilesResponse is Response.Success) {
                        licensingWithFilesResponse.data.groupBy { it.licensing.parentId }
                    } else emptyMap()

                val result = truckList.map { truck ->
                    TruckWithDetails(
                        truck = truck,
                        trailersWithDetails = trailersWithDetails[truck.id] ?: emptyList(),
                        licensingWithFiles = licensingWithFiles[truck.id] ?: emptyList()
                    )
                }

                Response.Success(result)
            }

        }

    private fun getLicensingQueryParams(
        queryParams: QueryParameters,
        truckIds: List<String>
    ) = QueryParameters.create(queryParams.user)
        .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, truckIds))
        .setStream(queryParams.shouldStream)
        .build()

    private fun getTrailerQueryParams(
        queryParams: QueryParameters,
        truckIds: List<String>
    ) = QueryParameters.create(queryParams.user)
        .setQueries(QuerySettings(Field.TRUCK_ID, QueryType.WHERE_EQUALS, truckIds))
        .setStream(queryParams.shouldStream)
        .build()

}