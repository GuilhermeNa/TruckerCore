package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateUserAndVerifyEmailUseCaseImpl
import com.example.truckercore.model.shared.errors.InvalidResponseException
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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

class CreateUserAndVerifyEmailUseCaseTest : KoinTest {

    // Injections
    private val authRepo: FirebaseAuthRepository by inject()
    private val useCase: CreateUserAndVerifyEmailUseCase by inject()

    // Data
    private val fbUser: FirebaseUser = mockk(relaxed = true)
    private val credential = EmailAuthCredential(
        email = "abc@email.com",
        password = "123456"
    )

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<FirebaseAuthRepository> { mockk(relaxed = true) }
                    single<CreateUserAndVerifyEmailUseCase> {
                        CreateUserAndVerifyEmailUseCaseImpl(
                            get()
                        )
                    }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should return an User Task when the operation is successful`() = runTest {
        // Arrange
        val credential = EmailAuthCredential(
            email = "abc@email.com",
            password = "123456"
        )

        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.sendEmailVerification(any()) } returns Response.Success(Unit)

        // Act
        val response = useCase(credential)

        assertEquals(response.user, fbUser)
        assertTrue(response.userCreated)
        assertTrue(response.emailSent)
        assertNull(response.createUserError)
        assertNull(response.sendEmailError)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an User Task when the user creation returns empty`() = runTest {
        // Arrange
        val credential = EmailAuthCredential(
            email = "abc@email.com",
            password = "123456"
        )

        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Empty

        // Act
        val response = useCase(credential)

        // Assert
        assertNull(response.user)
        assertFalse(response.userCreated)
        assertFalse(response.emailSent)
        assertTrue(response.createUserError is InvalidResponseException)
        assertNull(response.sendEmailError)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 0) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an User Task when the user creation returns error`() = runTest {
        // Arrange
        val credential = EmailAuthCredential(
            email = "abc@email.com",
            password = "123456"
        )

        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Error(
            NullPointerException("Simulated")
        )

        // Act
        val response = useCase(credential)

        // Assert
        assertNull(response.user)
        assertFalse(response.userCreated)
        assertFalse(response.emailSent)
        assertTrue(response.createUserError is NullPointerException)
        assertNull(response.sendEmailError)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 0) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an User Task when the email verification returns empty`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.sendEmailVerification(any()) } returns Response.Empty

        // Act
        val response = useCase(credential)

        // Assert
        assertEquals(response.user, fbUser)
        assertTrue(response.userCreated)
        assertFalse(response.emailSent)
        assertNull(response.createUserError)
        assertTrue(response.sendEmailError is InvalidResponseException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

    @Test
    fun `should return an User Task when the email verification returns error`() = runTest {
        // Arrange
        coEvery { authRepo.createUserWithEmail(any(), any()) } returns Response.Success(fbUser)
        coEvery { authRepo.sendEmailVerification(any()) } returns Response.Error(
            NullPointerException("Simulated")
        )

        // Act
        val response = useCase(credential)

        // Assert
        assertEquals(response.user, fbUser)
        assertTrue(response.userCreated)
        assertFalse(response.emailSent)
        assertNull(response.createUserError)
        assertTrue(response.sendEmailError is NullPointerException)

        coVerify(exactly = 1) {
            authRepo.createUserWithEmail(credential.email, credential.password)
        }
        coVerify(exactly = 1) {
            authRepo.sendEmailVerification(any())
        }
    }

}