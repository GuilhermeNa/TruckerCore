package com.example.truckercore.model.modules.user.use_cases.interfaces

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [User] entities.
 * This use case provides methods to fetch a single user or a list of users, either by ID or based on query parameters.
 * It supports fetching users in a streaming manner if necessary.
 *
 * @see User
 * @see Response
 */
internal interface GetUserUseCase {

    /**
     * Fetches a single [User] record based on the provided user ID.
     * This method retrieves a user by their unique identifier and optionally supports streaming.
     *
     * @param firebaseUid The ID of the user to fetch.
     * @param shouldStream Boolean flag indicating whether the response should be streamed.
     * @return A [Flow] containing:
     * - [Response.Success] containing the [User] object if the user record was found.
     * - [Response.Empty] if no user exists with the given ID.
     */
    fun execute(firebaseUid: String, shouldStream: Boolean = false): Flow<Response<User>>

    /**
     * Fetches a single user record based on the provided ID.
     * This method retrieves the user details as a [User] object.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [User] object if the user record was found.
     * - [Response.Empty] if no user exists with the given ID.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<User>>

    /**
     * Fetches a list of user records based on the provided query settings.
     * This method allows filtering the [User] records using a list of query settings.
     *
     * @param queryParams The query parameters to filter the user records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [User] objects that match the query.
     * - [Response.Empty] if no user records match the query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<User>>>

}