package com.example.truckercore.unit.model.infrastructure.data_source.firebase.auth

import com.example.truckercore._test_utils.mockStaticTextUtil
import com.example.truckercore.model.infrastructure.data_source.firebase.auth.FirebaseAuthSourceErrorMapper
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirebaseAuthUserInputUserInputCriticalRecoverableErrorMappingDtoToEntityTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val mapper = FirebaseAuthSourceErrorMapper()

    @BeforeEach
    fun setup() {
        mockStaticTextUtil()
    }

    //----------------------------------------------------------------------------------------------
    // creatingUserWithEmail
    //----------------------------------------------------------------------------------------------
    @Test
    fun `creatingUserWithEmail should map TaskFailureException`() {
        val ex = TaskFailureException()
        val result = mapper.creatingUserWithEmail(ex)

        assertTrue(result is TaskFailureException)
        assertNull(result.cause)
        assertEquals("Failed to create user with email. Please try again later.", result.message)
    }

    @Test
    fun `creatingUserWithEmail should map FirebaseAuthWeakPasswordException`() {
        val ex = FirebaseAuthWeakPasswordException("reason", "weak password", "email")
        val result = mapper.creatingUserWithEmail(ex)

        assertTrue(result is WeakPasswordException)
        assertEquals(result.cause, ex)
        assertEquals(
            "The password provided is too weak. Please choose a stronger password.",
            result.message
        )
    }

    @Test
    fun `creatingUserWithEmail should map FirebaseAuthInvalidCredentialsException`() {
        val ex = FirebaseAuthInvalidCredentialsException("error", "Invalid credentials")
        val result = mapper.creatingUserWithEmail(ex)

        assertTrue(result is InvalidCredentialsException)
        assertEquals(result.cause, ex)
        assertEquals(
            "The email address or password is invalid. " +
                    "Please check your credentials.", result.message
        )
    }

    @Test
    fun `creatingUserWithEmail should map FirebaseAuthUserCollisionException`() {
        val ex = FirebaseAuthUserCollisionException("error", "email already used")
        val result = mapper.creatingUserWithEmail(ex)

        assertTrue(result is UserCollisionException)
        assertEquals(result.cause, ex)
        assertEquals(
            "An account already exists with this email address.",
            result.message
        )
    }

    @Test
    fun `creatingUserWithEmail should map FirebaseNetworkException`() {
        val ex = FirebaseNetworkException("no connection")
        val result = mapper.creatingUserWithEmail(ex)

        assertTrue(result is NetworkException)
        assertEquals(result.cause, ex)
        assertEquals(
            "A network error occurred while creating user with email.",
            result.message
        )
    }

    @Test
    fun `creatingUserWithEmail should map unknown exceptions to UnknownException`() {
        val ex = RuntimeException("unexpected")
        val result = mapper.creatingUserWithEmail(ex)

        assertTrue(result is UnknownException)
        assertEquals(result.cause, ex)
        assertEquals(
            "An unknown error occurred during user creation with email.",
            result.message
        )
    }

    //----------------------------------------------------------------------------------------------
    // sendingEmailVerification
    //----------------------------------------------------------------------------------------------
    @Test
    fun `sendingEmailVerification should map SessionInactiveException`() {
        val ex = SessionInactiveException()
        val result = mapper.sendingEmailVerification(ex)

        assertTrue(result is SessionInactiveException)
        assertNull(result.cause)
        assertEquals(
            "The user profile is invalid or incomplete for sending email verification.",
            result.message
        )
    }

    @Test
    fun `sendingEmailVerification should map TaskFailureException`() {
        val ex = TaskFailureException()
        val result = mapper.sendingEmailVerification(ex)

        assertTrue(result is TaskFailureException)
        assertNull(result.cause)
        assertEquals(
            "Failed to send email verification. Please try again.",
            result.message
        )
    }

    @Test
    fun `sendingEmailVerification should map unknown exceptions to UnknownException`() {
        val ex = IllegalStateException("unexpected")
        val result = mapper.sendingEmailVerification(ex)

        assertTrue(result is UnknownException)
        assertEquals(ex, result.cause)
        assertEquals(
            "An unknown error occurred while sending email verification.",
            result.message
        )
    }

    //----------------------------------------------------------------------------------------------
    // updatingProfile
    //----------------------------------------------------------------------------------------------
    @Test
    fun `updatingProfile should map FirebaseNetworkException`() {
        val ex = FirebaseNetworkException("no connection")
        val result = mapper.updatingProfile(ex)

        assertTrue(result is NetworkException)
        assertEquals(ex, result.cause)
        assertEquals(
            "A network error occurred while updating the user profile.",
            result.message
        )
    }

    @Test
    fun `updatingProfile should map SessionInactiveException`() {
        val ex = SessionInactiveException()
        val result = mapper.updatingProfile(ex)

        assertTrue(result is SessionInactiveException)
        assertNull(result.cause)
        assertEquals(
            "The data provided for updating the profile is invalid.",
            result.message
        )
    }

    @Test
    fun `updatingProfile should map TaskFailureException`() {
        val ex = TaskFailureException()
        val result = mapper.updatingProfile(ex)

        assertTrue(result is TaskFailureException)
        assertNull(result.cause)
        assertEquals(
            "Failed to update user profile. Please try again.",
            result.message
        )
    }

    @Test
    fun `updatingProfile should map unknown exceptions to UnknownException`() {
        val ex = NullPointerException("unexpected null")
        val result = mapper.updatingProfile(ex)

        assertTrue(result is UnknownException)
        assertEquals(ex, result.cause)
        assertEquals(
            "An unknown error occurred while updating the user profile.",
            result.message
        )
    }

    //----------------------------------------------------------------------------------------------
    // signingInWithEmail
    //----------------------------------------------------------------------------------------------
    @Test
    fun `signingInWithEmail should map FirebaseAuthInvalidUserException`() {
        val ex = FirebaseAuthInvalidUserException("code", "user not found")
        val result = mapper.signingInWithEmail(ex)

        assertTrue(result is InvalidCredentialsException)
        assertEquals(ex, result.cause)
        assertEquals(
            "The user account was not found or may have been deleted. " +
                    "Please verify your credentials.",
            result.message
        )
    }

    @Test
    fun `signingInWithEmail should map FirebaseAuthInvalidCredentialsException`() {
        val ex = FirebaseAuthInvalidCredentialsException("code", "invalid credentials")
        val result = mapper.signingInWithEmail(ex)

        assertTrue(result is InvalidCredentialsException)
        assertEquals(ex, result.cause)
        assertEquals(
            "Invalid email or password. Please try again.",
            result.message
        )
    }

    @Test
    fun `signingInWithEmail should map FirebaseNetworkException`() {
        val ex = FirebaseNetworkException("no connection")
        val result = mapper.signingInWithEmail(ex)

        assertTrue(result is NetworkException)
        assertEquals(ex, result.cause)
        assertEquals(
            "A network error occurred while attempting to sign in with email.",
            result.message
        )
    }

    @Test
    fun `signingInWithEmail should map FirebaseTooManyRequestsException`() {
        val ex = FirebaseTooManyRequestsException("too many attempts")
        val result = mapper.signingInWithEmail(ex)

        assertTrue(result is TooManyRequestsException)
        assertEquals(ex, result.cause)
        assertEquals(
            "Too many login attempts. Please wait and try again later.",
            result.message
        )
    }

    @Test
    fun `signingInWithEmail should map TaskFailureException`() {
        val ex = TaskFailureException()
        val result = mapper.signingInWithEmail(ex)

        assertTrue(result is TaskFailureException)
        assertNull(result.cause)
        assertEquals(
            "Sign-in with email failed. Please try again.",
            result.message
        )
    }

    @Test
    fun `signingInWithEmail should map unknown exceptions to UnknownException`() {
        val ex = Exception("generic error")
        val result = mapper.signingInWithEmail(ex)

        assertTrue(result is UnknownException)
        assertEquals(ex, result.cause)
        assertEquals(
            "An unknown error occurred while signing in with email.",
            result.message
        )
    }

}