package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
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
     * @param entity The [BusinessCentral] entity to be created. This object contains the data to be stored in the system.
     * @return A [Response] string representing the unique identifier of the newly created [BusinessCentral] entity.
     */
    suspend fun execute(entity: BusinessCentral): Flow<Response<String>>

}