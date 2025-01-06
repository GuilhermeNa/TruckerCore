package com.example.truckercore.infrastructure.database.firebase.exceptions

/**
 * Custom exception class that represents errors related to a null document snapshot.
 *
 * This exception is thrown when an operation encounters a null or missing document snapshot,
 * which typically occurs when querying a database, such as Firebase, and the expected document
 * does not exist or is not returned.
 *
 * @param message An optional error message providing details about the specific issue. Defaults to null.
 */
class NullDocumentSnapShotException(message: String? = null): Exception(message)