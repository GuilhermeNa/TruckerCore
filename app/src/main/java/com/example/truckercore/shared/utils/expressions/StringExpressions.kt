package com.example.truckercore.shared.utils.expressions

/**
 * Extension function to validate that a String is not blank.
 *
 * This function checks if the string is empty or contains only spaces and
 * throws an IllegalArgumentException if the string is invalid.
 *
 * @param fieldName The name of the field being validated (default is "string").
 *                  This will be included in the exception message to indicate which field is invalid.
 * @throws IllegalArgumentException if the string is blank (empty or only spaces).
 */
fun String.validateIsNotBlank(fieldName: String? = "string") {
    if (isBlank()) {
        throw IllegalArgumentException("The provided $fieldName is invalid: it cannot be blank.")
    }
}

