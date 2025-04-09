package com.example.truckercore.model.infrastructure.security.authentication.errors

abstract class AuthenticationException(message: String?, cause: Throwable?) :
    Exception(message, cause)

class NewEmailUserException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause) {

    enum class ErrorCode(val description: String) {
        Network("Erro de rede. Verifique sua conexão com a internet."),
        InvalidCredentials("E-mail e/ou senha inválidos."),
        AccountCollision("Já existe uma conta registrada com este e-mail."),
        UserNotFound("Usuário não encontrado."),
        Unknown("Ocorreu um erro desconhecido. Tente novamente mais tarde.");
    }

}

class SendEmailVerificationException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause) {

    enum class ErrorCode(val description: String) {
        Network("Erro de rede. Verifique sua conexão com a internet."),
        EmailNotFound("O e-mail informado não foi encontrado. Verifique se está correto."),
        UnsuccessfulTask("A tarefa não foi bem-sucedida. Tente novamente."),
        Unknown("Ocorreu um erro desconhecido. Tente novamente mais tarde.");
    }

}

class UpdateUserProfileException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause) {

    enum class ErrorCode(val description: String) {
        Network("Erro de rede. Verifique sua conexão com a internet."),
        UnsuccessfulTask("A tarefa não foi bem-sucedida. Tente novamente."),
        Unknown("Ocorreu um erro desconhecido. Tente novamente mais tarde.");
    }

}