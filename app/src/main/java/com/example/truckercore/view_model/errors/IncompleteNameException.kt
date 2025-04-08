package com.example.truckercore.view_model.errors

/**
 * Custom exception to be thrown when the username is incomplete.
 * This exception should be used when the username only contains one word
 * (such as a first name without a last name), where a full name (first and last name)
 * is expected.
 *
 * @param message The error message that describes the exception condition.
 */
class IncompleteNameException(message: String) : Exception(message)
