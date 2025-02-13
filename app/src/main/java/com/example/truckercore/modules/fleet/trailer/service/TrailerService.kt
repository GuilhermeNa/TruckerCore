package com.example.truckercore.modules.fleet.trailer.service

import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface TrailerService {

    fun fetchTrailer(documentParam: DocumentParameters): Flow<Response<Trailer>>

    fun fetchTrailerList(queryParam: QueryParameters): Flow<Response<List<Trailer>>>

    fun fetchTrailerWithDetails(documentParam: DocumentParameters): Flow<Response<TrailerWithDetails>>

    fun fetchTrailerWithDetailsList(queryParam: QueryParameters): Flow<Response<List<TrailerWithDetails>>>

}