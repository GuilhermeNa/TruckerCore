package com.example.truckercore.unit.model.infrastructure.data_source.firebase.auth

import com.example.truckercore.model.infrastructure.data_source.firebase._auth.FirebaseAuthDataSource
import com.example.truckercore.model.infrastructure.data_source.firebase._auth.FirebaseAuthDataSourceImpl
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.NullFirebaseUserException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class FirebaseAuthDataSourceTest : KoinTest {

    // Injection
    private val auth: FirebaseAuth by inject()
    private val dataSource: FirebaseAuthDataSource by inject()

    // Data Provider
    private val provider = DataProvider()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(module {
                single<FirebaseAuth> { mockk(relaxed = true) }
                single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
            })
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing createUserWithEmail()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return FirebaseUser when creating a new user`() = runTest {
        // Arrange
        val task = provider.successAuthTask
        val email = provider.email
        val pass = provider.pass

        every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }

        // Call
        val result = dataSource.createUserWithEmail(email, pass)

        // Assert
        assertEquals(result, provider.fbUser)
        verify(exactly = 1) {
            auth.createUserWithEmailAndPassword(email, pass)
            task.addOnCompleteListener(any())
        }
    }

    @Test
    fun `should throw IncompleteTaskException when no exception is thrown but task failed while creating a new user`() =
        runTest {
            // Arrange
            val task = provider.incompleteAuthTask
            val email = provider.email
            val pass = provider.pass

            every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            assertThrows<IncompleteTaskException> {
                dataSource.createUserWithEmail(email, pass)
            }

            // Assert
            verify(exactly = 1) {
                auth.createUserWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }
        }

    @Test
    fun `should throw received exception when new user creation task return an exception`() =
        runTest {
            // Arrange
            val task = provider.errorAuthTask
            val email = provider.email
            val pass = provider.pass

            every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            assertThrows<NullPointerException> {
                dataSource.createUserWithEmail(email, pass)
            }

            // Assert
            verify(exactly = 1) {
                auth.createUserWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing sendEmailVerification()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should run correctly when email verification have been sent`() = runTest {
        // Arrange
        every { auth.currentUser } returns provider.fbUser
        every { provider.fbUser.sendEmailVerification() } returns provider.successVoidTask
        every { provider.successVoidTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(provider.successVoidTask)
            provider.successVoidTask
        }

        // Call
        assertDoesNotThrow {
            dataSource.sendEmailVerification()
        }

        // Assert
        verify(exactly = 1) {
            auth.currentUser
            provider.fbUser.sendEmailVerification()
            provider.successVoidTask.addOnCompleteListener(any())
            provider.successVoidTask.exception
        }

    }

    @Test
    fun `should throw NullFirebaseUserException when the email verification could not found a logged user`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns null

            // Call
            assertThrows<NullFirebaseUserException> {
                dataSource.sendEmailVerification()
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
            }
            verify(exactly = 0) {
                provider.fbUser.sendEmailVerification()
                provider.incompleteVoidTask.addOnCompleteListener(any())
                provider.incompleteVoidTask.exception
            }

        }

    @Test
    fun `should throw IncompleteTaskException when the email verification does not throw exception and task is unsuccessful`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns provider.fbUser
            every { provider.fbUser.sendEmailVerification() } returns provider.incompleteVoidTask
            every { provider.incompleteVoidTask.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(provider.incompleteVoidTask)
                provider.incompleteVoidTask
            }

            // Call
            assertThrows<IncompleteTaskException> {
                dataSource.sendEmailVerification()
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
                provider.fbUser.sendEmailVerification()
                provider.incompleteVoidTask.addOnCompleteListener(any())
                provider.incompleteVoidTask.exception
            }

        }

    @Test
    fun `should throw received exception when the email verification task return an exception`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns provider.fbUser
            every { provider.fbUser.sendEmailVerification() } returns provider.errorVoidTask
            every { provider.errorVoidTask.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(provider.errorVoidTask)
                provider.errorVoidTask
            }

            // Call
            assertThrows<NullPointerException> {
                dataSource.sendEmailVerification()
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
                provider.fbUser.sendEmailVerification()
                provider.errorVoidTask.addOnCompleteListener(any())
                provider.errorVoidTask.exception
            }
            verify(exactly = 0) {
                provider.errorVoidTask.isSuccessful
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing updateUserProfile()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should run correctly when user profile was updated`() = runTest {
        // Arrange
        every { auth.currentUser } returns provider.fbUser
        every { provider.fbUser.updateProfile(any()) } returns provider.successVoidTask
        every { provider.successVoidTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(provider.successVoidTask)
            provider.successVoidTask
        }

        // Call
        dataSource.updateUserProfile(provider.profileChange)

        // Assert
        verify(exactly = 1) {
            auth.currentUser
            provider.fbUser.updateProfile(provider.profileChange)
            provider.successVoidTask.addOnCompleteListener(any())
            provider.successVoidTask.exception
            provider.successVoidTask.isSuccessful
        }
    }

    @Test
    fun `should throw NullFirebaseUserException when user profile update could not found a logged user`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns null

            // Call
            assertThrows<NullFirebaseUserException> {
                dataSource.updateUserProfile(provider.profileChange)
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
            }
            verify(exactly = 0) {
                provider.fbUser.updateProfile(provider.profileChange)
                provider.successVoidTask.addOnCompleteListener(any())
                provider.successVoidTask.exception
                provider.successVoidTask.isSuccessful
            }
        }

    @Test
    fun `should throw IncompleteTaskException when the profile update does not throw exception and task is unsuccessful`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns provider.fbUser
            every { provider.fbUser.updateProfile(any()) } returns provider.incompleteVoidTask
            every { provider.incompleteVoidTask.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(provider.incompleteVoidTask)
                provider.incompleteVoidTask
            }

            // Call
            assertThrows<IncompleteTaskException> {
                dataSource.updateUserProfile(provider.profileChange)
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
                provider.fbUser.updateProfile(provider.profileChange)
                provider.incompleteVoidTask.addOnCompleteListener(any())
                provider.incompleteVoidTask.exception
                provider.incompleteVoidTask.isSuccessful
            }
        }

    @Test
    fun `should throw received exception when the profile update task return an exception`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns provider.fbUser
            every { provider.fbUser.updateProfile(any()) } returns provider.errorVoidTask
            every { provider.errorVoidTask.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(provider.errorVoidTask)
                provider.errorVoidTask
            }

            // Call
            assertThrows<NullPointerException> {
                dataSource.updateUserProfile(provider.profileChange)
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
                provider.fbUser.updateProfile(provider.profileChange)
                provider.errorVoidTask.addOnCompleteListener(any())
                provider.errorVoidTask.exception
            }
            verify(exactly = 0) {
                provider.errorVoidTask.isSuccessful
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing signIn()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should run correctly when the email sign in works`() = runTest {
        // Arrange
        every { auth.currentUser } returns provider.fbUser
        every { auth.signInWithEmailAndPassword(any(), any()) } returns provider.successAuthTask
        every { provider.successAuthTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(provider.successAuthTask)
            provider.successAuthTask
        }

        // Call
        dataSource.signInWithEmail(provider.email, provider.pass)

        // Assert
        verify(exactly = 1) {
            auth.currentUser
            auth.signInWithEmailAndPassword(provider.email, provider.pass)
            provider.successAuthTask.addOnCompleteListener(any())
            provider.successAuthTask.exception
            provider.successAuthTask.isSuccessful
        }
    }

    @Test
    fun `should throw NullFirebaseUserException when the email sign in could not found a logged user`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns null

            // Call
            assertThrows<IncompleteTaskException> {
                dataSource.signInWithEmail(provider.email, provider.pass)
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser

            }
            verify(exactly = 0) {
                auth.signInWithEmailAndPassword(provider.email, provider.pass)
                provider.incompleteAuthTask.addOnCompleteListener(any())
                provider.incompleteAuthTask.exception
                provider.incompleteAuthTask.isSuccessful
            }
        }

    @Test
    fun `should throw IncompleteTaskException when the email sign in does not throw exception and is unsuccessful`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns provider.fbUser
            every {
                auth.signInWithEmailAndPassword(any(), any())
            } returns provider.incompleteAuthTask
            every { provider.incompleteAuthTask.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(provider.incompleteAuthTask)
                provider.incompleteAuthTask
            }

            // Call
            assertThrows<IncompleteTaskException> {
                dataSource.signInWithEmail(provider.email, provider.pass)
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
                auth.signInWithEmailAndPassword(provider.email, provider.pass)
                provider.incompleteAuthTask.addOnCompleteListener(any())
                provider.incompleteAuthTask.exception
                provider.incompleteAuthTask.isSuccessful
            }
        }

    @Test
    fun `should throw received exception when the email sign in task return an exception`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns provider.fbUser
            every { auth.signInWithEmailAndPassword(any(), any()) } returns provider.errorAuthTask
            every { provider.errorAuthTask.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(provider.errorAuthTask)
                provider.errorAuthTask
            }

            // Call
            assertThrows<NullPointerException> {
                dataSource.signInWithEmail(provider.email, provider.pass)
            }

            // Assert
            verify(exactly = 1) {
                auth.currentUser
                auth.signInWithEmailAndPassword(provider.email, provider.pass)
                provider.errorAuthTask.addOnCompleteListener(any())
                provider.errorAuthTask.exception
            }
            verify(exactly = 0) {
                provider.errorAuthTask.isSuccessful
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing signOut()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should call signOut from firebase auth`() {
        // Call
        dataSource.signOut()
        // Assert
        verify(exactly = 1) { auth.signOut() }
    }

    //----------------------------------------------------------------------------------------------
    // Testing getCurrentUser()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should call currentUser from firebase auth`() {
        // Arrange
        every { auth.currentUser } returns provider.fbUser

        // Call
        val result = dataSource.getCurrentUser()

        // Assert
        assertEquals(provider.fbUser, result)
        verify(exactly = 1) { auth.currentUser }
    }

    //----------------------------------------------------------------------------------------------
    // Testing observeEmailValidation()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should await until email verification`() = runTest {
        // Arrange
        every { auth.currentUser } returns provider.fbUser
        every { provider.fbUser.isEmailVerified } returns true

        // Act
        dataSource.observeEmailValidation()

        // Assert
        verify(exactly = 1) {
            auth.currentUser
            provider.fbUser.reload()
            provider.fbUser.isEmailVerified
        }
    }

    @Test
    fun `should throw NullFirebaseUserException when observe validation could not found a logged user`() =
        runTest {
            // Arrange
            every { auth.currentUser } returns null

            // Act
            assertThrows<NullFirebaseUserException> {
                dataSource.observeEmailValidation()
            }

            // Assert
            verify(exactly = 1) { auth.currentUser }
            verify(exactly = 0) {
                provider.fbUser.reload()
                provider.fbUser.isEmailVerified
            }
        }

}

