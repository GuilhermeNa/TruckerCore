package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for creating a new [BusinessCentral] entity.
 *
 * This interface defines the contract for a use case responsible for creating a new [BusinessCentral] entity. It handles
 * the logic required to insert a new entity into the system, such as validating data, applying business rules, and
 * interacting with the persistence layer (e.g., database).
 */
internal interface CreateBusinessCentralUseCase {

    /**
     * Executes the use case to create a new [BusinessCentral] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param entity The [BusinessCentral] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully created.
     */
    suspend fun execute(user: User, bCentral: BusinessCentral): Flow<Response<String>>

}