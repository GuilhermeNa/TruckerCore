package com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [Truck] entity.
 */
internal interface UpdateTruckUseCase {

    /**
     * Executes the use case to update a [Truck] entity.
     *
     * This method checks if the truck exists and performs the update if valid.
     * It also ensures that the user has the necessary permissions to perform the update.
     *
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param truck The [Truck] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] containing [Response.Success] when the update is successful.
     *
     * @throws ObjectNotFoundException If the truck to be updated does not exist.
     * @throws NullPointerException If the [Truck.id] is null during the update attempt.
     */
    fun execute(user: User, truck: Truck): Flow<Response<Unit>>

}