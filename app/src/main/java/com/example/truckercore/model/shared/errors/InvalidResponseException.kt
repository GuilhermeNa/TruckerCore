package com.example.truckercore.model.shared.errors

/**
 * Custom exception class to represent an invalid response scenario.
 *
 * This exception is thrown when a response doesn't meet the expected
 * criteria or is deemed invalid. It extends from the base `Exception` class
 * and carries a message detailing the nature of the invalid response.
 *
 * @param message The error message that provides more details about the invalid response.
 */
class InvalidResponseException(message: String? = null): Exception(message)