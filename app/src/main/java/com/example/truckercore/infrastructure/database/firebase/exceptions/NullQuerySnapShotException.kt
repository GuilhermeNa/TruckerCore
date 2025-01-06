package com.example.truckercore.infrastructure.database.firebase.exceptions

/**
 * Custom exception class that represents errors related to a null query snapshot.
 *
 * This exception is thrown when an operation encounters a null or missing query snapshot,
 * which typically happens when querying a database or collection, such as Firebase, and no
 * results are returned or the query fails to return a valid snapshot.
 *
 * @param message An optional error message providing details about the specific issue. Defaults to null.
 */
class NullQuerySnapShotException(message: String? = null): Exception(message)