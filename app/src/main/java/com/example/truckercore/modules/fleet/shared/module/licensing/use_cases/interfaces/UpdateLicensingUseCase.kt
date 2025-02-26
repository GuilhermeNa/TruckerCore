package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [Licensing] entity.
 */
internal interface UpdateLicensingUseCase {

    /**
     * Executes the use case to update a [Licensing] entity.
     *
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param licensing The [Licensing] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] containing [Response.Success] when the [Licensing] entity is successfully updated.
     *
     * @throws NullPointerException if the [Licensing] entity's ID is null.
     * @throws ObjectNotFoundException if the [Licensing] entity does not exist.
     */
    fun execute(user: User, licensing: Licensing): Flow<Response<Unit>>

}