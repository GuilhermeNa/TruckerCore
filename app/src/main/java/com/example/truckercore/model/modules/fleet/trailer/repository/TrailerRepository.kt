package com.example.truckercore.model.modules.fleet.trailer.repository

import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface TrailerRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<TrailerDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<TrailerDto>>>

}