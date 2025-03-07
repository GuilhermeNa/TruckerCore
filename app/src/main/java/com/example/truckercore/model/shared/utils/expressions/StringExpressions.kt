package com.example.truckercore.model.shared.utils.expressions

/**
 * Extension function that capitalizes the first letter of the string and makes the rest lowercase.
 *
 * @receiver String The string to capitalize.
 *
 * @return The string with the first letter capitalized and the rest in lowercase.
 */
fun String.capitalizeFirstChar(): String =
    this.lowercase().replaceFirstChar { it.uppercase() }

/**
 * Extension function to check if the string is in a valid name format (only alphabetic characters).
 *
 * This function returns `true` if the string consists only of alphabetic characters (letters),
 * and `false` if it contains anything other than alphabetic characters.
 *
 * @receiver String The string to check.
 * @return `true` if the string contains only alphabetic characters; `false` otherwise.
 */
fun String.isNameFormat(): Boolean = this.matches("[\\p{L}]+".toRegex())

/**
 * Extension function to check if the string is in a valid email format.
 *
 * This function checks whether the string matches the pattern of a valid email address.
 * The pattern allows for alphanumeric characters, periods, and special characters like `+` or `%`
 * before the `@` symbol, followed by a valid domain.
 *
 * @receiver String The string to check.
 * @return `true` if the string matches a valid email format; `false` otherwise.
 */
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