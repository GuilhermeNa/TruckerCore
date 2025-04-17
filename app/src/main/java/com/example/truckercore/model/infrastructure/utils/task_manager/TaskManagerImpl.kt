package com.example.truckercore.model.infrastructure.utils.task_manager

/**
 * Default implementation of [TaskManager], responsible for managing
 * the lifecycle and result of a single task of type [T].
 *
 * It tracks success, error, or cancellation states and ensures
 * the task result is set only once.
 *
 * @param T The type of the result to be managed.
 */
class TaskManagerImpl<T> : TaskManager<T> {

    /**
     * Internal sealed class representing the different states a task can be in.
     */
    private sealed class Task<out T> {
        data class Success<T>(val data: T) : Task<T>()
        data class Error(val exception: Exception) : Task<Nothing>()
        data class Canceled(val exception: Exception) : Task<Nothing>()
    }

    private var _task: Task<T>? = null

    /** The successful result of the task, if available. */
    override val result: T?
        get() = (_task as? Task.Success)?.data

    /** Whether the task completed successfully. */
    override val isSuccess: Boolean
        get() = _task is Task.Success

    /** Whether the task was cancelled. */
    override val isCanceled: Boolean
        get() = _task is Task.Canceled

    /** Whether the task failed with an error. */
    override val isFailure: Boolean
        get() = _task is Task.Error

    /** The exception for failure or cancellation, if any. */
    override val exception: Exception?
        get() = when (_task) {
            is Task.Canceled -> (_task as Task.Canceled).exception
            is Task.Error -> (_task as Task.Error).exception
            else -> null
        }

    /**
     * Sets the internal task result.
     *
     * @param input The new task state to set.
     */
    private fun setTaskResult(input: Task<T>) {
        _task = input
    }

    /**
     * Marks the task as completed successfully.
     *
     * @param data The result of the successful task.
     */
    override fun onSuccess(data: T) {
        setTaskResult(Task.Success(data))
    }

    /**
     * Marks the task as failed due to an error.
     *
     * @param e The exception that caused the failure.
     */
    override fun onError(e: Exception) {
        setTaskResult(Task.Error(e))
    }

    /**
     * Marks the task as cancelled.
     *
     * @param e The exception that caused the cancellation.
     */
    override fun onCancel(e: Exception) {
        setTaskResult(Task.Canceled(e))
    }

    /**
     * Clear the task result.
     */
    override fun clear() {
        _task = null
    }

}

