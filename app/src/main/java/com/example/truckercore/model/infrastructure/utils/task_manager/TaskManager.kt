package com.example.truckercore.model.infrastructure.utils.task_manager

/**
 * Interface representing a generic task manager capable of storing and exposing
 * the state of an operation.
 *
 * @param T The type of the result produced by the task.
 */
interface TaskManager<T> {

    /**
     * The result of the task if it has completed successfully.
     */
    val result: T?

    /**
     * Whether the task was completed successfully.
     */
    val isSuccess: Boolean

    /**
     * Whether the task was cancelled with an exception.
     */
    val isCanceled: Boolean

    /**
     * Whether the task has failed due to an error.
     */
    val isFailure: Boolean

    /**
     * The exception that caused the task to fail or be cancelled, if any.
     */
    val exception: Exception?

    /**
     * Sets the task result as a successful operation.
     *
     * @param data The successful result.
     */
    fun onSuccess(data: T)

    /**
     * Sets the task result as a failure due to an exception.
     *
     * @param e The exception that caused the failure.
     */
    fun onError(e: Exception)

    /**
     * Sets the task result as cancelled due to an exception.
     *
     * @param e The exception that caused the cancellation.
     */
    fun onCancel(e: Exception)

    /**
     * Clear the task result.
     */
    fun clear()

}