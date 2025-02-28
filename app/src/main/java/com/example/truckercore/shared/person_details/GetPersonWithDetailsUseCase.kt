package com.example.truckercore.shared.person_details

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * This interface defines the contract for the use case that retrieves detailed information about a person,
 * including personal data, associated files, and a photo.
 */
internal interface GetPersonWithDetailsUseCase {

    /**
     * Executes the use case to retrieve a [PersonWithDetails] for the given [User].
     *
     * @param user The user whose person details are to be retrieved.
     * @return A [Flow] of:
     * - [Response.Success] containing the detailed information about the person.
     * - [Response.Empty] when the data was not found.
     */
    fun execute(user: User): Flow<Response<PersonWithDetails>>

}