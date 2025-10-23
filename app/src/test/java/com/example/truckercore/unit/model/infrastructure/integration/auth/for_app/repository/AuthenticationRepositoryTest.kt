package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore._test_data_provider.TestCredentialProvider
import com.example.truckercore.data.infrastructure.data_source.auth.abstractions.AuthSource
import com.example.truckercore.layers.data.data_source.auth.errors.auth_source_exceptions.InvalidCredentialsException
import com.example.truckercore.data.data_source.auth.errors.auth_source_exceptions.SessionInactiveException
import com.example.truckercore.data.infrastructure.data_source.auth.errors.auth_source_exceptions.TaskFailureException
import com.example.truckercore.data.infrastructure.repository.auth.error_factory.AuthRepositoryErrorFactory
import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.SignInErrCode
import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.UpdateUserProfileErrCode
import com.example.truckercore.data.infrastructure.repository.auth.contracts.AuthenticationRepository
import com.example.truckercore.data.infrastructure.repository.auth.impl.AuthenticationRepositoryImpl
import com.example.truckercore.core.classes.AppResult
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AuthenticationRepositoryTest : KoinTest {

    private val authSource: AuthSource by inject()
    private val repository: AuthenticationRepository by inject()

    private val provider = TestCredentialProvider()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<AuthSource> { mockk(relaxed = true) }
                    single { AuthRepositoryErrorFactory() }
                    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing createUserWithEmail
    //----------------------------------------------------------------------------------------------
    @Test
    fun `createUserWithEmail should return Success when AuthSource does not throw exception`() =
        runBlocking {
            // Arrange
            val email = provider.email
            val password = provider.password

            coEvery { authSource.createUserWithEmail(email, password) } just Runs

            // Act
            val result = repository.createUserWithEmail(email, password)

            // Assert
            assertTrue(result is AppResult.Success)
            coVerify { authSource.createUserWithEmail(email, password) }
        }

    @Test
    fun `createUserWithEmail should return Error when exception is thrown by AuthSource`() =
        runBlocking {
            // Arrange
            val email = provider.email
            val password = provider.password

            val exception = TaskFailureException()

            coEvery { authSource.createUserWithEmail(email, password) } throws exception

            // Act
            val result = repository.createUserWithEmail(email, password)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(
                (result as AppResult.Error).exception.errorCode
                        is NewEmailErrCode.TaskFailure
            )
        }

    //----------------------------------------------------------------------------------------------
    // Testing sendEmailVerification
    //----------------------------------------------------------------------------------------------
    @Test
    fun `sendingEmailVerification should return Success when AuthSource does not throw exception`() =
        runBlocking {
            // Arrange
            coEvery { authSource.sendEmailVerification() } just Runs

            // Act
            val result = repository.sendEmailVerification()

            // Assert
            assertTrue(result is AppResult.Success)
            coVerify { authSource.sendEmailVerification() }
        }

    @Test
    fun `sendingEmailVerification should return Error when exception is thrown by AuthSource`() =
        runBlocking {
            // Arrange
            val exception = TaskFailureException()

            coEvery { authSource.sendEmailVerification() } throws exception

            // Act
            val result = repository.sendEmailVerification()

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(
                (result as AppResult.Error).exception.errorCode
                        is SendEmailVerificationErrCode.TaskFailure
            )
        }

    //----------------------------------------------------------------------------------------------
    // Testing updateUserProfile
    //----------------------------------------------------------------------------------------------
    @Test
    fun `updateUserProfile should return Success when AuthSource does not throw exception`() =
        runBlocking {
            // Arrange
            val profile = provider.userProfile

            coEvery { authSource.updateUserProfile(profile) } just Runs

            // Act
            val result = repository.updateUserProfile(profile)

            // Assert
            assertTrue(result is AppResult.Success)
            coVerify { authSource.updateUserProfile(profile) }
        }

    @Test
    fun `updateUserProfile should return Error when exception is thrown by AuthSource`() =
        runBlocking {
            // Arrange
            val profile = provider.userProfile

            val exception = TaskFailureException()

            coEvery { authSource.updateUserProfile(profile) } throws exception

            // Act
            val result = repository.updateUserProfile(profile)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(
                (result as AppResult.Error).exception.errorCode
                        is UpdateUserProfileErrCode.TaskFailure
            )
        }

    //----------------------------------------------------------------------------------------------
    // Testing observeEmailValidation
    //----------------------------------------------------------------------------------------------
    @Test
    fun `observeEmailValidation should return Success when AuthSource does not throw exception`() =
        runBlocking {
            // Arrange
            coEvery { authSource.observeEmailValidation() } just Runs

            // Act
            val result = repository.observeEmailValidation()

            // Assert
            assertTrue(result is AppResult.Success)
            coVerify { authSource.observeEmailValidation() }
        }

    @Test
    fun `observeEmailValidation should return Error when exception is thrown by AuthSource`() =
        runBlocking {
            // Arrange
            val exception =
                com.example.truckercore.data.data_source.auth.errors.auth_source_exceptions.SessionInactiveException()

            coEvery { authSource.observeEmailValidation() } throws exception

            // Act
            val result = repository.observeEmailValidation()

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(
                (result as AppResult.Error).exception.errorCode
                        is ObserveEmailValidationErrCode.SessionInactive
            )
        }

    //----------------------------------------------------------------------------------------------
    // Testing signIn
    //----------------------------------------------------------------------------------------------
    @Test
    fun `signIn should return Success when AuthSource does not throw exception`() =
        runBlocking {
            // Arrange
            val email = provider.email
            val password = provider.password

            coEvery { authSource.signInWithEmail(email, password) } just Runs

            // Act
            val result = repository.signIn(email, password)

            // Assert
            assertTrue(result is AppResult.Success)
            coVerify { authSource.signInWithEmail(email, password) }
        }

    @Test
    fun `signIn should return Error when exception is thrown by AuthSource`() =
        runBlocking {
            // Arrange
            val email = provider.email
            val password = provider.password
            val exception =
                com.example.truckercore.layers.data.data_source.auth.errors.auth_source_exceptions.InvalidCredentialsException()

            coEvery { authSource.signInWithEmail(email, password) } throws exception

            // Act
            val result = repository.signIn(email, password)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(
                (result as AppResult.Error).exception.errorCode
                        is SignInErrCode.InvalidCredentials
            )
        }

    //----------------------------------------------------------------------------------------------
    // Testing signOut
    //----------------------------------------------------------------------------------------------
    @Test
    fun `signOut should invoke signOut on AuthSource`() {
        // Act
        repository.signOut()

        // Assert
        verify { authSource.signOut() }
    }

}