package com.example.truckercore.modules.employee.driver.use_cases.interfaces

import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [Driver] entity.
 */
internal interface UpdateDriverUseCase {

    /**
     * Executes the use case to update an existing [Driver] entity.
     *
     * This function updates the provided driver entity, checks whether the driver exists, validates permissions,
     * and applies the update to the repository.
     *
     * @param user The [User] who is performing the update. This is used to check the user's permissions
     *             and possibly track the update.
     * @param driver The [Driver] entity that needs to be updated. This entity contains the new data to be saved.
     * @return A [Flow] of [Response.Success] if the update was successful.
     * @throws ObjectNotFoundException if the [Driver] to be updated does not exist.
     * @throws NullPointerException if the [Driver] has a `null` ID while attempting to update.
     */
    suspend fun execute(user: User, driver: Driver): Flow<Response<Unit>>

}