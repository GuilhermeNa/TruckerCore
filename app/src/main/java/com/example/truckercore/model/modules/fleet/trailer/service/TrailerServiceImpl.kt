package com.example.truckercore.model.modules.fleet.trailer.service

import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class TrailerServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getTrailer: GetTrailerUseCase,
    private val getTrailerWithDetails: AggregateTrailerWithDetailsUseCase
) : Service(exceptionHandler), TrailerService {

    override fun fetchTrailer(
        documentParam: DocumentParameters
    ): Flow<Response<Trailer>> =
        runSafe { getTrailer.execute(documentParam) }

    override fun fetchTrailerList(
        queryParam: QueryParameters
    ): Flow<Response<List<Trailer>>> =
        runSafe { getTrailer.execute(queryParam) }

    override fun fetchTrailerWithDetails(
        documentParam: DocumentParameters
    ): Flow<Response<TrailerWithDetails>> =
        runSafe { getTrailerWithDetails.execute(documentParam) }

    override fun fetchTrailerWithDetailsList(
        queryParam: QueryParameters
    ): Flow<Response<List<TrailerWithDetails>>> =
        runSafe { getTrailerWithDetails.execute(queryParam) }

}