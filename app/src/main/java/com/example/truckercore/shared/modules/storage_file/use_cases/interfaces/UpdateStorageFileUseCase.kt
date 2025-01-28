package com.example.truckercore.shared.modules.storage_file.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating an [StorageFile] entity.
 *
 * @see StorageFile
 * @see Response
 */
interface UpdateStorageFileUseCase {

    /**
     * Executes the use case to update an existing [StorageFile] entity.
     *
     * @param user The [User] performing the update. This is used to check if the user has the necessary permissions.
     * @param pData The [StorageFile] entity that is being updated.
     * @return A [Flow] of [Response<Unit>] that will emit:
     * - [Response.Success] if the update was successful.
     * - [Response.Error] if there was any error during the update.
     */
    suspend fun execute(user: User, file: StorageFile): Flow<Response<Unit>>

}