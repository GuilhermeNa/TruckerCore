package com.example.truckercore.unit.model.infrastructure.data_source.firebase.auth

import com.example.truckercore._test_data_provider.TestCredentialProvider
import com.example.truckercore._test_data_provider.TestFirebaseAuthProvider
import com.example.truckercore._test_utils.mockStaticTextUtil
import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthSourceErrorMapper
import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthSource
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidCredentialsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.SessionInactiveException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TooManyRequestsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UnknownException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UserCollisionException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.WeakPasswordException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.reflect.KClass
import kotlin.test.assertTrue

class FirebaseAuthSourceTest : KoinTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val firebaseAuth: FirebaseAuth by inject()
    private val authSource: FirebaseAuthSource by inject()
    private val credentialProvider = TestCredentialProvider()
    private val firebaseAuthProvider = TestFirebaseAuthProvider()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<FirebaseAuth> { mockk(relaxed = true) }
                    single { FirebaseAuthSourceErrorMapper() }
                    single { FirebaseAuthSource(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            mockStaticTextUtil()
        }

        @JvmStatic
        fun provideFirebaseExceptionsForCreateUser() = arrayOf(
            Arguments.of(
                FirebaseAuthWeakPasswordException("reason", "weak", "email"),
                WeakPasswordException::class
            ),
            Arguments.of(
                FirebaseAuthInvalidCredentialsException("auth/error", "invalid creds"),
                InvalidCredentialsException::class
            ),
            Arguments.of(
                FirebaseAuthUserCollisionException("auth/error", "email exists"),
                UserCollisionException::class
            ),
            Arguments.of(
                FirebaseNetworkException("no internet"),
                NetworkException::class
            ),
            Arguments.of(
                RuntimeException("unexpected"),
                UnknownException::class
            )
        )

        @JvmStatic
        fun provideFirebaseExceptionsForSendEmail() = arrayOf(
            Arguments.of(
                SessionInactiveException(),
                SessionInactiveException::class
            ),
            Arguments.of(
                TaskFailureException(),
                TaskFailureException::class
            ),
            Arguments.of(
                RuntimeException("unexpected"),
                UnknownException::class
            )
        )

        @JvmStatic
        fun provideFirebaseExceptionsForUpdateProfile() = arrayOf(
            Arguments.of(
                FirebaseNetworkException("no internet"),
                NetworkException::class
            ),
            Arguments.of(
                SessionInactiveException(),
                SessionInactiveException::class
            ),
            Arguments.of(
                TaskFailureException(),
                TaskFailureException::class
            ),
            Arguments.of(
                RuntimeException("unexpected"),
                UnknownException::class
            )
        )

        @JvmStatic
        fun provideFirebaseExceptionsForSignIn() = arrayOf(
            Arguments.of(
                FirebaseAuthInvalidUserException("auth/user-not-found", "User not found"),
                InvalidCredentialsException::class
            ),
            Arguments.of(
                FirebaseAuthInvalidCredentialsException("auth/wrong-password", "Wrong password"),
                InvalidCredentialsException::class
            ),
            Arguments.of(
                FirebaseNetworkException("network failure"),
                NetworkException::class
            ),
            Arguments.of(
                FirebaseTooManyRequestsException("too many requests"),
                TooManyRequestsException::class
            ),
            Arguments.of(
                TaskFailureException(),
                TaskFailureException::class
            ),
            Arguments.of(
                RuntimeException("unexpected"),
                UnknownException::class
            )
        )

    }

    //----------------------------------------------------------------------------------------------
    // Testing CreateUserWithEmail
    //----------------------------------------------------------------------------------------------
    @Test
    fun `createUserWithEmail should complete successfully when task is successful`() = runTest {
        // Arrange
        val email = credentialProvider.email
        val password = credentialProvider.password

        every {
            firebaseAuth.createUserWithEmailAndPassword(email.value, password.value)
        } returns firebaseAuthProvider.mockSuccessfulAuthTask()

        // Act && Assert
        assertDoesNotThrow {
            authSource.createUserWithEmail(email, password)
        }

    }

    @Test
    fun `createUserWithEmail should throw TaskFailureException when task is not successful and exception is null`() =
        runTest {
            // Arrange
            val email = credentialProvider.email
            val password = credentialProvider.password

            every {
                firebaseAuth.createUserWithEmailAndPassword(email.value, password.value)
            } returns firebaseAuthProvider.mockUnsuccessfulAuthTask()

            // Act && Assert
            assertThrows<TaskFailureException> {
                authSource.createUserWithEmail(email, password)
            }

        }

    @ParameterizedTest
    @MethodSource("provideFirebaseExceptionsForCreateUser")
    fun `createUserWithEmail should throw mapped exception when task fails with backend exception`(
        givenException: Exception,
        expectedType: KClass<out AuthSourceException>
    ) = runTest {
        // Arrange
        val email = credentialProvider.email
        val password = credentialProvider.password

        every {
            firebaseAuth.createUserWithEmailAndPassword(email.value, password.value)
        } returns firebaseAuthProvider.mockErrorAuthTask(givenException)

        // Act && Assert
        val exception = assertThrows<AuthSourceException> {
            authSource.createUserWithEmail(email, password)
        }

        assertTrue(expectedType.isInstance(exception))
    }

    //----------------------------------------------------------------------------------------------
    // Testing SendEmailVerification
    //----------------------------------------------------------------------------------------------
    @Test
    fun `sendEmailVerification should complete successfully when task is successful`() = runTest {
        // Arrange
        val user = firebaseAuthProvider.firebaseUserMock

        every { firebaseAuth.currentUser } returns user
        every { user.sendEmailVerification() } returns firebaseAuthProvider.mockSuccessfulVoidTask()

        // Act & Assert
        assertDoesNotThrow {
            authSource.sendEmailVerification()
        }
    }

    @Test
    fun `sendEmailVerification should throw TaskFailureException when task is not successful and exception is null`() =
        runTest {
            // Arrange
            val user = firebaseAuthProvider.firebaseUserMock

            every { firebaseAuth.currentUser } returns user
            every { user.sendEmailVerification() } returns firebaseAuthProvider.mockUnsuccessfulVoidTask()

            // Act & Assert
            assertThrows<TaskFailureException> {
                authSource.sendEmailVerification()
            }
        }

    @ParameterizedTest
    @MethodSource("provideFirebaseExceptionsForSendEmail")
    fun `sendEmailVerification should throw mapped exception when task fails with backend exception`(
        givenException: Exception,
        expectedType: KClass<out AuthSourceException>
    ) = runTest {
        // Arrange
        val user = firebaseAuthProvider.firebaseUserMock

        every { firebaseAuth.currentUser } returns user
        every {
            user.sendEmailVerification()
        } returns firebaseAuthProvider.mockErrorVoidTask(givenException)

        // Act & Assert
        val thrown = assertThrows<AuthSourceException> {
            authSource.sendEmailVerification()
        }

        assertTrue(expectedType.isInstance(thrown))
    }

    //----------------------------------------------------------------------------------------------
    // Testing SendEmailVerification
    //----------------------------------------------------------------------------------------------
    @Test
    fun `updateUserProfile should complete successfully when task is successful`() = runTest {
        // Arrange
        val profile = credentialProvider.userProfile
        val user = firebaseAuthProvider.firebaseUserMock

        every { firebaseAuth.currentUser } returns user
        every { user.updateProfile(any()) } returns firebaseAuthProvider.mockSuccessfulVoidTask()

        // Act & Assert
        assertDoesNotThrow {
            authSource.updateUserProfile(profile)
        }
    }

    @Test
    fun `updateUserProfile should throw TaskFailureException when task is not successful and exception is null`() = runTest {
        // Arrange
        val profile = credentialProvider.userProfile
        val user = firebaseAuthProvider.firebaseUserMock

        every { firebaseAuth.currentUser } returns user
        every { user.updateProfile(any()) } returns firebaseAuthProvider.mockUnsuccessfulVoidTask()

        // Act & Assert
        assertThrows<TaskFailureException> {
            authSource.updateUserProfile(profile)
        }
    }

    @ParameterizedTest
    @MethodSource("provideFirebaseExceptionsForUpdateProfile")
    fun `updateUserProfile should throw mapped exception when task fails with backend exception`(
        givenException: Exception,
        expectedType: KClass<out AuthSourceException>
    ) = runTest {
        // Arrange
        val profile = credentialProvider.userProfile
        val user = firebaseAuthProvider.firebaseUserMock

        every { firebaseAuth.currentUser } returns user
        every {
            user.updateProfile(any())
        } returns firebaseAuthProvider.mockErrorVoidTask(givenException)

        // Act & Assert
        val thrown = assertThrows<AuthSourceException> {
            authSource.updateUserProfile(profile)
        }

        assertTrue(expectedType.isInstance(thrown))
    }

    //----------------------------------------------------------------------------------------------
    // Testing SignInWithEmail
    //----------------------------------------------------------------------------------------------
    @Test
    fun `signInWithEmail should complete successfully when task is successful`() = runTest {
        // Arrange
        val email = credentialProvider.email
        val password = credentialProvider.password

        every {
            firebaseAuth.signInWithEmailAndPassword(email.value, password.value)
        } returns firebaseAuthProvider.mockSuccessfulAuthTask()

        // Act & Assert
        assertDoesNotThrow {
            authSource.signInWithEmail(email, password)
        }
    }

    @Test
    fun `signInWithEmail should throw TaskFailureException when task is not successful and exception is null`() = runTest {
        // Arrange
        val email = credentialProvider.email
        val password = credentialProvider.password

        every {
            firebaseAuth.signInWithEmailAndPassword(email.value, password.value)
        } returns firebaseAuthProvider.mockUnsuccessfulAuthTask()

        // Act & Assert
        assertThrows<TaskFailureException> {
            authSource.signInWithEmail(email, password)
        }
    }

    @ParameterizedTest
    @MethodSource("provideFirebaseExceptionsForSignIn")
    fun `signInWithEmail should throw mapped exception when task fails with backend exception`(
        givenException: Exception,
        expectedType: KClass<out AuthSourceException>
    ) = runTest {
        // Arrange
        val email = credentialProvider.email
        val password = credentialProvider.password

        every {
            firebaseAuth.signInWithEmailAndPassword(email.value, password.value)
        } returns firebaseAuthProvider.mockErrorAuthTask(givenException)

        // Act & Assert
        val thrown = assertThrows<AuthSourceException> {
            authSource.signInWithEmail(email, password)
        }

        assertTrue(expectedType.isInstance(thrown))
    }

    //----------------------------------------------------------------------------------------------
    // Testing observeEmailValidation
    //----------------------------------------------------------------------------------------------
    @Test
    fun `observeEmailValidation should complete when email is verified`() = runTest {
        // Arrange
        val user = firebaseAuthProvider.firebaseUserMock

        every { firebaseAuth.currentUser } returns user
        every { user.isEmailVerified } returnsMany listOf(false, false, true)

        // Act & Assert
        assertDoesNotThrow {
            authSource.observeEmailValidation()
        }
    }

    @Test
    fun `observeEmailValidation should throw SessionInactiveException when user is null`() = runTest {
        // Arrange
        every { firebaseAuth.currentUser } returns null

        // Act & Assert
        assertThrows<SessionInactiveException> {
            authSource.observeEmailValidation()
        }
    }

}