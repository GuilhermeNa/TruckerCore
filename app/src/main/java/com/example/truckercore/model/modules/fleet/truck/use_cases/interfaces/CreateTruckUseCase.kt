package com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for creating a new [Truck] entity.
 *
 * It handles the logic required to insert a new entity into the system, such as validating data, applying business rules, and
 * interacting with the persistence layer (e.g., database).
 */
internal interface CreateTruckUseCase {

    /**
     * Executes the use case to create a new [Truck] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param truck The [Truck] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of [Response.Success] when the object is successfully created.
     */
    fun execute(user: User, truck: Truck): Flow<Response<String>>

}