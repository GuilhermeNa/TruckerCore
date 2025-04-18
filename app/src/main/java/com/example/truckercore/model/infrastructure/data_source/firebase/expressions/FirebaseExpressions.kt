package com.example.truckercore.model.infrastructure.data_source.firebase.expressions

import com.example.truckercore.model.infrastructure.integration.source_data.exceptions.DataSourceMappingException
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Awaits the completion of a Firebase [Task] in a suspendable way.
 *
 * This function suspends until the task is completed, and then:
 * - Resumes normally if the task is successful.
 * - Throws the original exception if one was provided by Firebase.
 * - Throws [IncompleteTaskException] if the task completes unsuccessfully but without an exception.
 *
 * This is useful when integrating Firebase's callback-based API into Kotlin coroutines
 * without relying on external dependencies like `kotlinx-coroutines-play-services`.
 *
 * @throws Exception Any exception originally thrown by the Firebase Task.
 * @throws IncompleteTaskException If the task failed but did not provide a specific exception.
 *
 * ### Example:
 * ```kotlin
 * val task = auth.signInWithEmailAndPassword(email, password)
 * try {
 *     task.awaitSuccessOrThrow()
 *     // Sign-in successful
 * } catch (e: Exception) {
 *     // Handle invalid credentials
 * }
 * ```
 */
suspend fun Task<*>.awaitSuccessOrThrow(): Unit = suspendCoroutine { cont ->
    addOnCompleteListener { task ->
        task.exception?.let { e ->
            cont.resumeWithException(e)
        } ?: if (task.isSuccessful) cont.resume(Unit)
        else cont.resumeWithException(IncompleteTaskException())
    }
}

/**
 * Converts a [QuerySnapshot] into a list of DTOs of the specified type.
 *
 * This function maps each [DocumentSnapshot] in the query result to an instance of [T],
 * using [DocumentSnapshot.toDto]. If the snapshot is empty, it returns an empty list.
 *
 * @param clazz the class of the DTO to convert each document into
 * @return a list of mapped DTOs or an empty list if the snapshot is empty
 *
 * @throws DataSourceMappingException if any document cannot be mapped to the provided DTO class
 *
 * ### Example:
 * ```kotlin
 * val users: List<UserDto> = querySnapshot.toList(UserDto::class.java)
 * ```
 */
fun <T : BaseDto> QuerySnapshot.toList(clazz: Class<T>): List<T> =
    if (this.isEmpty) emptyList()
    else this.mapNotNull { it.toDto(clazz) }

/**
 * Converts a [DocumentSnapshot] into a DTO of the specified type.
 *
 * This function attempts to deserialize the document's data into an instance of [T]
 * using Firebase's `toObject()` function. If conversion fails or returns null,
 * a [DataSourceMappingException] is thrown.
 *
 * @param clazz the class of the DTO to convert the document into
 * @return an instance of [T] representing the document's data
 *
 * @throws DataSourceMappingException if the conversion fails or results in null
 *
 * ### Example:
 * ```kotlin
 * val user: UserDto = documentSnapshot.toDto(UserDto::class.java)
 * ```
 */
fun <T : BaseDto> DocumentSnapshot.toDto(clazz: Class<T>): T =
    this.toObject(clazz) ?: throw DataSourceMappingException(
        "DocumentSnapshot can not be converted into a DTO class: (${this}), (${clazz.simpleName})."
    )