private class DataProvider {
    val email = "email"
    val pass = "password"
    val fbUid = "uid"
    val fbUser: FirebaseUser = mockk(relaxed = true) { every { uid } returns fbUid }

    val profileChange: UserProfileChangeRequest = mockk(relaxed = true)

    val successAuthTask = mockk<Task<AuthResult>> {
        every { exception } returns null
        every { isSuccessful } returns true
        every { isComplete } returns true
        every { isCanceled } returns false
        every { result.user } returns fbUser
    }

    val errorAuthTask = mockk<Task<AuthResult>> {
        every { exception } returns NullPointerException("Simulated")
        every { isSuccessful } returns false
        every { isComplete } returns false
        every { isCanceled } returns false
        every { result.user } returns null
    }

    val incompleteAuthTask = mockk<Task<AuthResult>> {
        every { exception } returns null
        every { isSuccessful } returns false
        every { isComplete } returns false
        every { isCanceled } returns false
        every { result.user } returns null
    }

    val successVoidTask = mockk<Task<Void>> {
        every { exception } returns null
        every { isSuccessful } returns true
        every { isComplete } returns true
        every { isCanceled } returns false
        every { result } returns null
    }

    val errorVoidTask = mockk<Task<Void>> {
        every { exception } returns NullPointerException("Simulated")
        every { isSuccessful } returns false
        every { isComplete } returns false
        every { isCanceled } returns false
        every { result } returns null
    }

    val incompleteVoidTask = mockk<Task<Void>> {
        every { exception } returns null
        every { isSuccessful } returns false
        every { isComplete } returns false
        every { isCanceled } returns false
        every { result } returns null
    }

}

