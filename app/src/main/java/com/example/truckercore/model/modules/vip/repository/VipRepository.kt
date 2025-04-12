package com.example.truckercore.model.modules.vip.repository

import com.example.truckercore.model.modules.vip.dto.VipDto
import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing VIP data.
 * Extends the `Repository` interface and provides methods to fetch VIP data
 * based on different parameters, such as document or query parameters.
 */
internal interface VipRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<VipDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<VipDto>>>

}