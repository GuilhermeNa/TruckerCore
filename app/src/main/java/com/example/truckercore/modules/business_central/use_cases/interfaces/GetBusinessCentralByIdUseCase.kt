package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [BusinessCentral] entity by its ID.
 *
 * This interface defines the contract for a use case that retrieves a [BusinessCentral] entity based on the provided ID.
 * The retrieval is asynchronous and returns a [Flow] of [Response] which allows the caller to collect the result reactively.
 * It may include operations like validation, permission checks, and data fetching from a repository or database.
 */
internal interface GetBusinessCentralByIdUseCase {

    /**
     * Executes the use case to retrieve a [BusinessCentral] entity by its ID.
     *
     * @param user The [User] making the request. This parameter might be used for permission checks or tracking.
     * @param id The ID of the [BusinessCentral] entity to be retrieved.
     * @return A [Flow] of [Response] that contains the result of the operation, either a success containing the entity or an error.
     */
    suspend fun execute(user: User, id: String): Flow<Response<BusinessCentral>>

}