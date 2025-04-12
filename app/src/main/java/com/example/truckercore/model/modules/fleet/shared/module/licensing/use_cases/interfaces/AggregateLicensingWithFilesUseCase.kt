package com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [Licensing] records along with their associated [File].
 *
 * @see Licensing
 * @see File
 * @see AppResponse
 */
internal interface AggregateLicensingWithFilesUseCase {

    /**
     * Fetches a single licensing record along with its associated files based on the provided ID.
     * This method retrieves the licensing details as a [LicensingWithFile] object, which includes the [Licensing]
     * data and the corresponding list of [File] objects.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing the [LicensingWithFile] object if the licensing record and files were found.
     * - [AppResponse.Empty] if no licensing record exists with the given ID or no files are associated with it.
     */
    fun execute(documentParams: DocumentParameters): Flow<AppResponse<LicensingWithFile>>

    /**
     * Fetches a list of licensing records along with their associated files based on the provided query settings.
     * This method allows filtering the [Licensing] records using a list of query settings and retrieves the related files.
     *
     * @param queryParams The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing a list of [LicensingWithFile] objects, each containing a [Licensing]
     *   object and its corresponding list of [File] objects.
     * - [AppResponse.Empty] if no licensing records match the query criteria or no files are associated with the records.
     */
    fun execute(queryParams: QueryParameters): Flow<AppResponse<List<LicensingWithFile>>>

}