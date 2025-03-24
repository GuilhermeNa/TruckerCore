package com.example.truckercore.view_model.enums

/**
 * Enum class representing different types of errors that can occur in the application.
 * Each error type is associated with a human-readable string (field name) that can be used for displaying error messages.
 */
enum class ErrorType(private val fieldName: String) {

    /**
     * Represents an error that occurs due to a network failure or connectivity issue.
     */
    NetworkError("Falha de conexão"),

    /**
     * Represents an error that occurs due to a component initialization.
     */
    InitializationError("Falha de inicialização"),

    /**
     * Represents an unknown error that could not be categorized.
     */
    UnknownError("Erro desconhecido");

    /**
     * Returns the human-readable name of the error type, which can be used for displaying
     * an error message to the user or for logging purposes.
     *
     * @return A string representing the error type in a user-friendly format.
     */
    fun getFieldName() = fieldName

}