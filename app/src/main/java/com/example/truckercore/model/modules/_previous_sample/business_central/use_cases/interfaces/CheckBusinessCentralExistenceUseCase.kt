/*
package com.example.truckercore.model.modules._previous_sample.business_central.use_cases.interfaces

import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

*/
/**
 * Interface representing the use case for checking the existence of a [BusinessCentral] entity by its ID.
 *//*

internal interface CheckBusinessCentralExistenceUseCase {

    */
/**
     * Executes the use case to check if a [BusinessCentral] entity exists by its ID.
     *
     * @param user The [User] who is making the request. This parameter may be used for permission checks or to track the request.
     * @param id The ID of the [BusinessCentral] entity to check for existence.
     * @return A [Flow] of:
     * - [Response.Success] when the object exists.
     * - [Response.Empty] when the object does not exist.
     *//*

    fun execute(user: User, id: String): Flow<Response<Unit>>

}*/
