package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [User] by its ID.
 *
 * @see User
 * @see Response
 */
internal interface GetUserUseCase {

    fun execute(userId: String, shouldStream: Boolean): Flow<Response<User>>

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