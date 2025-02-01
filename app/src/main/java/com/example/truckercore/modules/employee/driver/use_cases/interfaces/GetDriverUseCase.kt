package com.example.truckercore.modules.employee.driver.use_cases.interfaces

import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [Driver] entity by its ID.
 */
interface GetDriverUseCase {

    /**
     * Executes the use case to retrieve a [Driver] entity by its ID.
     *
     * @param user The [User] making the request. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [Driver] entity to be retrieved.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully found.
     * - [Response.Error] when any error occurs.
     * - [Response.Empty] when the data was not found.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Driver>>

}