package com.example.truckercore.model.infrastructure.data_source.firebase.auth

import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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


