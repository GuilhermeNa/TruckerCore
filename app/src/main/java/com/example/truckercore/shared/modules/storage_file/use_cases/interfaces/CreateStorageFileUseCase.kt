package com.example.truckercore.shared.modules.storage_file.use_cases.interfaces

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the use case for creating a new [StorageFile] entity.
 *
 * @see StorageFile
 * @see Response
 */
internal interface CreateStorageFileUseCase {

    /**
     * Executes the use case to create a new [StorageFile] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param file The [StorageFile] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully created.
     */
    fun execute(user: User, file: StorageFile): Flow<Response<String>>

}