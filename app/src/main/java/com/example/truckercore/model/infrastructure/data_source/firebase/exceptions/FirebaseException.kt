package com.example.truckercore.model.infrastructure.data_source.firebase.exceptions

/**
 * Base class for Firebase-related exceptions.
 *
 * This exception serves as the base class for all exceptions specific to Firebase operations.
 * It is extended by other exceptions that represent specific error conditions in the Firebase context.
 */
abstract class FirebaseException(message: String): Exception(message)