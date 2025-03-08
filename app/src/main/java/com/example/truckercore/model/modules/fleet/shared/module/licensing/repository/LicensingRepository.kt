package com.example.truckercore.model.modules.fleet.shared.module.licensing.repository

import com.example.truckercore.model.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing [LicensingDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [LicensingDto] objects. It typically includes
 * operations such as creating, reading, updating, and deleting data. Implementations
 * of this interface are responsible for defining the actual interaction with the data
 * storage (e.g., database, external service, etc.).
 *
 * @see Repository
 */
internal interface LicensingRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<LicensingDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<LicensingDto>>>

}