package com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating an [PersonalData] entity.
 *
 * This use case involves updating an existing [PersonalData] in the system, which may include
 * validating the data, checking permissions, and applying business rules.
 *
 * @see PersonalData
 * @see Response
 */
internal interface UpdatePersonalDataUseCase {

    /**
     * Executes the use case to update an existing [PersonalData] entity.
     *
     * @param user The [User] performing the update. This is used to check if the user has the necessary permissions.
     * @param pData The [PersonalData] entity that is being updated.
     * @return A [Flow] of [Response<Unit>] that will emit:
     * - [Response.Success] if the update was successful.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    suspend fun execute(user: User, pData: PersonalData): Flow<Response<Unit>>

}