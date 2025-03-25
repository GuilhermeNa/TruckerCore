package com.example.truckercore.view_model.enums

/**
 * Enum class representing different types of errors that can occur in the application.
 * Each error type is associated with a human-readable string (field name) that can be used for displaying error messages.
 */
enum class ErrorType(
    private val fieldName: String,
    private val message: String
) {

    NetworkError(
        fieldName = "Falha de conexão",
        message = "Não foi possível estabelecer uma conexão com o servidor."
    ),
    InitializationError(
        fieldName = "Falha de inicialização",
        message = "Erro ao inicializar o sistema. Tente novamente mais tarde."
    ),
    UnknownError(
        fieldName = "Erro desconhecido",
        message = "Ocorreu um erro inesperado. Tente novamente mais tarde."
    ),
    UnexpectedResponse(
        fieldName = "Resposta inesperada",
        message = "A resposta recebida do servidor é inválida ou inesperada."
    ),

    // Autenticação
    UserCollisionError(
        fieldName = "Credencial existente",
        message = "Já existe um usuário registrado com essa credencial."
    ),
    InvalidUserError(
        fieldName = "Credencial não encontrada",
        message = "A credencial informada não foi encontrada ou está desativada."
    ),
    WeakPasswordError(
        fieldName = "Senha inválida",
        message = "A senha deve ter pelo menos 6 caracteres."
    )
    ;


    /**
     * Returns the human-readable name of the error type, which can be used for displaying
     * an error message to the user or for logging purposes.
     *
     * @return A string representing the error type in a user-friendly format.
     */
    fun getFieldName() = fieldName

}

enum class Teste {}