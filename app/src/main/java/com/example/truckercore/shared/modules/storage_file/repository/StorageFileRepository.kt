package com.example.truckercore.shared.modules.storage_file.repository

import com.example.truckercore.shared.interfaces.NewRepository
import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
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
internal interface StorageFileRepository : NewRepository {

    override suspend fun fetchById(id: String): Flow<Response<StorageFileDto>>

    override suspend fun fetchByQuery(settings: List<QuerySettings>): Flow<Response<List<StorageFileDto>>>

}