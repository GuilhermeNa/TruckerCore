package com.example.truckercore.model.infrastructure.data_source.firebase.expressions

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.integration._data.for_api.exceptions.DataSourceException
import com.example.truckercore.model.infrastructure.integration._data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration._data.for_api.exceptions.MappingException
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.ProducerScope
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


