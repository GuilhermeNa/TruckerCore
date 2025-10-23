package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.ObserveEmailValidationErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ObserveEmailValidationErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each ObserveEmailValidationErrCode should have correct properties`(
        errorCode: ObserveEmailValidationErrCode,
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
                ObserveEmailValidationErrCode.SessionInactive,
                "SESSION_INACTIVE",
                "Usuário não encontrado. Tente fazer o login.",
                "Email validation attempt failed. User not found or session expired.",
                false
            ),
            Arguments.of(
                ObserveEmailValidationErrCode.Unknown,
                "UNKNOWN_ERROR",
                "Erro desconhecido. Tente novamente.",
                "Unexpected error on updating user profile",
                false
            )
        )
    }

}