package com.example.truckercore.model.modules.fleet.truck.repository

import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

internal interface TruckRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<AppResponse<TruckDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<AppResponse<List<TruckDto>>>

}