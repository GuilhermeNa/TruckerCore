package com.example.truckercore.shared.modules.storage_file.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for deleting a [StorageFile] entity by its ID.
 *
 * @see StorageFile
 * @see Response
 */
internal interface DeleteStorageFileUseCase {

    /**
     * Executes the use case to delete a [StorageFile] entity by its ID.
     *
     * @param user The [User] who is requesting the deletion. This is used to check if the user has the necessary permissions.
     * @param id The ID of the [StorageFile] entity to be deleted.
     * @return A [Flow] of [Response.Success] if the data was successfully deleted.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    fun execute(user: User, id: String): Flow<Response<Unit>>

}