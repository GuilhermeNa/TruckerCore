package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [User] entity.
 *
 * This interface defines the contract for a use case that is responsible for updating a User entity.
 * It encapsulates the logic for updating the entity, which might include validation, business logic, and persistence operations.
 * The operation is asynchronous and uses Kotlin's [suspend] function to allow it to be executed in a coroutine.
 */
interface UpdateUserUseCase {

    /**
     * Executes the use case to update a [User] entity.
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param userToUpdate The [User] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] of [Response] that contains the result of the operation.
     */
    suspend fun execute(user: User, userToUpdate: User): Flow<Response<Unit>>

}