package com.example.truckercore._shared.expressions

import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Awaits the completion of a [Task] and throws a mapped [AuthSourceException] if it fails.
 *
 * This suspending extension function is used to bridge Firebase-style asynchronous APIs (based on [Task])
 * with Kotlin coroutines. It waits for the task to complete and:
 * - Resumes normally if the task completes successfully.
 * - Resumes with a thrown exception if the task fails, using the provided [authError] mapper.
 *
 * This allows centralizing exception handling by mapping all task-related exceptions into domain-specific
 * [AuthSourceException] subclasses using the same strategy as defined in your [AuthSourceErrorMapper].
 *
 * ### Example usage:
 * ```
 * val task: Task<AuthResult> = firebaseAuth.signInWithEmailAndPassword(email, password)
 * task.awaitSuccessOrThrow { exception -> errorMapper.signingInWithEmail(exception) }
 * ```
 *
 * @receiver The Firebase [Task] instance being awaited.
 * @param authError A lambda function that maps the thrown [Exception] into an [AuthSourceException].
 * @throws AuthSourceException If the task fails with an exception or is not marked successful.
 */
suspend inline fun Task<*>.awaitSuccessOrThrow(
    crossinline authError: (Exception) -> AuthSourceException
) = suspendCoroutine { cont ->
    addOnCompleteListener { task ->
        task.exception?.let { e ->
            cont.resumeWithException(authError(e))
        } ?: if (task.isSuccessful) cont.resume(Unit)
        else cont.resumeWithException(authError(TaskFailureException()))
    }
}