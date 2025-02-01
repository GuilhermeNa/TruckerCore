package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving an [PersonalData] entity by its ID.
 *
 * @see PersonalData
 * @see Response
 */
internal interface GetPersonalDataByIdUseCase {

    /**
     * Executes the use case to retrieve an [PersonalData] entity by its ID.
     *
     * @param user The [User] who is requesting the retrieval. This is used to check if the user has the necessary permissions.
     * @param id The ID of the [PersonalData] entity to be fetched.
     *
     * @return A [Flow] of [Response<PersonalData>] that will emit:
     * - [Response.Success] if the data was found and retrieved.
     * - [Response.Error] if there was any error during the retrieval process.
     * - [Response.Empty] if the data with the provided ID does not exist.
     */
    suspend fun execute(user: User, id: String): Flow<Response<PersonalData>>

}