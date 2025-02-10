package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.aggregations.UserWithPerson
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.Person
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [User] records along with their associated [Person].
 *
 * @see User
 * @see Person
 * @see Response
 */
internal interface AggregateUserWithPersonUseCase {

    /**
     * Fetches a single user record along with its associated person based on the provided ID.
     * This method retrieves the user details as a [UserWithPerson] object, which includes the
     * [User] data and the corresponding [Person] object.
     *
     * @param userId The id of the user to be retrieved.
     * @param shouldStream If the flow should keep observing the data.
     * @return A [Flow] of:
     * - [Response.Success] containing the [UserWithPerson] object if the user record and person were found.
     * - [Response.Empty] if no user record exists with the given ID or no person is associated with it.
     */
    fun execute(userId: String, shouldStream: Boolean): Flow<Response<UserWithPerson>>

}