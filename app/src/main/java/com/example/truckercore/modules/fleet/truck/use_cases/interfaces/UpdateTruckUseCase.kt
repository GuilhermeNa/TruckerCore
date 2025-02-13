package com.example.truckercore.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [Truck] entity.
 */
internal interface UpdateTruckUseCase {

    /**
     * Executes the use case to update a [Truck] entity.
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param truck The [Truck] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] of [Response.Success] when the object is successfully updated.
     */
    fun execute(user: User, truck: Truck): Flow<Response<Unit>>

}