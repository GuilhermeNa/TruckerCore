/*
package com.example.truckercore.model.modules._previous_sample.business_central.use_cases.interfaces

import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

*/
/**
 * Interface representing the use case for deleting a [BusinessCentral] entity by its ID.
 *
 * This interface defines the contract for a use case responsible for deleting a [BusinessCentral] entity from the system
 * based on its unique ID. The deletion operation might involve checks such as verifying the user's permissions and ensuring
 * that the entity is eligible for deletion.
 *//*

internal interface DeleteBusinessCentralUseCase {

    */
/**
     * Executes the use case to delete a [BusinessCentral] entity by its ID.
     *
     * This method encapsulates the logic for deleting a [BusinessCentral] entity from the system by its ID.
     * It ensures that the entity exists before attempting to delete it, checks user permissions, and proceeds with
     * the deletion if all conditions are met.
     *
     * @param user The [User] who is requesting the deletion. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [BusinessCentral] entity to be deleted. This ID is used to locate the entity in the system.
     * @return A [Flow] that emits a [Response.Success] when the entity is successfully deleted, or an error response if the operation fails.
     * @throws ObjectNotFoundException If the entity with the specified ID does not exist in the system.
     *//*

    suspend fun execute(user: User, id: String): Flow<Response<Unit>>

}*/
