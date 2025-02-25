package com.example.truckercore.shared.errors.search_params

/**
 * Custom exception class used to handle errors related to invalid document parameters.
 * This exception is thrown when parameters passed for document processing are considered invalid.
 *
 * @property message The detail message that explains the reason for the exception.
 */
class IllegalDocumentParametersException(message: String) : Exception(message)