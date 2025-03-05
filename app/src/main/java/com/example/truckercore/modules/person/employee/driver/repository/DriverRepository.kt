package com.example.truckercore.modules.person.employee.driver.repository

import com.example.truckercore.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing [DriverDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [DriverDto] objects. It typically
 * includes operations such as creating, reading, updating, and deleting data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see DriverDto
 */
internal interface DriverRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<DriverDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<DriverDto>>>

}