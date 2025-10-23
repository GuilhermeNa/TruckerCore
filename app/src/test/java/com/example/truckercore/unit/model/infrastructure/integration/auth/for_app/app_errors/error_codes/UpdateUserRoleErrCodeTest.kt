package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.app_errors.error_codes

import com.example.truckercore.data.infrastructure.integration.auth.for_app.app_errors.error_codes.UpdateUserProfileErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class UpdateUserRoleErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each UpdateUserProfileErrCode should have correct properties`(
        errorCode: UpdateUserProfileErrCode,
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
                UpdateUserProfileErrCode.TaskFailure,
                "TASK_FAILURE",
                "Falha ao completar tarefa.",
                "Failed on complete update user. Task returned unsuccessful.",
                true
            ),
            Arguments.of(
                UpdateUserProfileErrCode.Network,
                "NETWORK_ERROR",
                "Erro de rede. Verifique sua conexão.",
                "Network failure on sign-up.",
                true
            ),
            Arguments.of(
                UpdateUserProfileErrCode.Unknown,
                "UNKNOWN_ERROR",
                "Erro desconhecido. Tente novamente.",
                "Unexpected error on update users name.",
                false
            ),
            Arguments.of(
                UpdateUserProfileErrCode.SessionInactive,
                "SESSION_INACTIVE",
                "Usuário não encontrado. Tente fazer o login.",
                "Names update attempt failed. User not found or session expired ",
                false
            )
        )
    }

}