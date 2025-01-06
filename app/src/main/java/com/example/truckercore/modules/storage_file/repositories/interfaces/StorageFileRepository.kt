package com.example.truckercore.modules.storage_file.repositories.interfaces

import com.example.truckercore.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface StorageFileRepository {

    /**
     * Creates a new StorageFile entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the storage file information to be created.
     * This method generates an ID for the storage file and stores it in the database.
     * @return The ID of the created object.
     */
    fun createFile(dto: StorageFileDto): String

    /**
     * Updates an existing StorageFile entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the updated storage file information.
     * This method uses the storage file's ID to locate and update the existing document in the database.
     */
    fun updateFile(dto: StorageFileDto)

    /**
     * Deletes a StorageFile entity from the database by its ID.
     *
     * @param id The unique identifier (ID) of the storage file to be deleted.
     * This method removes the storage file from the database based on the given ID.
     */
    fun deleteFile(id: String)

    /**
     * Fetches a StorageFile entity by its ID.
     *
     * @param id The unique identifier (ID) of the storage file to be fetched.
     * @return A [Flow] that emits a [Response] containing the StorageFileDto entity.
     * If an error occurs, an [Error] response is emitted instead.
     */
    suspend fun fetchFileById(id: String): Flow<Response<StorageFileDto>>

    /**
     * Fetches a list of StorageFile entities by their parent UID.
     *
     * @param parentId The parent unique identifier (parentId) used to filter the storage file entities.
     * @return A [Flow] that emits a [Response] containing a list of StorageFileDto entities.
     * If an error occurs, an [Error] response is emitted instead.
     */
    suspend fun fetchFilesByParentUid(parentId: String): Flow<Response<List<StorageFileDto>>>

}