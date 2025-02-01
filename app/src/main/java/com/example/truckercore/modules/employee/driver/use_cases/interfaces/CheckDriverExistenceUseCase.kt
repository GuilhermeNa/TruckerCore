package com.example.truckercore.modules.employee.driver.use_cases.interfaces

import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for checking the existence of a [Driver] entity by its ID.
 */
interface CheckDriverExistenceUseCase {

    /**
     * Executes the use case to check if a [Driver] entity exists by its ID.
     *
     * @param user The [User] who is making the request. This parameter may be used for permission checks or to track the request.
     * @param id The ID of the [Driver] entity to check for existence.
     * @return A [Flow] of:
     * - [Response.Success] when the object exists.
     * - [Response.Empty] when the object does not exist.
     * - [Response.Error] when any error occurs.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Unit>>

}
