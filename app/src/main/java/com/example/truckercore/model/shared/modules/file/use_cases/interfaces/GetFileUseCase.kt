package com.example.truckercore.model.shared.modules.file.use_cases.interfaces

import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [File].
 *
 * @see File
 * @see AppResponse
 */
internal interface GetFileUseCase {

    /**
     * Fetches a single file from the storage based on the provided file ID.
     * This method retrieves the file details as a [File] object.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing the [File] object if the file was found.
     * - [AppResponse.Empty] if the file with the given ID does not exist.
     */
    fun execute(documentParams: DocumentParameters): Flow<AppResponse<File>>

    /**
     * Fetches a list of files from the storage based on the provided query settings.
     * This method allows filtering the files using a list of query settings.
     *
     * @param queryParams The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing a list of [File] objects that match the query.
     * - [AppResponse.Empty] if no files match the query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<AppResponse<List<File>>>

}