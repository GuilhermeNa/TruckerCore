package com.example.truckercore.modules.fleet.truck.repository

import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.shared.interfaces.NewRepository
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface TruckRepository : NewRepository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<TruckDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<TruckDto>>>

}