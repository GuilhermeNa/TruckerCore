package com.example.truckercore.model.shared.errors.search_params

/**
 * Custom exception class used to handle errors related to invalid query parameters.
 * This exception is thrown when query parameters passed to a method or function are considered invalid.
 *
 * @property message The detail message that explains the reason for the exception.
 *
 */
class IllegalQueryParametersException(message: String): Exception(message)