package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore._test_utils.mockStaticTextUtil
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations.CreateUserAndVerifyEmailUseCaseImpl
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.utils.sealeds.Result
import com.example.truckercore.model.shared.task_manager.TaskManagerImpl
import com.example.truckercore.model.shared.task_manager.TaskManager
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateUserAndVerifyEmailUseCaseTest : KoinTest {

    // Injections
    private val authRepo: AuthenticationRepository by inject()
    private val useCase: CreateUserAndVerifyEmailUseCase by inject()

    // Data
    private val fbUser: FirebaseUser = mockk(relaxed = true)
    private val credential = EmailAuthCredential(
        name = "John Doe",
        email = "abc@email.com",
        password = "123456"
    )

    @BeforeEach
    fun setup() {
        mockStaticTextUtil()
        mockStaticLog()
        startKoin {
            modules(
                module {
                    single<AuthenticationRepository> { mockk(relaxed = true) }
                    factory<TaskManager<Any>> { TaskManagerImpl() }
                    single<CreateUserAndVerifyEmailUseCase> {
                        CreateUserAndVerifyEmailUseCaseImpl(
                            get(), get(), get(), get()
                        )
                    }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should return an Response when the operation is successful`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.updateUserProfile(any(), any()) } returns Result.Success(Unit)
        coEvery { authRepo.sendEmailVerification(any()) } returns Result.Success(Unit)

        // Act
        val result = useCase(credential)

        // Assertions

        assertEquals(result.firebaseUser, fbUser)
        assertTrue(result.userCreated)
        assertTrue(result.nameUpdated)
        assertTrue(result.emailSent)
        assertTrue(result.userCreated)
        assertTrue(result.errors.isEmpty())
        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.updateUserProfile(fbUser, any())
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an Response when the user creation returns empty`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Empty

        // Act
        val result = useCase(credential)

        // Assert
        assertNull(result.firebaseUser)
        assertFalse(result.userCreated)
        assertFalse(result.nameUpdated)
        assertFalse(result.emailSent)
        assertTrue(result.errors.first() is NullFirebaseUserException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 0) {
            authRepo.updateUserProfile(any(), any())
        }
        coVerify(exactly = 0) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an Response when the user creation returns error`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Error(
            NullPointerException("Simulated")
        )

        // Act
        val result = useCase(credential)

        // Assert
        assertNull(result.firebaseUser)
        assertFalse(result.userCreated)
        assertFalse(result.nameUpdated)
        assertFalse(result.emailSent)
        assertTrue(result.errors.size == 1)
        assertTrue(result.errors.first() is NullPointerException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 0) {
            authRepo.updateUserProfile(any(), any())
        }
        coVerify(exactly = 0) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an Response when the name update response returns error`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.updateUserProfile(any(), any()) } returns Result.Error(
            NullPointerException("Simulated")
        )
        coEvery { authRepo.sendEmailVerification(any()) } returns Result.Success(Unit)

        // Act
        val result = useCase(credential)

        assertEquals(result.firebaseUser, fbUser)
        assertTrue(result.userCreated)
        assertFalse(result.nameUpdated)
        assertTrue(result.emailSent)
        assertTrue(result.errors.size == 1)
        assertTrue(result.errors.first() is NullPointerException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.updateUserProfile(fbUser, any())
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an Response when the email verification returns error`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.updateUserProfile(any(), any()) } returns Result.Success(Unit)
        coEvery { authRepo.sendEmailVerification(any()) } returns Result.Error(
            NullPointerException("Simulated")
        )

        // Act
        val result = useCase(credential)

        // Assert
        assertEquals(result.firebaseUser, fbUser)
        assertTrue(result.userCreated)
        assertTrue(result.nameUpdated)
        assertFalse(result.emailSent)
        assertTrue(result.errors.size == 1)
        assertTrue(result.errors.first() is NullPointerException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.updateUserProfile(fbUser, any())
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an Response when name update and email verification failed`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.updateUserProfile(any(), any()) } returns Result.Error(
            InvalidStateException("Simulated")
        )
        coEvery { authRepo.sendEmailVerification(any()) } returns Result.Error(
            NullPointerException("Simulated")
        )

        // Act
        val result = useCase(credential)

        // Assert
        assertEquals(result.firebaseUser, fbUser)
        assertTrue(result.userCreated)
        assertFalse(result.nameUpdated)
        assertFalse(result.emailSent)
        assertTrue(result.errors.size == 2)
        assertTrue(result.errors.first() is InvalidStateException)
        assertTrue(result.errors.last() is NullPointerException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.updateUserProfile(fbUser, any())
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

}