package com.example.truckercore.unit.model.infrastructure.security.authentication.app_errors

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.EmailCredentialErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.security.authentication.app_errors.error_codes.UpdateUserProfileErrCode
import com.example.truckercore.model.shared.value_classes.exceptions.InvalidNameException
import com.example.truckercore.model.shared.value_classes.exceptions.InvalidPasswordException
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.NullFirebaseUserException
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.ObserveEmailValidationErrCode
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthenticationAppErrorFactoryTest {

    //----------------------------------------------------------------------------------------------
    // Tests for handleCreateUserWithEmailError()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleCreateUserWithEmailError should return AccountCollision error when FirebaseAuthUserCollisionException is thrown`() {
        val exception =
            FirebaseAuthUserCollisionException("ERROR_EMAIL_ALREADY_IN_USE", "Email already in use")

        val result = AuthenticationAppErrorFactory.handleCreateUserWithEmailError(exception)

        assertEquals(NewEmailErrCode.AccountCollision, result.errorCode)
    }

    @Test
    fun `handleCreateUserWithEmailError should return InvalidCredentials for weak password or invalid credentials`() {
        val weakPassword =
            FirebaseAuthWeakPasswordException("ERROR_WEAK_PASSWORD", "Weak password", "reason")
        val invalidCreds =
            FirebaseAuthInvalidCredentialsException("ERROR_INVALID_EMAIL", "Invalid email")

        val result1 = AuthenticationAppErrorFactory.handleCreateUserWithEmailError(weakPassword)
        val result2 = AuthenticationAppErrorFactory.handleCreateUserWithEmailError(invalidCreds)

        assertEquals(NewEmailErrCode.InvalidCredentials, result1.errorCode)
        assertEquals(NewEmailErrCode.InvalidCredentials, result2.errorCode)
    }

    @Test
    fun `handleCreateUserWithEmailError should return Network error for FirebaseNetworkException`() {
        val exception = FirebaseNetworkException("No network")

        val result = AuthenticationAppErrorFactory.handleCreateUserWithEmailError(exception)

        assertEquals(NewEmailErrCode.Network, result.errorCode)
    }

    @Test
    fun `handleCreateUserWithEmailError should return UnsuccessfulTask when IncompleteTaskException is thrown`() {
        val result = AuthenticationAppErrorFactory.handleCreateUserWithEmailError(
            IncompleteTaskException()
        )

        assertEquals(NewEmailErrCode.UnsuccessfulTask, result.errorCode)
    }

    @Test
    fun `handleCreateUserWithEmailError should return Unknown error for unexpected exception`() {
        val result =
            AuthenticationAppErrorFactory.handleCreateUserWithEmailError(RuntimeException())

        assertEquals(NewEmailErrCode.Unknown, result.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Tests for handleSendEmailVerificationError()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleSendEmailVerificationError should return UserNotFound when NullFirebaseUserException is thrown`() {
        val result = AuthenticationAppErrorFactory.handleSendEmailVerificationError(
            NullFirebaseUserException()
        )

        assertEquals(SendEmailVerificationErrCode.UserNotFound, result.errorCode)
    }

    @Test
    fun `handleSendEmailVerificationError should return UnsuccessfulTask when IncompleteTaskException is thrown`() {
        val result =
            AuthenticationAppErrorFactory.handleSendEmailVerificationError(IncompleteTaskException())

        assertEquals(SendEmailVerificationErrCode.UnsuccessfulTask, result.errorCode)
    }

    @Test
    fun `handleSendEmailVerificationError should return Unknown for unexpected exceptions`() {
        val result =
            AuthenticationAppErrorFactory.handleSendEmailVerificationError(IllegalStateException())

        assertEquals(SendEmailVerificationErrCode.Unknown, result.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Tests for handleUpdateUserNameError()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleUpdateUserNameError should return Network error when FirebaseNetworkException is thrown`() {
        val result =
            AuthenticationAppErrorFactory.handleUpdateUserNameError(FirebaseNetworkException(""))

        assertEquals(UpdateUserProfileErrCode.Network, result.errorCode)
    }

    @Test
    fun `handleUpdateUserNameError should return UserNotFound when NullFirebaseUserException is thrown`() {
        val result =
            AuthenticationAppErrorFactory.handleUpdateUserNameError(NullFirebaseUserException())

        assertEquals(UpdateUserProfileErrCode.UserNotFound, result.errorCode)
    }

    @Test
    fun `handleUpdateUserNameError should return UnsuccessfulTask for IncompleteTaskException`() {
        val result =
            AuthenticationAppErrorFactory.handleUpdateUserNameError(IncompleteTaskException())

        assertEquals(UpdateUserProfileErrCode.UnsuccessfulTask, result.errorCode)
    }

    @Test
    fun `handleUpdateUserNameError should return Unknown for unexpected exception`() {
        val result =
            AuthenticationAppErrorFactory.handleUpdateUserNameError(IllegalArgumentException())

        assertEquals(UpdateUserProfileErrCode.Unknown, result.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Tests for handleObservingEmailValidationError()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleObservingEmailValidationError should always return UserNotFound`() {
        val result = AuthenticationAppErrorFactory.handleObservingEmailValidationError()

        assertEquals(ObserveEmailValidationErrCode.SessionInactive, result.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Tests for handleSignInError()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleSignInError should return InvalidCredentials for known auth credential exceptions`() {
        val result1 = AuthenticationAppErrorFactory.handleSignInError(
            FirebaseAuthInvalidUserException("ERR", "msg")
        )
        val result2 = AuthenticationAppErrorFactory.handleSignInError(
            FirebaseAuthInvalidCredentialsException("ERR", "msg")
        )

        assertEquals(SignInErrCode.InvalidCredentials, result1.errorCode)
        assertEquals(SignInErrCode.InvalidCredentials, result2.errorCode)
    }

    @Test
    fun `handleSignInError should return NetworkError when FirebaseNetworkException is thrown`() {
        val result = AuthenticationAppErrorFactory.handleSignInError(FirebaseNetworkException(""))

        assertEquals(SignInErrCode.NetworkError, result.errorCode)
    }

    @Test
    fun `handleSignInError should return TooManyRequests when FirebaseTooManyRequestsException is thrown`() {
        val result = AuthenticationAppErrorFactory.handleSignInError(
            FirebaseTooManyRequestsException("")
        )

        assertEquals(SignInErrCode.TooManyRequests, result.errorCode)
    }

    @Test
    fun `handleSignInError should return UnsuccessfulTask when exception is null`() {
        val result = AuthenticationAppErrorFactory.handleSignInError(null)

        assertEquals(SignInErrCode.UnsuccessfulTask, result.errorCode)
    }

    @Test
    fun `handleSignInError should return UnknownError for unexpected exception`() {
        val result = AuthenticationAppErrorFactory.handleSignInError(IllegalStateException())

        assertEquals(SignInErrCode.UnknownError, result.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Tests for handleEmailCredentialError()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleEmailCredentialError should return correct error codes for validation exceptions`() {
        val nameError = AuthenticationAppErrorFactory.handleEmailCredentialError(
            InvalidNameException()
        )
        val emailError = AuthenticationAppErrorFactory.handleEmailCredentialError(
            InvalidEmailException()
        )
        val passError = AuthenticationAppErrorFactory.handleEmailCredentialError(
            InvalidPasswordException()
        )

        assertEquals(EmailCredentialErrCode.InvalidName, nameError.errorCode)
        assertEquals(EmailCredentialErrCode.InvalidEmail, emailError.errorCode)
        assertEquals(EmailCredentialErrCode.InvalidPassword, passError.errorCode)
    }

    @Test
    fun `handleEmailCredentialError should return Unknown for unmapped exception`() {
        val result = AuthenticationAppErrorFactory.handleEmailCredentialError(RuntimeException())

        assertEquals(EmailCredentialErrCode.Unknown, result.errorCode)
    }

}