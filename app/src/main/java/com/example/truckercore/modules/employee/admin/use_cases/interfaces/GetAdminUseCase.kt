package com.example.truckercore.modules.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving an [Admin] entity by its ID.
 *
 * This use case involves fetching the [Admin] from the repository using the provided ID,
 * checking the necessary permissions, and ensuring the [Admin] exists in the system.
 *
 * @see Admin
 * @see Response
 */
interface GetAdminUseCase {

    /**
     * Executes the use case to retrieve an [Admin] entity by its ID.
     *
     * @param user The [User] who is requesting the retrieval. This is used to check if the user has the necessary permissions.
     * @param id The ID of the [Admin] entity to be fetched.
     *
     * @return A [Flow] of [Response<Admin>] that will emit:
     * - [Response.Success] if the admin was found and retrieved.
     * - [Response.Error] if there was any error during the retrieval process.
     * - [Response.Empty] if the admin with the provided ID does not exist.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Admin>>

}