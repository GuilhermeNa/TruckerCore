package com.example.truckercore.shared.modules.storage_file.repository

import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing [StorageFile] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [StorageFile] objects. It typically
 * includes operations such as creating, reading, updating, and deleting data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see StorageFile
 */
internal interface StorageFileRepository : Repository<StorageFileDto> {

    /**
     * Fetches a list of [StorageFile] entities by their parentId.
     *
     * @param parentId The parent unique identifier (parentId) used to filter the storage file entities.
     * @return A [Flow] of:
     * - [Response.Success] when the object's are successfully found.
     * - [Response.Error] when any error occurs.
     * - [Response.Empty] when the data was not found.
     */
    suspend fun fetchByParentId(parentId: String): Flow<Response<List<StorageFileDto>>>

}