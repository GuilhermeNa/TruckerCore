package com.example.truckercore.model.modules.person.shared.person_details

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
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
     * - [AppResponse.Success] containing the detailed information about the person.
     * - [AppResponse.Empty] when the data was not found.
     */
    fun execute(user: User): Flow<AppResponse<PersonWithDetails>>

    /**
     * Executes the use case to retrieve detailed information about a [PersonWithDetails]
     * based on the provided [DocumentParameters] and [PersonCategory].
     *
     * This method allows for retrieving the detailed information of a person, including personal data,
     * associated files, and a photo, based on the document parameters and the category of the person
     * (e.g., "ADMIN" or "DRIVER").
     *
     * @param params The [DocumentParameters] containing the necessary information for the query.
     * @param category The [PersonCategory] indicating the type of person (e.g., "ADMIN" or "DRIVER").
     * @return A [Flow] of:
     * - [AppResponse.Success] containing the detailed information about the person.
     * - [AppResponse.Empty] if no data is found.
     *
     * @throws [NullPointerException] If any required parameter is null (e.g., user ID).
     */
    fun execute(
        params: DocumentParameters,
        category: PersonCategory
    ): Flow<AppResponse<PersonWithDetails>>

    /**
     * Executes the use case to retrieve a list of [PersonWithDetails] based on the provided
     * [QueryParameters] and [PersonCategory].
     *
     * This method allows for retrieving detailed information about a list of people, including
     * personal data, associated files, and photos, based on the query parameters and the category
     * of the person (e.g., "ADMIN" or "DRIVER").
     *
     * @param params The [QueryParameters] containing the necessary filters for the query.
     * @param category The [PersonCategory] indicating the type of people to be retrieved
     * (e.g., "ADMIN" or "DRIVER").
     * @return A [Flow] of:
     * - [AppResponse.Success] containing a list of detailed information about the people.
     * - [AppResponse.Empty] if no data is found.
     *
     * @throws [NullPointerException] If any required parameter is null (e.g., user ID).
     */
    fun execute(
        params: QueryParameters,
        category: PersonCategory
    ): Flow<AppResponse<List<PersonWithDetails>>>

}