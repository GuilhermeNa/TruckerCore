package com.example.truckercore.model.infrastructure.database.firebase.errors

/**
 * Exception thrown when a Firebase request fails.
 *
 * This exception is used to represent errors that occur during a request to Firebase, such as network issues,
 * invalid responses, or authorization failures.
 *
 * @param message The error message that describes the failure in detail.
 */
class FirebaseRequestException(message: String): FirebaseException(message)