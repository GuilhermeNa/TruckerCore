package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.model.infrastructure.integration.auth.for_app.app_errors.error_codes.SignInErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SignInErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each SignInErrCode should have correct properties`(
        errorCode: SignInErrCode,
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
                SignInErrCode.InvalidCredentials,
                "INVALID_CREDENTIALS",
                "Email ou senha inválidos.",
                "Invalid credentials provided during log in.",
                true
            ),
            Arguments.of(
                SignInErrCode.NetworkError,
                "NETWORK_ERROR",
                "Erro de conexão. Verifique sua internet e tente novamente.",
                "Network error occurred during login.",
                true
            ),
            Arguments.of(
                SignInErrCode.TooManyRequests,
                "TOO_MANY_REQUESTS",
                "Muitas tentativas. Aguarde um momento e tente novamente.",
                "Too many login attempts. Request was throttled.",
                true
            ),
            Arguments.of(
                SignInErrCode.UnknownError,
                "UNKNOWN_ERROR",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                "An unknown error occurred during login.",
                false
            ),
            Arguments.of(
                SignInErrCode.TaskFailure,
                "TASK_FAILURE",
                "Não foi possível concluir o login.",
                "Unexpected failure in the login task.",
                true
            )
        )
    }

}