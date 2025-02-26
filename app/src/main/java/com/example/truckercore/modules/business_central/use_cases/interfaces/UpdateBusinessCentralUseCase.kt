package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
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
     *
     * This method encapsulates the logic required to update a [BusinessCentral] entity in the system.
     * The operation typically involves validating the entity, checking if it exists, and updating it in the repository.
     * Additionally, the [user] parameter may be used to check whether the user has the necessary permissions for this action.
     *
     * @param user The [User] performing the update. This parameter is used to verify permissions and to track who is making the update.
     * @param bCentral The [BusinessCentral] entity that is being updated. This contains the new data to be persisted in the system.
     * @return A [Flow] that emits a [Response.Success] when the update is successfully completed.
     *         If any error occurs during the process, a relevant error response is emitted.
     * @throws NullPointerException If the [BusinessCentral] entity ID is null while performing the update.
     * @throws ObjectNotFoundException If the [BusinessCentral] entity does not exist during the existence check.
     */
    fun execute(user: User, bCentral: BusinessCentral): Flow<Response<Unit>>

}