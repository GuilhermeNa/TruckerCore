package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [Trailer] entity.
 * The implementation of this use case should provide the logic for updating an existing trailer
 * in the system, ensuring the validity of the data and checking the necessary permissions.
 */
internal interface UpdateTrailerUseCase {

    /**
     * Executes the use case to update a [Trailer] entity.
     *
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param trailer The [Trailer] entity that is being updated. This contains the data to be modified.
     *
     * @return A [Flow] of
     * - [Response.Success] when the trailer is successfully updated.
     * - [Response.Empty] if the trailer was not found or could not be updated.
     *
     * @throws NullPointerException If the `Trailer.id` is null when attempting to update the trailer.
     * @throws ObjectNotFoundException If the trailer with the given ID does not exist in the system.
     */
    fun execute(user: User, trailer: Trailer): Flow<Response<Unit>>

}