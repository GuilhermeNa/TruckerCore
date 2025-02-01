package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for checking the existence of a [User] entity by its ID.
 *
 * This interface defines the contract for a use case that checks whether a User entity with the provided ID
 * exists in the system. The operation is asynchronous and returns a [Flow] of [Response] that will indicate whether the
 * entity exists or not.
 */
internal interface CheckUserExistenceUseCase {

    /**
     * Executes the use case to check if a [User] entity exists by its ID.
     *
     * @param user The User who is making the request. This parameter may be used for permission checks or to track the request.
     * @param id The ID of the User entity to check for existence.
     * @return A [Flow] of [Response] that contains a `Boolean` value indicating whether the entity exists (`true`) or not (`false`).
     */
    suspend fun execute(user: User, id: String): Flow<Response<Unit>>

}