package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving an [PersonalData] entity by its ID.
 *
 * This use case involves fetching the [PersonalData] from the repository using the provided ID,
 * checking the necessary permissions, and ensuring the [PersonalData] exists in the system.
 *
 * @see PersonalData
 * @see Response
 */
internal interface GetPersonalDataByParentIdUseCase {

    /**
     * Executes the use case to retrieve an [PersonalData] entity by its parentID.
     *
     * @param user The [User] who is requesting the retrieval. This is used to check if the user has the necessary permissions.
     * @param parentId The ID of the related parent entity.
     *
     * @return A [Flow] of [Response<List<PersonalData>>] that will emit:
     * - [Response.Success] if the data was found and retrieved.
     * - [Response.Error] if there was any error during the retrieval process.
     * - [Response.Empty] if the data with the provided parentId does not exist.
     */
    suspend fun execute(user: User, parentId: String): Flow<Response<List<PersonalData>>>

}