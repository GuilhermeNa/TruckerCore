package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [PersonalData] records along with their associated [File].
 *
 * @see PersonalData
 * @see File
 * @see Response
 */
internal interface AggregatePersonalDataWithFilesUseCase {

    /**
     * Fetches a single personal data record along with its associated files based on the provided ID.
     * This method retrieves the personal data details as a [PersonalDataWithFile] object, which includes the [PersonalData]
     * data and the corresponding list of [File] objects.
     *
     * @param documentParams The document parameters to filter the personal data records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [PersonalDataWithFile] object if the personal data record and files were found.
     * - [Response.Empty] if no personal data record exists with the given ID or no files are associated with it.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<PersonalDataWithFile>>

    /**
     * Fetches a list of personal data records along with their associated files based on the provided query settings.
     * This method allows filtering the [PersonalData] records using a list of query settings and retrieves the related files.
     *
     * @param queryParams The query parameters to filter the personal data records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [PersonalDataWithFile] objects, each containing a [PersonalData]
     *   object and its corresponding list of [File] objects.
     * - [Response.Empty] if no personal data records match the query criteria or no files are associated with the records.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<PersonalDataWithFile>>>

}