/*
package com.example.truckercore.unit.model.infrastructure.integration.writer.for_app.repository

import com.example.truckercore._test_data_provider.fake_objects.FakeInstruction
import com.example.truckercore.model.infrastructure.data_source.writer.impl.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.ExecutorAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.error_codes.ExecuteInstructionErrCode
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepository
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepositoryImpl
import com.example.truckercore.model.shared.utils.expressions.extractData
import com.example.truckercore.model.shared.utils.expressions.extractError
import com.example.truckercore._utils.classes.AppResult
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class ApiInstructionExecutorRepositoryTest : KoinTest {

    private val executor: ApiInstructionExecutor<*> by inject()
    private val repository: InstructionExecutorRepository by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(
                module {
                    single<ApiInstructionExecutor<*>> { mockk() }
                    single { ExecutorAppErrorFactory() }
                    single<InstructionExecutorRepository> { InstructionExecutorRepositoryImpl(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `invoke should return Success when executor completes without exception`() = runTest {
        // Arrange
        val instructions = ArrayDeque<FakeInstruction>()
        coEvery { executor.invoke(instructions) } just Runs

        // Act
        val result = repository.invoke(instructions)

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(Unit, result.extractData())
    }

    @Test
    fun `invoke should map InvalidInstructionException to InvalidInstruction error`() = runTest {
        // Arrange
        val ex = InvalidInstructionException("Invalid")
        val instructions = ArrayDeque<FakeInstruction>()
        coEvery { executor.invoke(instructions) } throws ex

        // Act
        val result = repository.invoke(instructions)

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(ExecuteInstructionErrCode.InvalidInstruction, result.extractError().errorCode)
    }

    @Test
    fun `invoke should map InstructionNotImplementedException to InstructionNotImplemented error`() =
        runTest {
            // Arrange
            val ex = InstructionNotImplementedException("Not implemented")
            val instructions = ArrayDeque<FakeInstruction>()
            coEvery { executor.invoke(instructions) } throws ex

            // Act
            val result = repository.invoke(instructions)

            // Assert
            assertTrue(result is AppResult.Error)
            val error = result.extractError()
            assertEquals(ExecuteInstructionErrCode.InstructionNotImplemented, error.errorCode)
            assertEquals(ex, error.cause)
        }

    @Test
    fun `invoke should map unknown exception to Unknown error`() = runTest {
        // Arrange
        val ex = RuntimeException("Something went wrong")
        val instructions = ArrayDeque<FakeInstruction>()
        coEvery { executor.invoke(instructions) } throws ex

        // Act
        val result = repository.invoke(instructions)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result.extractError()
        assertEquals(ExecuteInstructionErrCode.Unknown, error.errorCode)
        assertEquals(ex, error.cause)
    }

}*/
