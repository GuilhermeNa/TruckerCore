package com.example.truckercore.unit.model.infrastructure.security.authentication.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.configs.di.firebaseModule
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.model.infrastructure.security.authentication.entity.LoggedUser
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthServiceImpl
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetLoggedUserUseCase
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AuthServiceImplTest : KoinTest {

    private val authRepo: FirebaseAuthRepository by inject()
    private val createAccess: CreateNewSystemAccessUseCase by inject()
    private val getLoggedUser: GetLoggedUserUseCase by inject()
    private val service: AuthService by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetLoggedUserUseCase> { mockk() }
                        single<FirebaseAuthRepository> { mockk() }
                        single<CreateNewSystemAccessUseCase> { mockk() }
                        single<AuthService> { AuthServiceImpl(mockk(), get(), get(), get()) }
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
        val email = "abc@mail.com"
        val password = "123456"
        val credentials = Credentials(email = email, password = password)
        val createdId = "uid123"

        every { authRepo.createUserWithEmailAndPassword(any(), any()) } returns flowOf(
            Response.Success(createdId)
        )

        // Call
        val result = service.createUserWithEmailAndPassword(credentials).single()

        // Assertions
        assertEquals(createdId, (result as Response.Success).data)
        verify {
            authRepo.createUserWithEmailAndPassword(email, password)
        }

    }

    @Test
    fun `should call auth repository for sign in`() = runTest {
        // Arrange
        val email = "abc@mail.com"
        val password = "123456"
        val credentials = Credentials(email, password)
        val id = "uid"
        val fbUser: FirebaseUser = mockk { every { uid } returns id}
        val loggedUser: LoggedUser = mockk()

        every { authRepo.signIn(email, password) } returns flowOf(Response.Success(Unit))
        every { authRepo.getCurrentUser() } returns fbUser
        every { getLoggedUser.execute(any()) } returns flowOf(Response.Success(loggedUser))

        //Call
        val result = service.signIn(credentials).single()

        // Assertions
        assertTrue(result is Response.Success)
        verifyOrder {
            authRepo.signIn(email, password)
            authRepo.getCurrentUser()
            getLoggedUser.execute(id)
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
        val loggedUser: LoggedUser = mockk()

        every { authRepo.getCurrentUser() } returns fbUser
        every { getLoggedUser.execute(any()) } returns flowOf(Response.Success(loggedUser))

        // Act
        val result = service.getLoggedUser().single()

        // Assert
        assertTrue(result is Response.Success)
        verifyOrder {
            authRepo.getCurrentUser()
            getLoggedUser.execute(fbUid)
        }
    }

    @Test
    fun `should return an Response Error when the firebase User was not found`() = runTest {
        // Arrange
        every { authRepo.getCurrentUser() } returns null

        // Act
        val result = service.getLoggedUser().single()

        // Assert
        assertTrue(result is Response.Error)
        assertTrue(result.exception is NullFirebaseUserException)

    }


}