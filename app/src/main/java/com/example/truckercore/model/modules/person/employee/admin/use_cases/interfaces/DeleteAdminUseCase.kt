package com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting an [Admin] entity by its ID.
 *
 * The deletion operation involves checks such as verifying the user's permissions and ensuring
 * that the [Admin] entity exists before performing the deletion.
 *
 * @see Admin
 * @see AppResponse
 */
internal interface DeleteAdminUseCase {

    /**
     * Executes the use case to delete an [Admin] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This is used to check if the user has the necessary permissions.
     * @param id The ID of the [Admin] entity to be deleted.
     * @return A [Flow] of [AppResponse.Success] if the data was successfully deleted.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    fun execute(user: User, id: String): Flow<AppResponse<Unit>>

}