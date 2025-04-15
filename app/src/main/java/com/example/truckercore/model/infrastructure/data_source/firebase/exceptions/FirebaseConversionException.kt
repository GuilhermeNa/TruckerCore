package com.example.truckercore.model.infrastructure.data_source.firebase.exceptions

/**
 * Custom exception class that represents errors occurring during Firebase data conversion, such as
 * when attempting to convert firebase objects into dto objects.
 *
 * @param message The error message providing details about the specific issue.
 */
class FirebaseConversionException(message: String): FirebaseException(message)