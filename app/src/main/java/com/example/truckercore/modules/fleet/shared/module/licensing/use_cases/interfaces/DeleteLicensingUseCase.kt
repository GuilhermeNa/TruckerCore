package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting a [Licensing] entity by its ID.
 *
 * The deletion operation might involve checks such as verifying the user's permissions and ensuring
 * that the entity is eligible for deletion.
 */
interface DeleteLicensingUseCase {

    /**
     * Executes the use case to delete a [Licensing] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [Licensing] entity to be deleted.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully deleted.
     * - [Response.Error] when the object deletion fails.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Unit>>

}