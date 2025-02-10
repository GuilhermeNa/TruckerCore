package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting an [PersonalData] entity by its ID.
 *
 * @see PersonalData
 * @see Response
 */
internal interface DeletePersonalDataUseCase {

    /**
     * Executes the use case to delete an [PersonalData] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This is used to check if the user has the necessary permissions.
     * @param id The ID of the [PersonalData] entity to be deleted.
     * @return A [Flow] response:
     * - [Response.Success] if the data was successfully deleted.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    suspend fun execute(user: User, id: String): Flow<Response<Unit>>

}