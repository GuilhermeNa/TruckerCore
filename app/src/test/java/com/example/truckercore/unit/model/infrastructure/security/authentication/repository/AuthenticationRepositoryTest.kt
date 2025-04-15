package com.example.truckercore.unit.model.infrastructure.security.authentication.repository

import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepositoryImpl
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationRepositoryTest : KoinTest {

    // Injections
    private val auth: FirebaseAuth by inject()
    private val authRepository: AuthenticationRepository by inject()

    // Mocks
    private val email = "email"
    private val pass = "password"
    private val fbUid = "uid"
    private val fbUser: FirebaseUser = mockk(relaxed = true) {
        every { uid } returns fbUid
    }
    private val profileChange: UserProfileChangeRequest = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        startKoin {
            modules(module {
                single<FirebaseAuth> { mockk(relaxed = true) }
                single { AuthenticationAppErrorFactory }
                single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
            })
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing createUserWithEmail()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return Success when authenticate with email and return uid`() = runTest {
        // Arrange
        val task = mockk<Task<AuthResult>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result.user } returns fbUser
        }

        every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }

        // Call
        val response = authRepository.createUserWithEmail(email, pass)

        // Assert
        assertTrue(response is AppResult.Success)
        assertEquals(response.data, fbUser)
        verify(exactly = 1) {
            auth.createUserWithEmailAndPassword(email, pass)
            task.addOnCompleteListener(any())
        }
    }

    @Test
    fun `should return ErrorResult when user was authenticated but user not found`() =
        runTest {
            // Arrange
            val task = mockk<Task<AuthResult>> {
                every { exception } returns null
                every { isSuccessful } returns true
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result.user } returns null
            }

            every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.createUserWithEmail(email, pass)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is NewEmailErrCode.UnsuccessfulTask)
            verify(exactly = 1) {
                auth.createUserWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }
        }

    @Test
    fun `should return Response Error when new user creation with email failed`() =
        runTest {
            // Arrange
            val task = mockk<Task<AuthResult>> {
                every { exception } returns FirebaseNetworkException("Simulated exception.")
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result.user } returns null
            }

            every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            val response = authRepository.createUserWithEmail(email, pass)

            // Assert
            assertTrue(response is AppResult.Error)
            assertTrue(response.exception.errorCode is NewEmailErrCode.Network)
            verify(exactly = 1) {
                auth.createUserWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }

        }

    //----------------------------------------------------------------------------------------------
    // Testing sendEmailVerification()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return SuccessResult when email verification have been sent`() = runTest {
        // Arrange
        every { auth.currentUser } returns fbUser

        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { fbUser.sendEmailVerification() } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call
        val result = authRepository.sendEmailVerification()

        // Assert
        assertTrue(result is AppResult.Success)
        verify(exactly = 1) {
            fbUser.sendEmailVerification()
            task.addOnCompleteListener(any())
        }
    }

    @Test
    fun `should return ErrorResult when no exception was thrown and task is not successful`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns fbUser

            val task = mockk<Task<Void>> {
                every { exception } returns null
                every { isSuccessful } returns false
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result } returns null
            }

            every { fbUser.sendEmailVerification() } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.sendEmailVerification()

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is SendEmailVerificationErrCode.UnsuccessfulTask)
            verify(exactly = 1) {
                fbUser.sendEmailVerification()
                task.addOnCompleteListener(any())
            }
        }

    @Test
    fun `should return ErrorResult when an exception was thrown while sending verification email`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns fbUser

            val task = mockk<Task<Void>> {
                every { exception } returns NullPointerException("Simulated")
                every { isSuccessful } returns false
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result } returns null
            }

            every { fbUser.sendEmailVerification() } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.sendEmailVerification()

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is SendEmailVerificationErrCode.Unknown)
            verify(exactly = 1) {
                fbUser.sendEmailVerification()
                task.addOnCompleteListener(any())
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing updateUserProfile()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return SuccessResult when user profile was updated`() = runTest {
        // Arrange
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { auth.currentUser } returns fbUser
        every { fbUser.updateProfile(any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call
        val result = authRepository.updateUserProfile(profileChange)

        // Assert
        assertTrue(result is AppResult.Success)
        verify(exactly = 1) {
            fbUser.updateProfile(profileChange)
            task.addOnCompleteListener(any())
        }
    }

    @Test
    fun `should return an ErrorResult when no exception is thrown but the task is unsuccessful while updating profile`() =
        runTest {
            // Arrange
            val task = mockk<Task<Void>> {
                every { exception } returns null
                every { isSuccessful } returns false
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result } returns null
            }

            every { auth.currentUser } returns fbUser
            every { fbUser.updateProfile(any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.updateUserProfile(profileChange)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is SendEmailVerificationErrCode.UnsuccessfulTask)
            verify(exactly = 1) {
                fbUser.updateProfile(profileChange)
                task.addOnCompleteListener(any())
            }
        }

    @Test
    fun `should return an ErrorResult when an exception is thrown while updating profile`() =
        runTest {
            // Arrange
            val task = mockk<Task<Void>> {
                every { exception } returns NullPointerException("Simulated")
                every { isSuccessful } returns false
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result } returns null
            }

            every { auth.currentUser } returns fbUser
            every { fbUser.updateProfile(any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.updateUserProfile(profileChange)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is SendEmailVerificationErrCode.Unknown)
            verify(exactly = 1) {
                fbUser.updateProfile(profileChange)
                task.addOnCompleteListener(any())
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing observeEmailValidation()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return SuccessResult when email is verified`() = runTest {
        // Arrange
        every { auth.currentUser } returns fbUser
        every { fbUser.isEmailVerified } returns true

        // Act && Arrange
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            authRepository.observeEmailValidation().collect { result ->
                assertTrue(result is AppResult.Success)
                verify(exactly = 1) {
                    auth.currentUser
                    fbUser.reload()
                    fbUser.isEmailVerified
                }
            }
        }

    }

    @Test
    fun `should return ErrorResult when user is not found while verifying email`() = runTest {
        // Arrange
        every { auth.currentUser } returns null

        // Act && Arrange
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            authRepository.observeEmailValidation().collect { result ->
                assertTrue(result is AppResult.Error)
                assertTrue(result.exception.errorCode is ObserveEmailValidationErrCode.UserNotFound)
                verify(exactly = 1) {
                    auth.currentUser
                }
                verify(exactly = 0) {
                    fbUser.reload()
                    fbUser.isEmailVerified
                }
            }
        }

    }

    @Test
    fun `should not emit a return when email is not verified`() = runTest {
        // Arrange
        every { auth.currentUser } returns fbUser
        every { fbUser.isEmailVerified } returns false

        // Act && Arrange
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            // Check if this exception occurs because the flow should emit nothing
            assertThrows<NoSuchElementException> {
                authRepository.observeEmailValidation().first()
            }

            verify(exactly = 1) {
                auth.currentUser
                fbUser.isEmailVerified
            }
        }

        advanceTimeBy(200)
        job.cancel()

    }

    //----------------------------------------------------------------------------------------------
    // Testing signIn()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return SuccessResult when signIn works`() = runTest {
        // Arrange
        val task = mockk<Task<AuthResult>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { auth.signInWithEmailAndPassword(any(), any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }

        // Call
        val result = authRepository.signIn(email, pass)

        // Assert
        assertTrue(result is AppResult.Success)

        verify(exactly = 1) {
            auth.signInWithEmailAndPassword(email, pass)
            task.addOnCompleteListener(any())
            task.isSuccessful
        }

    }

    @Test
    fun `should return ErrorResult when signIn throws an exception`() =
        runTest {
            // Arrange
            val task = mockk<Task<AuthResult>> {
                every { exception } returns NullPointerException("Simulated")
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result.user } returns null
            }

            every { auth.signInWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.signIn(email, pass)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is SignInErrCode.UnknownError)

            verify(exactly = 1) {
                auth.signInWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }
        }

    @Test
    fun `should return ErrorResult when signIn task returns as not successful`() =
        runTest {
            // Arrange
            val task = mockk<Task<AuthResult>> {
                every { exception } returns null
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result.user } returns null
            }

            every { auth.signInWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            val result = authRepository.signIn(email, pass)

            // Assert
            assertTrue(result is AppResult.Error)
            assertTrue(result.exception.errorCode is SignInErrCode.UnsuccessfulTask)
            verify(exactly = 1) {
                auth.signInWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
                task.isSuccessful
            }

        }

    //----------------------------------------------------------------------------------------------
    // Testing signOut()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should call signOut from firebase auth`() {
        // Call && Assert
        authRepository.signOut()
        verify(exactly = 1) { auth.signOut() }
    }

    //----------------------------------------------------------------------------------------------
    // Testing getCurrentUser()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should call currentUser from firebase auth`() {
        // Arrange
        every { auth.currentUser } returns fbUser

        // Call
        val result = authRepository.getCurrentUser()

        // Assert
        assertEquals(fbUser, result)
        verify(exactly = 1) { auth.currentUser }
    }

}