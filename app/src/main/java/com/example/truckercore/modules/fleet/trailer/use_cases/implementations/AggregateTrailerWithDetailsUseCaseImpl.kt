package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class AggregateTrailerWithDetailsUseCaseImpl(
    private val getTrailer: GetTrailerUseCase,
    private val getLicensingWithFiles: AggregateLicensingWithFilesUseCase
) : AggregateTrailerWithDetailsUseCase {

    override fun execute(documentParams: DocumentParameters) = combine(
        getTrailer.execute(documentParams),
        getLicensingWithFiles.execute(getDocumentParams(documentParams))
    ) { trailerResponse, licensingWithFilesResponse ->

        if (trailerResponse !is Response.Success) return@combine Response.Empty
        val trailer = trailerResponse.data

        val licensingWithFiles =
            if (licensingWithFilesResponse is Response.Success) licensingWithFilesResponse.data
            else emptyList()

        Response.Success(TrailerWithDetails(trailer, licensingWithFiles))
    }

    private fun getDocumentParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(queryParams: QueryParameters) =
        getTrailer.execute(queryParams).flatMapConcat { trailerResponse ->
            if (trailerResponse !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
            val trailerList = trailerResponse.data

            getLicensingWithFiles.execute(
                getLicensingQueryParams(queryParams, trailerList.mapNotNull { it.id })
            ).map { licensingWithFilesResponse ->
                val licensingWithFilesMap =
                    if (licensingWithFilesResponse is Response.Success) {
                        licensingWithFilesResponse.data.groupBy { it.licensing.parentId }
                    } else emptyMap()

                val result = trailerList.map { trailer ->
                    TrailerWithDetails(
                        trailer = trailer,
                        licensingWithFiles = licensingWithFilesMap[trailer.id] ?: emptyList()
                    )
                }

                Response.Success(result)
            }

        }

    private fun getLicensingQueryParams(queryParams: QueryParameters, trailerIds: List<String>) =
        QueryParameters.create(queryParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, trailerIds))
            .setStream(queryParams.shouldStream)
            .build()

}