/*
package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.app_errors

import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidCredentialsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.SessionInactiveException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TooManyRequestsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UserCollisionException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.WeakPasswordException
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.NewEmailErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.ObserveEmailValidationErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.SendEmailVerificationErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.SignInErrCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.UpdateUserProfileErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthenticationAppUserInputUserInputErrorFactoryTest {

    private lateinit var factory: AuthenticationAppErrorFactory

    @BeforeEach
    fun setUp() {
        factory = AuthenticationAppErrorFactory()
    }

    //--------------------------------------------------------------------------
    // Testing creatingUserWithEmail
    //--------------------------------------------------------------------------
    @Test
    fun `creatingUserWithEmail should map TaskFailureException to TaskFailure`() {
        // Arrange
        val ex = TaskFailureException()

        // Act
        val result = factory.creatingUserWithEmail(ex)

        // Assert
        assertEquals(result.errorCode, NewEmailErrCode.TaskFailure)
        assertEquals(result.message, NewEmailErrCode.TaskFailure.logMessage)
        assertEquals(result.cause, ex)
    }

    @Test
    fun `creatingUserWithEmail should map WeakPasswordException to WeakPassword`() {
        // Arrange
        val ex = WeakPasswordException()

        // Act
        val result = factory.creatingUserWithEmail(ex)

        // Assert
        assertEquals(NewEmailErrCode.WeakPassword, result.errorCode)
        assertEquals(NewEmailErrCode.WeakPassword.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `creatingUserWithEmail should map InvalidCredentialsException to InvalidCredentials`() {
        // Arrange
        val ex = InvalidCredentialsException()

        // Act
        val result = factory.creatingUserWithEmail(ex)

        // Assert
        assertEquals(NewEmailErrCode.InvalidCredentials, result.errorCode)
        assertEquals(NewEmailErrCode.InvalidCredentials.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `creatingUserWithEmail should map UserCollisionException to AccountCollision`() {
        // Arrange
        val ex = UserCollisionException()

        // Act
        val result = factory.creatingUserWithEmail(ex)

        // Assert
        assertEquals(NewEmailErrCode.AccountCollision, result.errorCode)
        assertEquals(NewEmailErrCode.AccountCollision.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `creatingUserWithEmail should map NetworkException to Network`() {
        // Arrange
        val ex = NetworkException()

        // Act
        val result = factory.creatingUserWithEmail(ex)

        // Assert
        assertEquals(NewEmailErrCode.Network, result.errorCode)
        assertEquals(NewEmailErrCode.Network.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `creatingUserWithEmail should map unknown exception to Unknown`() {
        // Arrange
        val ex = RuntimeException("unexpected")

        // Act
        val result = factory.creatingUserWithEmail(ex)

        // Assert
        assertEquals(NewEmailErrCode.Unknown, result.errorCode)
        assertEquals(NewEmailErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    //---------------------------------------------------------------------------
    // Testing sendingEmailVerification
    //---------------------------------------------------------------------------
    @Test
    fun `sendingEmailVerification should map SessionInactiveException to SessionInactive`() {
        // Arrange
        val ex = SessionInactiveException()

        // Act
        val result = factory.sendingEmailVerification(ex)

        // Assert
        assertEquals(SendEmailVerificationErrCode.SessionInactive, result.errorCode)
        assertEquals(SendEmailVerificationErrCode.SessionInactive.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `sendingEmailVerification should map TaskFailureException to TaskFailure`() {
        // Arrange
        val ex = TaskFailureException()

        // Act
        val result = factory.sendingEmailVerification(ex)

        // Assert
        assertEquals(SendEmailVerificationErrCode.TaskFailure, result.errorCode)
        assertEquals(SendEmailVerificationErrCode.TaskFailure.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `sendingEmailVerification should map unknown exception to Unknown`() {
        // Arrange
        val ex = RuntimeException()

        // Act
        val result = factory.sendingEmailVerification(ex)

        // Assert
        assertEquals(SendEmailVerificationErrCode.Unknown, result.errorCode)
        assertEquals(SendEmailVerificationErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    //---------------------------------------------------------------------------
    // Testing updatingProfile
    //---------------------------------------------------------------------------
    @Test
    fun `updatingProfile should map NetworkException to Network`() {
        // Arrange
        val ex = NetworkException()

        // Act
        val result = factory.updatingProfile(ex)

        // Assert
        assertEquals(UpdateUserProfileErrCode.Network, result.errorCode)
        assertEquals(UpdateUserProfileErrCode.Network.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `updatingProfile should map SessionInactiveException to SessionInactive`() {
        // Arrange
        val ex = SessionInactiveException()

        // Act
        val result = factory.updatingProfile(ex)

        // Assert
        assertEquals(UpdateUserProfileErrCode.SessionInactive, result.errorCode)
        assertEquals(UpdateUserProfileErrCode.SessionInactive.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `updatingProfile should map TaskFailureException to TaskFailure`() {
        // Arrange
        val ex = TaskFailureException()

        // Act
        val result = factory.updatingProfile(ex)

        // Assert
        assertEquals(UpdateUserProfileErrCode.TaskFailure, result.errorCode)
        assertEquals(UpdateUserProfileErrCode.TaskFailure.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `updatingProfile should map unknown exception to Unknown`() {
        // Arrange
        val ex = RuntimeException()

        // Act
        val result = factory.updatingProfile(ex)

        // Assert
        assertEquals(UpdateUserProfileErrCode.Unknown, result.errorCode)
        assertEquals(UpdateUserProfileErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    //---------------------------------------------------------------------------
    // Testing observingEmailValidation
    //---------------------------------------------------------------------------
    @Test
    fun `observingEmailValidation should map SessionInactiveException to SessionInactive`() {
        // Arrange
        val ex = SessionInactiveException()

        // Act
        val result = factory.observingEmailValidation(ex)

        // Assert
        assertEquals(ObserveEmailValidationErrCode.SessionInactive, result.errorCode)
        assertEquals(ObserveEmailValidationErrCode.SessionInactive.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `observingEmailValidation should map unknown exception to Unknown`() {
        // Arrange
        val ex = RuntimeException()

        // Act
        val result = factory.observingEmailValidation(ex)

        // Assert
        assertEquals(ObserveEmailValidationErrCode.Unknown, result.errorCode)
        assertEquals(ObserveEmailValidationErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    //---------------------------------------------------------------------------
    // Testing signingIn
    //---------------------------------------------------------------------------
    @Test
    fun `signingIn should map InvalidCredentialsException to InvalidCredentials`() {
        // Arrange
        val ex = InvalidCredentialsException()

        // Act
        val result = factory.signingIn(ex)

        // Assert
        assertEquals(SignInErrCode.InvalidCredentials, result.errorCode)
        assertEquals(SignInErrCode.InvalidCredentials.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `signingIn should map NetworkException to NetworkError`() {
        // Arrange
        val ex = NetworkException()

        // Act
        val result = factory.signingIn(ex)

        // Assert
        assertEquals(SignInErrCode.NetworkError, result.errorCode)
        assertEquals(SignInErrCode.NetworkError.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `signingIn should map TooManyRequestsException to TooManyRequests`() {
        // Arrange
        val ex = TooManyRequestsException()

        // Act
        val result = factory.signingIn(ex)

        // Assert
        assertEquals(SignInErrCode.TooManyRequests, result.errorCode)
        assertEquals(SignInErrCode.TooManyRequests.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `signingIn should map TaskFailureException to TaskFailure`() {
        // Arrange
        val ex = TaskFailureException()

        // Act
        val result = factory.signingIn(ex)

        // Assert
        assertEquals(SignInErrCode.TaskFailure, result.errorCode)
        assertEquals(SignInErrCode.TaskFailure.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `signingIn should map unknown exception to UnknownError`() {
        // Arrange
        val ex = RuntimeException()

        // Act
        val result = factory.signingIn(ex)

        // Assert
        assertEquals(SignInErrCode.UnknownError, result.errorCode)
        assertEquals(SignInErrCode.UnknownError.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

}*/
