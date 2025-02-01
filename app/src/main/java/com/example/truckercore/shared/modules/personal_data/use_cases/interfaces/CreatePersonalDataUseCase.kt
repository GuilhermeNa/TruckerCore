package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the use case for creating a new [PersonalData] entity.
 *
 * @see PersonalData
 * @see Response
 */
internal interface CreatePersonalDataUseCase {

    /**
     * Executes the use case to create a new [PersonalData] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param pData The [PersonalData] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully created.
     * - [Response.Error] when the object creation fails.
     */
    suspend fun execute(user: User, pData: PersonalData): Flow<Response<String>>

}