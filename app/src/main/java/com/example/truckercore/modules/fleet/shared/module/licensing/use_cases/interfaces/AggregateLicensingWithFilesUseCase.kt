package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [Licensing] records along with their associated [StorageFile].
 *
 * @see Licensing
 * @see StorageFile
 * @see Response
 */
internal interface AggregateLicensingWithFilesUseCase {

    /**
     * Fetches a single licensing record along with its associated files based on the provided ID.
     * This method retrieves the licensing details as a [LicensingWithFile] object, which includes the [Licensing]
     * data and the corresponding list of [StorageFile] objects.
     *
     * @param user The user requesting the licensing and file information.
     * @param id The ID of the licensing record to fetch along with its associated files.
     * @return A [Flow] of:
     * - [Response.Success] containing the [LicensingWithFile] object if the licensing record and files were found.
     * - [Response.Error] if an error occurs during the fetch.
     * - [Response.Empty] if no licensing record exists with the given ID or no files are associated with it.
     */
    suspend fun execute(user: User, id: String)
            : Flow<Response<LicensingWithFile>>

    /**
     * Fetches a list of licensing records along with their associated files based on the provided query settings.
     * This method allows filtering the [Licensing] records using a list of query settings and retrieves the related files.
     *
     * @param user The user requesting the licensing records and their associated files.
     * @param querySettings The list of query settings to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [LicensingWithFile] objects, each containing a [Licensing]
     *   object and its corresponding list of [StorageFile] objects.
     * - [Response.Error] if an error occurs during the fetch.
     * - [Response.Empty] if no licensing records match the query criteria or no files are associated with the records.
     */
    suspend fun execute(user: User, vararg querySettings: QuerySettings)
            : Flow<Response<List<LicensingWithFile>>>

}