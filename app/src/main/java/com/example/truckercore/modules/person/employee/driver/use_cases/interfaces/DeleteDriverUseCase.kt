package com.example.truckercore.modules.person.employee.driver.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting a [Driver] entity by its ID.
 *
 * The deletion operation might involve checks such as verifying the user's permissions and ensuring
 * that the entity is eligible for deletion.
 */
internal interface DeleteDriverUseCase {

    /**
     * Executes the use case to delete a [Driver] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [Driver] entity to be deleted.
     * @return A [Flow] of [Response.Success] if the data was successfully deleted.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    fun execute(user: User, id: String): Flow<Response<Unit>>

}