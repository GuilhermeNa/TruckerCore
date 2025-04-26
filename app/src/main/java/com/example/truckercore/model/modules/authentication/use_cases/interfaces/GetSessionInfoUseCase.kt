package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * This interface defines the contract for the use case that retrieves the details of the logged-in user,
 * including their personal data and associated details.
 */
internal interface GetSessionInfoUseCase {

    /**
     * Executes the use case to retrieve the details of the logged-in user, including their associated person details.
     *
     * @param firebaseUid The unique identifier of the logged-in user (Firebase UID).
     * @return A [Flow] of:
     *  - [Response.Success] containing the logged-in user's details and their associated person data.
     *  - [Response.Empty] when data was not found.
     * @throws ObjectNotFoundException If no person details are found for the user.
     */
    fun execute(firebaseUid: String): Flow<Response<String>>

}
