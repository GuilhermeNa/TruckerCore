package com.example.truckercore.model.shared.errors

/**
 * Custom exception class that represents errors related to an invalid state in the application.
 *
 * This exception is thrown when an operation is attempted in an inappropriate or illegal state, such as
 * when the system or an object is not in the expected condition to perform a particular action.
 *
 * @param message An optional error message providing details about the specific issue. Defaults to null.
 */
class InvalidStateException(message: String) : Exception(message)