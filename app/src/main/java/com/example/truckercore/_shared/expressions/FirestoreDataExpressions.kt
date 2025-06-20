package com.example.truckercore._shared.expressions

import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.DataSourceException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.ProducerScope

/**
 * Executes a block that produces a value and emits it to the [Flow], or closes the flow with a mapped error if it fails.
 *
 * This function is typically used inside snapshot listeners to emit transformed DTOs to the flow.
 * If an exception is thrown during emission (e.g., during mapping), the flow is closed with a [DataSourceException].
 *
 * ### Example:
 * ```kotlin
 * safeEmit(
 *     block = { snapshot.toDto(MyDto::class.java) },
 *     error = { errorMapper(it, spec) } // Map the error to an App known error
 * )
 * ```
 *
 * @param block The block that returns a value to be sent through the flow.
 * @param error A function to map thrown exceptions into [DataSourceException]s.
 */
inline fun <T> ProducerScope<T>.safeEmit(
    block: () -> T,
    error: (Throwable) -> DataSourceException
) {
    runCatching { block() }
        .onSuccess { this.trySend(it) }
        .onFailure { this.close(error(it)) }
}

/**
 * Safely converts a [DocumentSnapshot] to a DTO of type [T].
 *
 * If the document does not exist, returns `null`. If the snapshot is null or mapping fails,
 * a [DataSourceException] is thrown.
 *
 * @param clazz The class of the DTO.
 * @return The mapped DTO, or `null` if the document doesn't exist.
 * @throws InvalidDataException If the snapshot is null.
 * @throws MappingException If the mapping fails.
 */
fun <T : BaseDto> DocumentSnapshot?.toDto(clazz: Class<T>): T? =
    if (this == null) throw InvalidDataException()
    else if (!exists()) null
    else toObject(clazz) ?: throw MappingException()

/**
 * Safely converts a [QuerySnapshot] to a list of DTOs of type [T].
 *
 * If the snapshot is empty, returns `null`. If the snapshot is null, throws an [InvalidDataException].
 *
 * @param clazz The class of the DTO.
 * @return A list of mapped DTOs, or `null` if the snapshot is empty.
 * @throws InvalidDataException If the snapshot is null.
 */
fun <T : BaseDto> QuerySnapshot?.toList(clazz: Class<T>): List<T>? =
    if (this == null) throw InvalidDataException()
    else if (isEmpty) null
    else mapNotNull { it.toDto(clazz) }
