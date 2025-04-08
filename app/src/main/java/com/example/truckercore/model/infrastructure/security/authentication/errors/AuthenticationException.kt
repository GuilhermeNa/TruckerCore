package com.example.truckercore.model.infrastructure.security.authentication.errors

abstract class AuthenticationException(message: String?, cause: Throwable?) :
    Exception(message, cause)

class NewEmailUserException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause) {

    enum class ErrorCode {
        Network,
        InvalidCredentials,
        AccountCollision,
        UserNotFound,
        Unknown;

        fun getErrorDescription(): String {
            return when (this) {
                Network -> "Erro de rede. Verifique sua conexão com a internet."
                InvalidCredentials -> "E-mail e/ou senha inválidos."
                AccountCollision -> "Já existe uma conta registrada com este e-mail."
                Unknown -> "Ocorreu um erro desconhecido. Tente novamente mais tarde."
                UserNotFound -> "Usuário não encontrado."
            }
        }
    }

}

class SendEmailVerificationException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause) {

    enum class ErrorCode {
        Network,
        EmailNotFound,
        UnsuccessfulTask,
        Unknown;

        fun getErrorDescription(): String {
            return when (this) {
                Network -> "Erro de rede. Verifique sua conexão com a internet."
                Unknown -> "Ocorreu um erro desconhecido. Tente novamente mais tarde."
                UnsuccessfulTask -> "A tarefa não foi bem-sucedida. Tente novamente."
                EmailNotFound -> "O e-mail informado não foi encontrado. Verifique se está correto."
            }
        }
    }

}

class UpdateUserProfileException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause) {

    enum class ErrorCode {
        Network,
        UnsuccessfulTask,
        Unknown;

        fun getErrorDescription(): String {
            return when (this) {
                Network -> "Erro de rede. Verifique sua conexão com a internet."
                Unknown -> "Ocorreu um erro desconhecido. Tente novamente mais tarde."
                UnsuccessfulTask -> "A tarefa não foi bem-sucedida. Tente novamente."
            }
        }
    }

}