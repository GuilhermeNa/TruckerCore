package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.SendEmailVerificationErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SendEmailVerificationErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each SendEmailVerificationErrCode should have correct properties`(
        errorCode: SendEmailVerificationErrCode,
        expectedName: String,
        expectedUserMessage: String,
        expectedLogMessage: String,
        expectedIsRecoverable: Boolean
    ) {
        assertEquals(expectedName, errorCode.name)
        assertEquals(expectedUserMessage, errorCode.userMessage)
        assertEquals(expectedLogMessage, errorCode.logMessage)
        assertEquals(expectedIsRecoverable, errorCode.isRecoverable)
    }

    companion object {
        @JvmStatic
        fun provideErrorCodes() = listOf(
            Arguments.of(
                SendEmailVerificationErrCode.TaskFailure,
                "TASK_FAILURE",
                "Não foi possível concluir o envio do e-mail de verificação.",
                "Unexpected failure in the email verification task.",
                true
            ),
            Arguments.of(
                SendEmailVerificationErrCode.Unknown,
                "UNKNOWN_ERROR",
                "Ocorreu um erro desconhecido. Tente novamente mais tarde.",
                "Unexpected error during the email verification process.",
                false
            ),
            Arguments.of(
                SendEmailVerificationErrCode.SessionInactive,
                "SESSION_INACTIVE",
                "Usuário não encontrado. Tente fazer o login.",
                "Send email attempt failed. User not found or session expired ",
                false
            )
        )
    }

}