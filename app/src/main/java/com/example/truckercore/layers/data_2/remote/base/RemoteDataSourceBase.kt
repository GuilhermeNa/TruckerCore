package com.example.truckercore.layers.data_2.remote.base

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.core.error.default_errors.FirebaseRemoteDataException
import com.example.truckercore.core.my_lib.expressions.toDto
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

abstract class RemoteDataSourceBase<D : BaseDto>(protected val firestore: FirebaseFirestore) {

    abstract val dtoClazz: Class<D>  // Class reference for the DTO type

    abstract val collection: AppCollection  // Firestore collection associated with this data source

    //----------------------------------------------------------------------------------------------
    // Fetch a single object or a query result
    //----------------------------------------------------------------------------------------------
    protected suspend fun <D : BaseDto> fetch(
        source: DataSource,
        dataClazz: Class<D>
    ): D? = when (source) {

        is DataSource.Document -> {
            try {
                // Step 1: Get document reference and fetch snapshot
                val documentReference = source.value
                val documentSnapshot = documentReference.get().await()

                // Step 2: Map snapshot to DTO
                documentSnapshot.mapToDto(dataClazz)
            } catch (exception: Exception) {
                // Step 3: Wrap and propagate exceptions as FirebaseRemoteDataException
                throw FirebaseRemoteDataException(
                    "Failed to fetch document from Firestore collection [${collection.name}].",
                    exception
                )
            }
        }

        is DataSource.Query -> {
            try {
                // Step 1: Execute query and fetch snapshot
                val query = source.value
                val querySnapshot = query.get().await()

                // Step 2: Map snapshot to list of DTOs
                val data = querySnapshot.mapToDtoList(dataClazz)

                // Step 3: Validate list (must have 0 or 1 item)
                validateListAndGetDto(data)
            } catch (exception: Exception) {
                // Step 4: Wrap and propagate exceptions
                throw FirebaseRemoteDataException(
                    "Failed to fetch query from Firestore collection [${collection.name}].",
                    exception
                )
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Observe a single document or query in real-time
    //----------------------------------------------------------------------------------------------
    protected fun <D : BaseDto> observe(
        source: DataSource,
        dataClazz: Class<D>
    ): Flow<D?> = callbackFlow {
        when (source) {

            is DataSource.Document -> {
                // Step 1: Attach a snapshot listener to the Firestore document
                val listener = source.value.addSnapshotListener { documentSnapshot, error ->

                    // Step 2: Handle listener errors by closing the flow
                    if (error != null) {
                        this.close(error)
                        return@addSnapshotListener
                    }

                    try {
                        // Step 3: Map the document snapshot to a DTO object
                        val data = documentSnapshot.mapToDto(dataClazz)

                        // Step 4: Emit the DTO into the flow
                        trySend(data)
                    } catch (exception: Exception) {
                        // Step 5: Wrap any mapping exceptions in a FirebaseRemoteDataException
                        // and close the flow
                        close(
                            FirebaseRemoteDataException(
                                "Failed to observe document from Firestore collection [${collection.name}].",
                                exception
                            )
                        )
                    }
                }

                // Step 6: Remove the listener when the flow is closed
                awaitClose { listener.remove() }
            }

            is DataSource.Query -> {
                // Step 1: Attach a snapshot listener to the Firestore query
                val listener = source.value.addSnapshotListener { querySnapshot, error ->

                    // Step 2: Handle listener errors by closing the flow
                    if (error != null) {
                        this.close(error)
                        return@addSnapshotListener
                    }

                    try {
                        // Step 3: Map the query snapshot to a list of DTOs
                        val data = querySnapshot.mapToDtoList(dataClazz)

                        // Step 4: Validate the list and extract a single DTO (or null)
                        val dto = validateListAndGetDto(data)

                        // Step 5: Emit the DTO into the flow
                        trySend(dto)
                    } catch (exception: Exception) {
                        // Step 6: Wrap any mapping exceptions and close the flow
                        close(
                            FirebaseRemoteDataException(
                                "Failed to observe query from Firestore collection [${collection.name}].",
                                exception
                            )
                        )
                    }
                }

                // Step 7: Remove the listener when the flow is closed
                awaitClose { listener.remove() }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Internal mapping helpers
    //----------------------------------------------------------------------------------------------
    private fun <T : BaseDto> DocumentSnapshot?.mapToDto(dtoClazz: Class<T>): T? {
        if (this == null) {
            // Snapshot itself is null → unexpected error
            throw IllegalStateException(
                "Failed to convert DocumentSnapshot to DTO. Received null from Firestore collection [${collection.name}]."
            )
        } else if (!this.exists()) {
            // Document does not exist → treat as empty
            return null
        }

        return toObject(dtoClazz)  // Convert Firestore document to DTO object
    }

    private fun <T : BaseDto> QuerySnapshot?.mapToDtoList(dtoClazz: Class<T>): List<T>? {
        if (this == null) {
            throw IllegalStateException(
                "Failed to convert QuerySnapshot to DTO list. Received null from Firestore collection [${collection.name}]."
            )
        } else if (isEmpty) {
            return null  // No results → treat as empty
        }

        return mapNotNull { it.toDto(dtoClazz) } // Convert all documents to DTOs, skip nulls
    }

    private fun <D : BaseDto> validateListAndGetDto(data: List<D>?): D? {
        return data?.let { list ->
            if (list.size > 1) {
                // Rule violation: only one result expected
                throw IllegalStateException(
                    "Query returned multiple objects for Firestore collection [${collection.name}], but only one result was expected."
                )
            } else {
                list.firstOrNull()  // Return single DTO or null
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Queries and Documents helpers
    //----------------------------------------------------------------------------------------------
    protected fun getQuery(uid: UID): DataSource.Query {
        // Build query by UID
        val query = firestore.collection(collection.name)
            .whereEqualTo("uid", uid.value)
        return DataSource.Query(query)
    }

    fun getQuery(userId: UserID): DataSource.Query {
        // Build query by userId
        val query = firestore.collection(collection.name)
            .whereEqualTo("userId", userId.value)
        return DataSource.Query(query)
    }

}