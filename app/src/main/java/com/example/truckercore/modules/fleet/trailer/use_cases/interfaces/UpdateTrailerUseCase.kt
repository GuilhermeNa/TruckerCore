package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [Trailer] entity.
 */
interface UpdateTrailerUseCase {

    /**
     * Executes the use case to update a [Trailer] entity.
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param trailer The [Trailer] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully updated.
     * - [Response.Error] when the object update fails.
     */
    suspend fun execute(user: User, trailer: Trailer): Flow<Response<Unit>>

}