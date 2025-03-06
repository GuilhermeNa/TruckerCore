package com.example.truckercore.shared.abstractions

import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Abstract base class that provides a mechanism for executing operations safely by handling exceptions.
 * It provides a method [runSafe] that wraps operations in a safe context and ensures that exceptions
 * are caught and handled using an [ExceptionHandler].
 *
 * This class is intended to be extended by services that need to perform operations that might throw
 * exceptions, allowing for centralized exception handling.
 *
 * @property exceptionHandler An instance of [ExceptionHandler] responsible for catching and processing exceptions.
 */
internal abstract class Service(
    open val exceptionHandler: ExceptionHandler
) {

    /**
     * Executes the provided operation within a safe context. If any exception occurs during the operation,
     * it is caught and handled using the [exceptionHandler].
     *
     * This method ensures that exceptions are handled consistently and that no uncaught exceptions
     * propagate to higher levels in the application.
     *
     * @param block The operation to be executed. It is a function that returns a [Flow],
     *              which represents the result of the operation.
     * @return A [Flow] that represents the result of the operation, potentially wrapped in
     *         a safe context with exception handling.
     */
    fun <T> runSafe(block: () -> Flow<Response<T>>): Flow<Response<T>> =
        try {
            block()
                .flowOn(Dispatchers.IO)
                .catch { emit(exceptionHandler.run(it)) }

        } catch (e: Exception) {
            flow { emit(exceptionHandler.run(e)) }
        }

}