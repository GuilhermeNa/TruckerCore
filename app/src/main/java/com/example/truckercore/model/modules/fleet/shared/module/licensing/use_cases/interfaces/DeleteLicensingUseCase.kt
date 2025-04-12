package com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting a [Licensing] entity by its ID.
 *
 * The deletion operation might involve checks such as verifying the user's permissions and ensuring
 * that the entity is eligible for deletion.
 */
internal interface DeleteLicensingUseCase {

    /**
     * Executes the use case to delete a [Licensing] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [Licensing] entity to be deleted.
     * @return A [Flow] containing [AppResponse.Success] when the [Licensing] entity is successfully deleted.
     *
     * @throws ObjectNotFoundException if the [Licensing] entity does not exist.
     */
    fun execute(user: User, id: String): Flow<AppResponse<Unit>>

}