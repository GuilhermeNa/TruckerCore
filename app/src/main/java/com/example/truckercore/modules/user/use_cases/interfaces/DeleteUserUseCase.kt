package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting a [User] entity by its ID.
 */
interface DeleteUserUseCase {

    /**
     * Executes the use case to delete a [User] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [User] entity to be deleted.
     * @return A [Flow] of:
     * - [Response.Success] when the user is successfully deleted.
     */
    fun execute(user: User, id: String): Flow<Response<Unit>>

}