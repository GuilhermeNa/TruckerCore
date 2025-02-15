package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating a [BusinessCentral] entity.
 *
 * This interface defines the contract for a use case that is responsible for updating a [BusinessCentral] entity.
 * It encapsulates the logic for updating the entity, which might include validation, business logic, and persistence operations.
 * The operation is asynchronous and uses Kotlin's [suspend] function to allow it to be executed in a coroutine.
 */
internal interface UpdateBusinessCentralUseCase {

    /**
     * Executes the use case to update a [BusinessCentral] entity.
     * @param user The [User] who is performing the update. This parameter might be used to check permissions or track the update.
     * @param bCentral The [BusinessCentral] entity that is being updated. This contains the data to be modified.
     * @return A [Flow] of [Response.Success] when the object is successfully updated.
     */
    fun execute(user: User, bCentral: BusinessCentral): Flow<Response<Unit>>

}