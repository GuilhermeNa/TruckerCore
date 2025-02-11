package com.example.truckercore.shared.modules.storage_file.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating an [StorageFile] entity.
 */
internal interface UpdateStorageFileUseCase {

    /**
     * Executes the use case to update an existing [StorageFile] entity.
     *
     * @param user The [User] performing the update. This is used to check if the user has the necessary permissions.
     * @param file The [StorageFile] entity that is being updated.
     * @return A [Flow] of [Response.Success] if the update was successful.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    suspend fun execute(user: User, file: StorageFile): Flow<Response<Unit>>

}