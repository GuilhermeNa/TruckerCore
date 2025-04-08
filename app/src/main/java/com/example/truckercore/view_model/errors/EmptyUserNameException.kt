package com.example.truckercore.view_model.errors

/**
 * Custom exception to be thrown when the username is empty.
 * This exception should be used when the username doesn't meet the minimum requirement of being non-empty.
 *
 * @param message The error message that describes the exception condition.
 */
class EmptyUserNameException(message: String): Exception(message)