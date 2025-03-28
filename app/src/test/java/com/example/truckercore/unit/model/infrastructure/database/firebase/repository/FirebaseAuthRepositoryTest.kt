package com.example.truckercore.unit.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepositoryImpl
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class FirebaseAuthRepositoryTest : KoinTest {

    // Injections
    private val auth: FirebaseAuth by inject()
    private val authRepository: FirebaseAuthRepository by inject()

    // Mocks
    private val fbUid = "uid"
    private val fbUser: FirebaseUser = mockk(relaxed = true) {
        every { uid } returns fbUid
    }

    @BeforeEach
    fun setup() {
        //mockStaticLog()
        //mockStaticTask()

        startKoin {
            modules(module {
                single<FirebaseAuth> { mockk(relaxed = true) }
                single<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(get()) }
            })
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing createUserWithEmail()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should authenticate with email and return uid`() = runTest {
        // Arrange
        val email = "email"
        val pass = "password"
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
        assertTrue(response is Response.Success)
        assertEquals(response.data, fbUser)
        verify(exactly = 1) {
            auth.createUserWithEmailAndPassword(email, pass)
            task.addOnCompleteListener(any())
        }
    }

    @Test
    fun `should throw IncompleteTaskException when the task returns an null user for email auth`() =
        runTest {
            // Arrange
            val email = "email"
            val pass = "password"
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

            // Call && Assert
            assertThrows<IncompleteTaskException> {
                authRepository.createUserWithEmail(email, pass)
            }
            verify(exactly = 1) {
                auth.createUserWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }
        }

    @Test
    fun `should return Response Error when the task returns npe error for email auth`() =
        runTest {
            // Arrange
            val email = "email"
            val pass = "password"
            val task = mockk<Task<AuthResult>> {
                every { exception } returns NullPointerException("Simulated exception.")
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result.user } returns fbUser
            }

            every { auth.createUserWithEmailAndPassword(any(), any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call && Assert
            val response = authRepository.createUserWithEmail(email, pass)

            assertTrue(response is Response.Error)
            assertTrue(response.exception is NullPointerException)
            verify(exactly = 1) {
                auth.createUserWithEmailAndPassword(email, pass)
                task.addOnCompleteListener(any())
            }

        }

    //----------------------------------------------------------------------------------------------
    // Testing sendEmailVerification()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should send email verification`() = runTest {
        // Arrange
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
        val response = authRepository.sendEmailVerification(fbUser)

        // Assert
        assertTrue(response is Response.Success)
        verify(exactly = 1) {
            fbUser.sendEmailVerification()
            task.addOnCompleteListener(any())
        }
    }

    @Test
    fun `should throw IncompleteTaskException when returns unsuccessful task`() =
        runTest {
            // Arrange
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

            // Call && Assert
            assertThrows<IncompleteTaskException> {
                authRepository.sendEmailVerification(fbUser)
                verify(exactly = 1) {
                    fbUser.sendEmailVerification()
                    task.addOnCompleteListener(any())
                }
            }
        }

    @Test
    fun `should return Response Error when the task returns npe error for email verification`() =
        runTest {
            // Arrange
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
            val response = authRepository.sendEmailVerification(fbUser)

            // Assert
            assertTrue(response is Response.Error)
            assertTrue(response.exception is NullPointerException)
            verify(exactly = 1) {
                fbUser.sendEmailVerification()
                task.addOnCompleteListener(any())
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing createUserWithPhone()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should authenticate with phone`() = runTest {
        // Arrange
        val credential: PhoneAuthCredential = mockk(relaxed = true)
        val task = mockk<Task<AuthResult>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result.user } returns fbUser
        }

        every { auth.signInWithCredential(any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }

        // Call
        val response = authRepository.createUserWithPhone(credential)

        // Assert
        assertTrue(response is Response.Success)
        assertEquals(response.data, fbUid)
        verify(exactly = 1) {
            auth.signInWithCredential(credential)
            task.addOnCompleteListener(any())
        }
    }


    @Test
    fun `should throw IncompleteTaskException when the task returns an null user for phone auth`() =
        runTest {
            // Arrange
            val credential: PhoneAuthCredential = mockk(relaxed = true)
            val task = mockk<Task<AuthResult>> {
                every { exception } returns null
                every { isSuccessful } returns true
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result.user } returns null
            }

            every { auth.signInWithCredential(credential) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call && Assert
            assertThrows<IncompleteTaskException> {
                authRepository.createUserWithPhone(credential)
                verify(exactly = 1) {
                    auth.signInWithCredential(credential)
                    task.addOnCompleteListener(any())
                }
            }
        }

    @Test
    fun `should return Response Error when the task returns npe error for phone auth`() =
        runTest {
            // Arrange
            val credential: PhoneAuthCredential = mockk(relaxed = true)
            val task = mockk<Task<AuthResult>> {
                every { exception } returns NullPointerException("Simulated exception.")
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result.user } returns fbUser
            }

            every { auth.signInWithCredential(any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
                listener.onComplete(task)
                task
            }

            // Call
            val response = authRepository.createUserWithPhone(credential)

            // Assert
            assertTrue(response is Response.Error)
            assertTrue(response.exception is NullPointerException)
            verify(exactly = 1) {
                auth.signInWithCredential(credential)
                task.addOnCompleteListener(any())
            }
        }


    //----------------------------------------------------------------------------------------------
// Testing signIn()
//----------------------------------------------------------------------------------------------
    @Test
    fun `should signIn with email and return Success`() = runTest {
        // Arrange
        val email = "email"
        val pass = "password"
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

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            authRepository.signIn(email, pass).collect { response ->
                assertTrue(response is Response.Success)
                verify(exactly = 1) {
                    auth.signInWithEmailAndPassword(email, pass)
                    task.addOnCompleteListener(any())
                }
            }
        }
    }

    @Test
    fun `should throw IncompleteTaskException when the task returns an null user for email signIn`() =
        runTest {
            // Arrange
            val email = "email"
            val pass = "password"
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

            // Call && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<IncompleteTaskException> {
                    authRepository.signIn(email, pass).single()
                    verify(exactly = 1) {
                        auth.signInWithEmailAndPassword(email, pass)
                        task.addOnCompleteListener(any())
                    }
                }
            }
        }

    @Test
    fun `should throw NullPointerException when the task returns npe error for email signIn`() =
        runTest {
            // Arrange
            val email = "email"
            val pass = "password"
            val task = mockk<Task<AuthResult>> {
                every { exception } returns NullPointerException("Simulated exception.")
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

            // Call && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<NullPointerException> {
                    authRepository.signIn(email, pass).single()
                    verify(exactly = 1) {
                        auth.signInWithEmailAndPassword(email, pass)
                        task.addOnCompleteListener(any())
                    }
                }
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