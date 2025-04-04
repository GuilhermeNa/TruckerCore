package com.example.truckercore.unit.model.infrastructure.security.authentication.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailUserResponse
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateUserAndVerifyEmailUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetSessionInfoUseCase
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.PhoneAuthCredential
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthServiceImplTest : KoinTest {

    private val authRepo: FirebaseAuthRepository by inject()
    private val createAccess: CreateNewSystemAccessUseCase by inject()
    private val getSessionInfo: GetSessionInfoUseCase by inject()
    private val createAndVerifyUser: CreateUserAndVerifyEmailUseCase by inject()
    private val service: AuthService by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetSessionInfoUseCase> { mockk() }
                        single<FirebaseAuthRepository> { mockk() }
                        single<CreateNewSystemAccessUseCase> { mockk() }
                        single<CreateUserAndVerifyEmailUseCase> { mockk() }
                        single<AuthService> { AuthServiceImpl(mockk(), get(), get(), get(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `should call auth repository for authenticate credentials`() = runTest {
        // Arrange
        val response: NewEmailUserResponse = mockk()
        val emailAuthCredential: EmailAuthCredential = mockk(relaxed = true)


        coEvery { createAndVerifyUser(any()) } returns response

        // Call
        val result = service.createUserAndVerifyEmail(emailAuthCredential)

        // Assertions
        assertEquals(result, response)
        coVerify(exactly = 1) { createAndVerifyUser(emailAuthCredential) }
    }

    @Test
    fun `should call auth repository for authenticate with phone`() = runTest {
        // Arrange
        val uid = "123"
        val phoneCredential: PhoneAuthCredential = mockk(relaxed = true)

        coEvery { authRepo.createUserWithPhone(any()) } returns Response.Success(uid)

        // Call
        val result = service.createUserWithPhone(phoneCredential)

        // Assert
        assertTrue(result is Response.Success)
        assertEquals(result.data, uid)
        coVerify(exactly = 1) {
            authRepo.createUserWithPhone(phoneCredential)
        }
    }

   /* @Test
    fun `should call auth repository for sign in`() = runTest {
        // Arrange
        val email = "abc@mail.com"
        val password = "123456"
        val emailAuthCredential = EmailAuthCredential(email, password)
        val id = "uid"
        val fbUser: FirebaseUser = mockk { every { uid } returns id }
        val sessionInfo: SessionInfo = mockk()

        every { authRepo.signIn(email, password) } returns flowOf(Response.Success(Unit))
        every { authRepo.getCurrentUser() } returns fbUser
        every { getSessionInfo.execute(any()) } returns flowOf(Response.Success(sessionInfo))

        //Call
        val result = service.signIn(emailAuthCredential).single()

        // Assertions
        assertTrue(result is Response.Success)
        verifyOrder {
            authRepo.signIn(email, password)
            authRepo.getCurrentUser()
            getSessionInfo.execute(id)
        }
    }

    @Test
    fun `should call auth repository for sign out`() {
        // Arrange
        every { authRepo.signOut() } returns Unit

        // Call
        service.signOut()

        // Assertions
        verify { authRepo.signOut() }
    }

    @Test
    fun `should return true when the firebase user was found`() {
        // Arrange
        val fbUser: FirebaseUser = mockk(relaxed = true)
        every { authRepo.getCurrentUser() } returns fbUser

        // Act
        val result = service.thereIsLoggedUser()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `should return false when the firebase user was found`() {
        // Arrange
        every { authRepo.getCurrentUser() } returns null

        // Act
        val result = service.thereIsLoggedUser()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `should call create system useCase for create new access`() = runTest {
        // Arrange
        val requirements: NewAccessRequirements = mockk()

        every { createAccess.execute(requirements) } returns flowOf(Response.Success(Unit))

        // Call
        val result = service.createNewSystemAccess(requirements).single()

        // Assertions
        assertTrue(result is Response.Success)

    }

    @Test
    fun `should get the logged user details`() = runTest {
        // Arrange
        val fbUid = "uid"
        val fbUser: FirebaseUser = mockk { every { uid } returns fbUid }
        val sessionInfo: SessionInfo = mockk()

        every { authRepo.getCurrentUser() } returns fbUser
        every { getSessionInfo.execute(any()) } returns flowOf(Response.Success(sessionInfo))

        // Act
        val result = service.getSessionInfo().single()

        // Assert
        assertTrue(result is Response.Success)
        verifyOrder {
            authRepo.getCurrentUser()
            getSessionInfo.execute(fbUid)
        }
    }

    @Test
    fun `should return an Response Error when the firebase User was not found`() = runTest {
        // Arrange
        every { authRepo.getCurrentUser() } returns null

        // Act
        val result = service.getSessionInfo().single()

        // Assert
        assertTrue(result is Response.Error)
        assertTrue(result.exception is NullFirebaseUserException)

    }*/


}