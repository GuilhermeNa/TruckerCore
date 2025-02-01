package com.example.truckercore.modules.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting an [Admin] entity by its ID.
 *
 * The deletion operation involves checks such as verifying the user's permissions and ensuring
 * that the [Admin] entity exists before performing the deletion.
 *
 * @see Admin
 * @see Response
 */
interface DeleteAdminUseCase {

    /**
     * Executes the use case to delete an [Admin] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This is used to check if the user has the necessary permissions.
     * @param id The ID of the [Admin] entity to be deleted.
     * @return A [Flow] response:
     * - [Response.Success] if the admin was successfully deleted.
     * - [Response.Error] if there was any error during the deletion process.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Unit>>
}