package com.example.truckercore.model.infrastructure.security.authentication.errors

abstract class AuthenticationException(message: String?, cause: Throwable?) :
    Exception(message, cause)

//--------------------------------------------------------------------------------------------------
class NewEmailUserException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: NewEmailErrCode
) : AuthenticationException(message, cause)

sealed class NewEmailErrCode(val info: String) : ErrorCode {
    class InvalidCredentialsError : NewEmailErrCode("E-mail e/ou senha inválidos.")
    class AccountCollisionError : NewEmailErrCode("Já existe uma conta registrada com este e-mail.")
    class NetworkError : NewEmailErrCode("Erro de rede. Verifique sua conexão com a internet.")
    class UnknownError : NewEmailErrCode("Ocorreu um erro desconhecido. Tente novamente mais tarde.")
}

//--------------------------------------------------------------------------------------------------
class SendEmailVerificationException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: SendEmailVerificationErrCode
) : AuthenticationException(message, cause)

sealed class SendEmailVerificationErrCode(val info: String) : ErrorCode {
    class NetworkError :
        SendEmailVerificationErrCode("Erro de rede. Verifique sua conexão com a internet.")

    class EmailNotFoundError :
        SendEmailVerificationErrCode("O e-mail informado não foi encontrado. Verifique se está correto.")

    class UnsuccessfulTaskError :
        SendEmailVerificationErrCode("A tarefa não foi bem-sucedida. Tente novamente.")

    class UnknownError :
        SendEmailVerificationErrCode("Ocorreu um erro desconhecido. Tente novamente mais tarde.")
}

//--------------------------------------------------------------------------------------------------
class UpdateUserProfileException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    val code: ErrorCode
) : AuthenticationException(message, cause)

sealed class UpdateProfileErrCode(val info: String) : ErrorCode {

    class Network :
        UpdateProfileErrCode("Erro de rede. Verifique sua conexão com a internet.")

    class UnsuccessfulTask :
        UpdateProfileErrCode("A tarefa não foi bem-sucedida. Tente novamente.")

    class Unknown :
        UpdateProfileErrCode("Ocorreu um erro desconhecido. Tente novamente mais tarde.")

}


sealed interface ErrorCode

