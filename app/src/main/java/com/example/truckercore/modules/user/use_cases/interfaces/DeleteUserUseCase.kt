package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting a [User] entity by its ID.
 *
 * This interface defines the contract for a use case responsible for deleting a User entity from the system
 * based on its unique ID. The deletion operation might involve checks such as verifying the user's permissions and ensuring
 * that the entity is eligible for deletion.
 */
interface DeleteUserUseCase {

    /**
     * Executes the use case to delete a [User] entity by its ID.
     *
     * @param user The User who is requesting the deletion. This parameter might be used for permission checks or tracking.
     * @param id The ID of the User entity to be deleted.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Unit>>

}