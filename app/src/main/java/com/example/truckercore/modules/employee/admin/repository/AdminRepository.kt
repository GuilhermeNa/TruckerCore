package com.example.truckercore.modules.employee.admin.repository

import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing [AdminDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [AdminDto] objects. It typically includes
 * operations such as creating, reading, updating, and deleting data. Implementations
 * of this interface are responsible for defining the actual interaction with the data
 * storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see AdminDto
 */
internal interface AdminRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<AdminDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<AdminDto>>>

}