package com.example.truckercore.model.modules.business_central.repository

import com.example.truckercore.model.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing `BusinessCentralDto` data.
 *
 * This interface extends the `Repository` interface, providing methods to interact
 * with the underlying data source for `BusinessCentralDto` objects. It typically
 * includes operations such as creating, reading, updating, and deleting `BusinessCentralDto` data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see BusinessCentralDto
 */
internal interface BusinessCentralRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<AppResponse<BusinessCentralDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<AppResponse<List<BusinessCentralDto>>>

}