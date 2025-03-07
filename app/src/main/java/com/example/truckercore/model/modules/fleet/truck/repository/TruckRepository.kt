package com.example.truckercore.model.modules.fleet.truck.repository

import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface TruckRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<TruckDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<TruckDto>>>

}