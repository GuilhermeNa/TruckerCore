package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [Trailer] entity by its ID.
 */
internal interface GetTrailerUseCase {

    fun execute(documentParams: DocumentParameters): Flow<Response<Trailer>>

    fun execute(queryParams: QueryParameters): Flow<Response<List<Trailer>>>

}