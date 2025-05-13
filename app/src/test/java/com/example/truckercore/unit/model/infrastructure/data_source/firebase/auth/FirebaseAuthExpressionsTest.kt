package com.example.truckercore.unit.model.infrastructure.data_source.firebase.auth

import com.example.truckercore._test_utils.mockStaticTextUtil
import com.example.truckercore._utils.expressions.awaitSuccessOrThrow
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidCredentialsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UnknownException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FirebaseAuthExpressionsTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    @BeforeEach
    fun setup() {
        mockStaticTextUtil()
    }

    //----------------------------------------------------------------------------------------------
    // awaitSuccessOrThrow
    //----------------------------------------------------------------------------------------------
    @Test
    fun `awaitSuccessOrThrow should return Unit on success`() {
        // Arrange
        val task = mockk<Task<Any>>(relaxed = true)

        every { task.isSuccessful } returns true
        every { task.exception } returns null
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<Any>>(0)
            listener.onComplete(task)
            task
        }

        // Act && Assert
        assertDoesNotThrow {
            runTest {
                task.awaitSuccessOrThrow { e -> UnknownException("unexpected", e) }
            }
        }

    }

    @Test
    fun `awaitSuccessOrThrow should throw mapped exception when task fails with exception`() =
        runTest {
            // Arrange
            val task = mockk<Task<Any>>(relaxed = true)
            val originalException =
                FirebaseAuthInvalidCredentialsException("auth/error", "invalid creds")

            every { task.isSuccessful } returns false
            every { task.exception } returns originalException
            every { task.addOnCompleteListener(any()) } answers {
                val listener = arg<OnCompleteListener<Any>>(0)
                listener.onComplete(task)
                task
            }

            // Act && Assert
            assertThrows<InvalidCredentialsException> {
                task.awaitSuccessOrThrow { InvalidCredentialsException() }
            }

        }

    @Test
    fun `awaitSuccessOrThrow should throw TaskFailureException when no exception and not successful`() =
        runTest {
            // Arrange
            val task = mockk<Task<Any>>(relaxed = true)

            every { task.isSuccessful } returns false
            every { task.exception } returns null
            every { task.addOnCompleteListener(any()) } answers {
                val listener = arg<OnCompleteListener<Any>>(0)
                listener.onComplete(task)
                task
            }

            // Act && Assert
            assertThrows<TaskFailureException> {
                task.awaitSuccessOrThrow { TaskFailureException() }
            }

        }

}