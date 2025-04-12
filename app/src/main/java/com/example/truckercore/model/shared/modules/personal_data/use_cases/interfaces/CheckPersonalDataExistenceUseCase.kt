package com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for checking the existence of a [PersonalData] entity by its ID.
 */
internal interface CheckPersonalDataExistenceUseCase {

    /**
     * Executes the use case to check if an [PersonalData] entity exists by its ID.
     *
     * @param user The [User] who is making the request.
     * @param id The ID of the [PersonalData] entity to check for existence.
     * @return A [Flow] of:
     * - [Response.Success] when the object exists.
     * - [Response.Empty] when the object does not exist.
     */
    fun execute(user: User, id: String): Flow<Response<Unit>>

}