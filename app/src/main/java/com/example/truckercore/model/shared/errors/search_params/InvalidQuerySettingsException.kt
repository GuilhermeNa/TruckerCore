package com.example.truckercore.model.shared.errors.search_params

/**
 * Custom exception thrown when invalid query settings are encountered.
 *
 * This exception is used to indicate issues in the query settings, such as incorrect search configurations.
 *
 * @param message The message describing the cause of the exception.
 */
class InvalidQuerySettingsException(message: String): Exception(message)