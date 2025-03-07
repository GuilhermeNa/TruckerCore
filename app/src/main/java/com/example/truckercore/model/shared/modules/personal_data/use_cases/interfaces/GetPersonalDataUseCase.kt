package com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
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
internal interface GetPersonalDataUseCase {

    /**
     * Executes the use case to retrieve an [PersonalData] entity by its parentID.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of [Response<List<PersonalData>>] that will emit:
     * - [Response.Success] if the data was found and retrieved.
     * - [Response.Empty] if the data with the provided parentId does not exist.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<PersonalData>>

    /**
     * Fetches a list of personal data from the storage based on the provided query settings.
     * This method allows filtering the files using a list of query settings.
     *
     * @param queryParams The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [PersonalData] objects that match the query.
     * - [Response.Empty] if no files match the query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<PersonalData>>>

}