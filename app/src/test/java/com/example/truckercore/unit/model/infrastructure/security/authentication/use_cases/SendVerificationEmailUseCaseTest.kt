package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.SendVerificationEmailUseCaseImpl
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class SendVerificationEmailUseCaseTest : KoinTest {

    // Injections
    private val auth: FirebaseAuthRepository by inject()
    private val useCase: SendVerificationEmailUseCase by inject()

    // Objects
    private val mockFireBaseUser: FirebaseUser = mockk()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<FirebaseAuthRepository> { mockk() }
                    single<SendVerificationEmailUseCase> { SendVerificationEmailUseCaseImpl(get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should send email verification when current user is found`() = runTest {
        // Arrange
        every { auth.getCurrentUser() } returns mockFireBaseUser
        coEvery { auth.sendEmailVerification(mockFireBaseUser) } returns Response.Success(Unit)

        // Act
        val result = useCase.invoke()

        // Assert
        assertTrue(result is Response.Success)
        coVerify(exactly = 1) { auth.sendEmailVerification(mockFireBaseUser) }
    }

    @Test
    fun `should return error when current user is not found`() = runTest {
        // Arrange
        every { auth.getCurrentUser() } returns null

        // Act
        val result = useCase.invoke()

        // Assert
        assertTrue(result is Response.Error)
        val error = result.extractException()
        assertTrue(error is NullFirebaseUserException)
        assertEquals(error?.message, ERROR_MESSAGE)
        coVerify(exactly = 0) { auth.sendEmailVerification(any()) }
    }

    companion object {
        private const val ERROR_MESSAGE = "Failed to complete email verification." +
                " The Firebase current user was not found"
    }

}