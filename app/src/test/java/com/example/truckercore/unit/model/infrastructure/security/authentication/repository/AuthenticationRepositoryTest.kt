/*
package com.example.truckercore.unit.model.infrastructure.security.authentication.repository

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.UpdateUserProfileErrCode
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepositoryImpl
import com.example.truckercore._utils.classes.AppResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
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

class AuthenticationRepositoryTest : KoinTest {

    // Injections
    private val dataSource: AuthSource by inject()
    private val repository: AuthenticationRepository by inject()

    // Data Provider
    private val provider = DataProvider()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(module {
                single<AuthSource> { mockk(relaxed = true) }
                single<AuthenticationAppErrorFactory> { AuthenticationAppErrorFactory }
                single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
            })
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    companion object {
        private const val EMAIL = "email"
        private const val PASS = "pass"
    }

    //----------------------------------------------------------------------------------------------
    // createUserWithEmail()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return Success when createUserWithEmail completes successfully`() = runTest {
        // Arrange
        coEvery { dataSource.createUserWithEmail(any(), any()) } returns provider.fbUser

        // Act
        val result = repository.createUserWithEmail(EMAIL, PASS)

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(provider.fbUser, (result as AppResult.Success).data)
        coVerify(exactly = 1) { dataSource.createUserWithEmail(EMAIL, PASS) }
    }

    @Test
    fun `should return Error when createUserWithEmail throws exception`() = runTest {
        // Arrange
        coEvery { dataSource.createUserWithEmail(any(), any()) } throws IncompleteTaskException()

        // Act
        val result = repository.createUserWithEmail(EMAIL, PASS)

        // Assert
        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).exception.errorCode is NewEmailErrCode.UnsuccessfulTask)
        coVerify(exactly = 1) {
            dataSource.createUserWithEmail(EMAIL, PASS)
        }
    }

    //----------------------------------------------------------------------------------------------
    // sendEmailVerification()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return Success when sendEmailVerification completes successfully`() = runTest {
        // Arrange
        coEvery { dataSource.sendEmailVerification() } just Runs

        // Act
        val result = repository.sendEmailVerification()

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(Unit, (result as AppResult.Success).data)
        coVerify(exactly = 1) { dataSource.sendEmailVerification() }
    }

    @Test
    fun `should return Error when sendEmailVerification throws exception`() = runTest {
        // Arrange
        coEvery { dataSource.sendEmailVerification() } throws IncompleteTaskException()

        // Act
        val result = repository.sendEmailVerification()

        // Assert
        assertTrue(result is AppResult.Error)
        assertTrue(
            (result as AppResult.Error).exception.errorCode is
                    SendEmailVerificationErrCode.UnsuccessfulTask
        )
        coVerify(exactly = 1) { dataSource.sendEmailVerification() }
    }

    //----------------------------------------------------------------------------------------------
    // updateUserProfile()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return Success when updateUserProfile completes successfully`() = runTest {
        // Arrange
        coEvery { dataSource.updateUserProfile(any()) } just Runs

        // Act
        val result = repository.updateUserProfile(provider.profileChange)

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(Unit, (result as AppResult.Success).data)
        coVerify(exactly = 1) { dataSource.updateUserProfile(provider.profileChange) }
    }

    @Test
    fun `should return Error when updateUserProfile throws exception`() = runTest {
        // Arrange
        coEvery { dataSource.updateUserProfile(any()) } throws IncompleteTaskException()

        // Act
        val result = repository.updateUserProfile(provider.profileChange)

        // Assert
        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).exception.errorCode is UpdateUserProfileErrCode.UnsuccessfulTask)
        coVerify(exactly = 1) { dataSource.updateUserProfile(provider.profileChange) }
    }

    //----------------------------------------------------------------------------------------------
    // observeEmailValidation()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return Success when observeEmailValidation completes successfully`() = runTest {
        // Arrange
        coEvery { dataSource.observeEmailValidation() } just Runs

        // Act
        val result = repository.observeEmailValidation()

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(Unit, (result as AppResult.Success).data)
        coVerify(exactly = 1) { dataSource.observeEmailValidation() }
    }

    @Test
    fun `should return Error when observeEmailValidation throws exception`() = runTest {
        // Arrange
        coEvery { dataSource.observeEmailValidation() } throws NullFirebaseUserException()

        // Act
        val result = repository.observeEmailValidation()

        // Assert
        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).exception.errorCode is ObserveEmailValidationErrCode.SessionInactive)
        coVerify(exactly = 1) { dataSource.observeEmailValidation() }
    }

    //----------------------------------------------------------------------------------------------
    // signIn()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return Success when signIn completes successfully`() = runTest {
        // Arrange
        coEvery { dataSource.signInWithEmail(any(), any()) } just Runs

        // Act
        val result = repository.signIn(EMAIL, PASS)

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(Unit, (result as AppResult.Success).data)
        coVerify(exactly = 1) { dataSource.signInWithEmail(EMAIL, PASS) }
    }

    @Test
    fun `should return Error when signIn throws exception`() = runTest {
        // Arrange
        coEvery { dataSource.signInWithEmail(any(), any()) } throws IncompleteTaskException()

        // Act
        val result = repository.signIn(EMAIL, PASS)

        // Assert
        assertTrue(result is AppResult.Error)
        assertTrue((result as AppResult.Error).exception.errorCode is SignInErrCode.UnsuccessfulTask)
        coVerify(exactly = 1) { dataSource.signInWithEmail(EMAIL, PASS) }
    }

    //----------------------------------------------------------------------------------------------
    // signOut()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should call signOut from dataSource`() {
        // Act
        repository.signOut()

        // Assert
        verify(exactly = 1) { dataSource.signOut() }
    }

    //----------------------------------------------------------------------------------------------
    // getCurrentUser()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return current user from dataSource`() {
        // Arrange
        every { dataSource.getCurrentUser() } returns provider.fbUser

        // Act
        val result = repository.getCurrentUser()

        // Assert
        assertEquals(provider.fbUser, result)
        verify(exactly = 1) { dataSource.getCurrentUser() }
    }

}

private class DataProvider {

    val fbUser: FirebaseUser = mockk(relaxed = true)
    val profileChange: UserProfileChangeRequest = mockk(relaxed = true)

}*/
