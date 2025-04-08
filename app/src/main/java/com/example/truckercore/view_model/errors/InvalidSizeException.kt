package com.example.truckercore.view_model.errors

/**
 * Custom exception to be thrown when the username size is invalid.
 * This exception should be used when the username doesn't meet the specified size criteria.
 *
 * @param message The error message that describes the exception condition.
 */
class InvalidSizeException(message: String) : Exception(message)
