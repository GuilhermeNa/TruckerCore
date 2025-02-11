package com.example.truckercore.modules.user.service

import com.example.truckercore.modules.user.aggregations.UserWithPerson
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the User Service, responsible for communicating with the backend
 * to fetch and manage user data. This service acts as an intermediary layer that
 * allows applications to interact with the backend for user records.
 *
 * @see UserDto
 * @see Response
 */
interface UserService {

    /**
     * Retrieves the details of the logged-in user along with associated person information.
     *
     * @param userId The unique identifier of the logged-in user.
     * @param shouldStream A flag indicating whether the response should be streamed. If true,
     *                     the response will be streamed to receive real-time updates; if false,
     *                     it will return a single response.
     * @return A [Flow] of [Response] containing the [UserWithPerson] data, which includes both
     *         the user's information and associated person details.
     */
    fun fetchLoggedUserWithPerson(
        userId: String,
        shouldStream: Boolean
    ): Flow<Response<UserWithPerson>>

}