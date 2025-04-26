package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the use case for creating new system access.
 * Provides a method for executing the process of creating and validating necessary data
 * for system access based on the provided requirements.
 */
internal interface CreateNewSystemAccessUseCase {

    /**
     * Executes the process of creating new system access based on the provided [NewAccessRequirements].
     *
     * This method manages the transaction process, including the creation of references and DTOs for
     * central, user, and person entities, and stores the data in the Firebase Firestore.
     *
     * @param requirements The [NewAccessRequirements] containing details necessary for creating new access.
     * @return A [Flow] emitting a [Response] that indicates success or failure.
     */
    fun execute(requirements: String): Flow<Response<Unit>>

}