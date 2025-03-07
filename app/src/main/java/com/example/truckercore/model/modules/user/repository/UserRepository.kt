package com.example.truckercore.model.modules.user.repository

import com.example.truckercore.modules.user.dto.UserDto
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
internal interface UserRepository : Repository {

    /**
     * Retrieves the data of the logged-in user based on their unique identifier (ID).
     *
     * This method allows fetching the details of the currently authenticated user in the system. It
     * can return the response either synchronously or asynchronously, depending on the value of
     * the `shouldStream` parameter. If `shouldStream` is true, the response will be streamed, allowing
     * continuous updates. Otherwise, it returns the user data in a single response.
     *
     * @param userId The unique identifier (ID) of the logged-in user.
     * @param shouldStream A flag indicating whether the response should be streamed. If true, the response
     *                     will be streamed; if false, it will be a single response.
     * @return A [Flow] of:
     * - [Response.Success] containing the [UserDto] data for the logged-in user.
     * - [Response.Empty] if the user was not found.
     */
    fun fetchLoggedUser(userId: String, shouldStream: Boolean): Flow<Response<UserDto>>

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<UserDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<UserDto>>>

}