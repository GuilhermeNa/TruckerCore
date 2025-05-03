package com.example.truckercore.unit.model.shared.task_manager

import com.example.truckercore.model.z_to_delete.task_manager.TaskManager
import com.example.truckercore.model.z_to_delete.task_manager.TaskManagerImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TaskManagerTest : KoinTest {

    private val taskManager: TaskManager<String> by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    factory<TaskManager<String>> { TaskManagerImpl() }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should return default values when the task was not set yet`() {
        // Assert
        assertNull(taskManager.result)
        assertFalse(taskManager.isSuccess)
        assertFalse(taskManager.isCanceled)
        assertFalse(taskManager.isFailure)
        assertNull(taskManager.exception)
    }

    @Test
    fun `should return success state task was set as success`() {
        // Arrange
        val value = "Value"

        // Act
        taskManager.onSuccess(value)

        // Assert
        assertEquals(taskManager.result, value)
        assertTrue(taskManager.isSuccess)
        assertFalse(taskManager.isCanceled)
        assertFalse(taskManager.isFailure)
        assertNull(taskManager.exception)
    }

    @Test
    fun `should return error state when task was set as error`() {
        // Arrange
        val e = NullPointerException()

        // Act
        taskManager.onError(e)

        //Call
        assertNull(taskManager.result)
        assertFalse(taskManager.isSuccess)
        assertFalse(taskManager.isCanceled)
        assertTrue(taskManager.isFailure)
        assertEquals(taskManager.exception, e)
    }

    @Test
    fun `should return cancel state when task was set as error`() {
        // Arrange
        val e = NullPointerException()

        // Act
        taskManager.onCancel(e)

        //Call
        assertNull(taskManager.result)
        assertFalse(taskManager.isSuccess)
        assertTrue(taskManager.isCanceled)
        assertFalse(taskManager.isFailure)
        assertEquals(taskManager.exception, e)
    }

    @Test
    fun `should set task in initial state when clear`() {
        // Act
        taskManager.clear()

        //Call
        assertNull(taskManager.result)
        assertFalse(taskManager.isSuccess)
        assertFalse(taskManager.isCanceled)
        assertFalse(taskManager.isFailure)
        assertNull(taskManager.exception)
    }


}