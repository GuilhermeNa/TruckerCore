package com.example.truckercore.model.shared.utils.expressions

/**
 * Extension function for the [String] class that capitalizes the first letter of
 * every word in the string, while making the rest of the letters lowercase.
 * Words are considered as substrings separated by spaces.
 *
 * This function is useful for formatting strings where each word should start with
 * an uppercase letter, such as titles or names.
 *
 * Example:
 * ```
 * "hello world".capitalizeEveryFirstChar() // Returns "Hello World"
 * ```
 *
 * @return A new string where the first letter of each word is capitalized and the
 *         remaining letters are lowercase.
 */
fun String.capitalizeEveryFirstChar(): String {
    val arr = this.split(" ")
    val upperCaseArr = arr.map { it.lowercase().replaceFirstChar { it.uppercase() } }
    return upperCaseArr.joinToString(" ")
}

/**
 * Extension function to check if the string is in a valid name format (only alphabetic characters).
 *
 * This function returns `true` if the string consists only of alphabetic characters (letters),
 * and `false` if it contains anything other than alphabetic characters.
 *
 * @receiver String The string to check.
 * @return `true` if the string contains only alphabetic characters; `false` otherwise.
 */
fun String.isNameFormat(): Boolean = this.matches("[\\p{L} ]+".toRegex())

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