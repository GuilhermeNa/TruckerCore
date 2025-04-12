package com.example.truckercore.model.modules.user.use_cases.interfaces

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for creating a new [User] entity.
 */
interface CreateUserUseCase {

    /**
     * Executes the use case to create a new [User] entity.
     *
     * @param user The User who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param newUser The User entity to be created. This object contains the data to be stored in the system.
     * @return A [Response] string representing the unique identifier of the newly created User entity.
     */
    fun execute(user: User, newUser: User): Flow<Response<String>>

}