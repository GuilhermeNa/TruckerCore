package com.example.truckercore.shared.modules.storage_file.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [StorageFile] entity by its parent ID.
 *
 * @see StorageFile
 * @see Response
 */
interface GetStorageFileByParentIdUseCase {

    /**
     * Executes the use case to retrieve a [StorageFile] entity by its parent ID.
     *
     * @param user The [User] who is requesting the retrieval. This is used to check if the user has the necessary permissions.
     * @param parentId The ID of the related parent entity.
     *
     * @return A [Flow] of [Response<List<StorageFile>>] that will emit:
     * - [Response.Success] if the data was found and retrieved.
     * - [Response.Error] if there was any error during the retrieval process.
     * - [Response.Empty] if the data with the provided parent ID does not exist.
     */
    suspend fun execute(user: User, parentId: String): Flow<Response<List<StorageFile>>>

}