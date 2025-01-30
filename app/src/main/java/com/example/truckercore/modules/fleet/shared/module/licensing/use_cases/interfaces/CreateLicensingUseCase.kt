package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for creating a new [Licensing] entity.
 *
 * It handles the logic required to insert a new entity into the system, such as validating data, applying business rules, and
 * interacting with the persistence layer (e.g., database).
 */
interface CreateLicensingUseCase {

    /**
     * Executes the use case to create a new [Licensing] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param licensing The [Licensing] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully created.
     * - [Response.Error] when the object creation fails.
     */
    suspend fun execute(user: User, licensing: Licensing): Flow<Response<String>>

}