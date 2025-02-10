package com.example.truckercore.modules.user.repository

import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.shared.interfaces.NewRepository
import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing [UserDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [UserDto] objects. It typically
 * includes operations such as creating, reading, updating, and deleting UserDto data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 */
internal interface UserRepository : NewRepository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<UserDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<UserDto>>>

}