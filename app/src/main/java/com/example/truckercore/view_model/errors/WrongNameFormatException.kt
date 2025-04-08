package com.example.truckercore.view_model.errors

/**
 * Custom exception to be thrown when the username format is wrong.
 * This exception is used when any word in the username contains invalid characters
 * (such as digits, symbols, or other non-alphabetic characters).
 *
 * @param message The error message that describes the exception condition.
 */
class WrongNameFormatException(message: String) : Exception(message)
