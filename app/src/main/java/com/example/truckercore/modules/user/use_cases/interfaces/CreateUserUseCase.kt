package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for creating a new [User] entity.
 *
 * This interface defines the contract for a use case responsible for creating a new User entity. It handles
 * the logic required to insert a new entity into the system, such as validating data, applying business rules, and
 * interacting with the persistence layer (e.g., database).
 */
interface CreateUserUseCase {

    /**
     * Executes the use case to create a new [User] entity.
     *
     * @param user The User who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param newUser The User entity to be created. This object contains the data to be stored in the system.
     * @return A [Response] string representing the unique identifier of the newly created User entity.
     */
    suspend fun execute(user: User, newUser: User): Flow<Response<String>>

}