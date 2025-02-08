package com.example.truckercore.infrastructure.database.firebase.errors

/**
 * Exception thrown when there is an issue with the Firebase query builder.
 *
 * This exception is thrown when a query cannot be properly constructed, typically due to invalid parameters
 * or an issue with how the query is built.
 */
class FirebaseQueryBuilderException: FirebaseException()