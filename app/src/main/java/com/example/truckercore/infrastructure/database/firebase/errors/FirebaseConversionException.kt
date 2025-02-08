package com.example.truckercore.infrastructure.database.firebase.errors

/**
 * Custom exception class that represents errors occurring during Firebase data conversion, such as
 * when attempting to convert firebase objects into dto objects.
 *
 * @param message The error message providing details about the specific issue.
 */
class FirebaseConversionException(message: String): FirebaseException()