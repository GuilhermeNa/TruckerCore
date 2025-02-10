package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [User] entity.
 */
internal interface UpdateUserUseCase {

    /**
     * Executes the use case to update a [User] entity.
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param userToUpdate The [User] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully updated.
     */
    fun execute(user: User, userToUpdate: User): Flow<Response<Unit>>

}