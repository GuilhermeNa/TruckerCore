package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.NewEmailErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class NewEmailErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each NewEmailErrCode should have correct properties`(
        errorCode: NewEmailErrCode,
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
                NewEmailErrCode.WeakPassword,
                "WEAK_PASSWORD",
                "Senha inválida.",
                "Weak password. Please provide a valid password",
                true
            ),
            Arguments.of(
                NewEmailErrCode.InvalidCredentials,
                "INVALID_CREDENTIALS",
                "E-mail e/ou senha inválidos.",
                "Invalid credentials during creating a new account",
                true
            ),
            Arguments.of(
                NewEmailErrCode.AccountCollision,
                "ACCOUNT_COLLISION",
                "Já existe uma conta com este e-mail.",
                "Email already registered",
                true
            ),
            Arguments.of(
                NewEmailErrCode.Network,
                "NETWORK_ERROR",
                "Erro de rede. Verifique sua conexão.",
                "Network failure on creating a new account",
                true
            ),
            Arguments.of(
                NewEmailErrCode.Unknown,
                "UNKNOWN_ERROR",
                "Erro desconhecido. Tente novamente.",
                "Unexpected error on creating a new account",
                false
            ),
            Arguments.of(
                NewEmailErrCode.TaskFailure,
                "UNSUCCESSFUL_TASK",
                "Não foi possível logar automaticamente. Faça o login.",
                "Unexpected failure in the new email task. Current user cannot be recovered.",
                true
            )
        )
    }

}