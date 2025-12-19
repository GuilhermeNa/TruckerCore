package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend inline fun Task<*>.awaitSuccessOrThrow(
    crossinline authError: (Exception) -> AppException
) = suspendCoroutine { cont ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            cont.resume(Unit)
        } else if (task.exception != null) {
            cont.resumeWithException(authError(task.exception!!))
        } else {
            cont.resumeWithException(
                DataException.Unknown("An unknown error occurred while executing the task.")
            )
        }
    }
}
