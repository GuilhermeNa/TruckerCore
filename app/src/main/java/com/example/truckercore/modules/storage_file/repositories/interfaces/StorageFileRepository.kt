package com.example.truckercore.modules.storage_file.repositories.interfaces

import com.example.truckercore.configs.database.Collection
import com.example.truckercore.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface StorageFileRepository<T>: Repository<T> {

    override val collectionName: String
        get() = Collection.FILE

    /**
     * Fetches a list of StorageFile entities by their parent UID.
     *
     * @param parentId The parent unique identifier (parentId) used to filter the storage file entities.
     * @return A [Flow] that emits a [Response] containing a list of StorageFileDto entities.
     * If an error occurs, an [Error] response is emitted instead.
     */
    suspend fun fetchFilesByParentUid(parentId: String): Flow<Response<List<StorageFileDto>>>

}