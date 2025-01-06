package com.example.truckercore.shared.interfaces

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface Repository<T> {

    val firebaseRepository: FirebaseRepository
    val queryBuilder: FirebaseQueryBuilder
    val converter: FirebaseConverter<T>
    val collectionName: String

    /**
     * Creates a new entity entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the entity information to be created.
     * This method generates an ID for the entity and stores it in the database.
     * @return The ID of the created object.
     */
    fun create(dto: T): String

    /**
     * Updates an existing Entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the updated information.
     */
    fun update(dto: T)

    /**
     * Deletes an Entity entity from the database by its ID.
     *
     * @param id The unique identifier (ID) of the entity to be deleted.
     */
    fun delete(id: String)

    /**
     * Fetches an Entity by its ID.
     *
     * @param id The unique identifier (ID) of the entity to be fetched.
     * @return A [Flow] that emits a [Response] containing the StorageFileDto entity.
     * If an error occurs, an [Error] response is emitted instead.
     */
    suspend fun fetchById(id: String): Flow<Response<T>>

}