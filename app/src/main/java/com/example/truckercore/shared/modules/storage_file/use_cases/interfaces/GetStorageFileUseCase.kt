package com.example.truckercore.shared.modules.storage_file.use_cases.interfaces

import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [StorageFile].
 *
 * @see StorageFile
 * @see Response
 */
internal interface GetStorageFileUseCase {

    /**
     * Fetches a single file from the storage based on the provided file ID.
     * This method retrieves the file details as a [StorageFile] object.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [StorageFile] object if the file was found.
     * - [Response.Empty] if the file with the given ID does not exist.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<StorageFile>>

    /**
     * Fetches a list of files from the storage based on the provided query settings.
     * This method allows filtering the files using a list of query settings.
     *
     * @param queryParams The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [StorageFile] objects that match the query.
     * - [Response.Empty] if no files match the query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<StorageFile>>>

}