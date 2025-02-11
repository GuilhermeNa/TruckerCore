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
     * Executes the use case to update a [Driver] entity.
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param driver The [Driver] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] of [Response.Success] if the update was successful.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    suspend fun execute(user: User, driver: Driver): Flow<Response<Unit>>

}