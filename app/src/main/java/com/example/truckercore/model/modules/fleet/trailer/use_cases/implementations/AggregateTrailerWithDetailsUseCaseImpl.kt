package com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.model.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
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
        getLicensingWithFiles.execute(getLicensingWithFilesQueryParams(documentParams))
    ) { trailerResponse, licensingWithFilesResponse ->
        if (trailerResponse !is AppResponse.Success) return@combine AppResponse.Empty

        val trailer = trailerResponse.data
        val licensingWithFiles = licensingWithFilesResponse.extractList()
        val result = TrailerWithDetails(trailer, licensingWithFiles)

        AppResponse.Success(result)
    }

    private fun AppResponse<List<LicensingWithFile>>.extractList() =
        if (this is AppResponse.Success) this.data
        else emptyList()

    private fun getLicensingWithFilesQueryParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(queryParams: QueryParameters) =
        getTrailer.execute(queryParams).flatMapConcat { trailerResponse ->
            if (trailerResponse !is AppResponse.Success) return@flatMapConcat flowOf(AppResponse.Empty)

            val trailerList = trailerResponse.data
            val licensingQueryParams = getLicensingQueryParams(queryParams, trailerList)

            getLicensingWithFiles.execute(licensingQueryParams).map { licensingWithFilesResponse ->
                val licensingWithFilesMap = licensingWithFilesResponse.groupByParentId()
                val result = getResult(trailerList, licensingWithFilesMap)
                AppResponse.Success(result)
            }
        }

    private fun getResult(
        trailerList: List<Trailer>,
        licensingWithFilesMap: Map<String, List<LicensingWithFile>>
    ): List<TrailerWithDetails> = trailerList.map { trailer ->
        TrailerWithDetails(
            trailer = trailer,
            licensingWithFiles = licensingWithFilesMap[trailer.id] ?: emptyList()
        )
    }

    private fun AppResponse<List<LicensingWithFile>>.groupByParentId(): Map<String, List<LicensingWithFile>> =
        if (this is AppResponse.Success) this.data.groupBy { it.licensing.parentId }
        else emptyMap()

    private fun getLicensingQueryParams(
        queryParams: QueryParameters,
        trailers: List<Trailer>
    ): QueryParameters {
        val trailerIds = trailers.mapNotNull { it.id }
        return QueryParameters.create(queryParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, trailerIds))
            .setStream(queryParams.shouldStream)
            .build()
    }

}