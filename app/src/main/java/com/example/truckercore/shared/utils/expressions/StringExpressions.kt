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

/**
 * Extension function that capitalizes the first letter of the string and makes the rest lowercase.
 * @receiver String The string to capitalize.
 * @return The string with the first letter capitalized and the rest in lowercase.
 */
fun String.capitalizeFirst(): String =
    this.lowercase().replaceFirstChar { it.uppercase() }

/**
 * Extension function to check if the string is in a valid name format (only alphabetic characters).
 * @receiver String The string to check.
 * @return True if the string contains only alphabetic characters.
 */
fun String.isNameFormat(): Boolean = this.matches("[a-zA-z]+".toRegex())

fun String.isEmailFormat(): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
    return this.matches(emailRegex)
}

/**
 * Extension function that removes all blank spaces from the string.
 * @receiver String The string from which spaces should be removed.
 * @return The string with all spaces removed.
 */
fun String.removeBlank() = this.replace("\\s".toRegex(), "")