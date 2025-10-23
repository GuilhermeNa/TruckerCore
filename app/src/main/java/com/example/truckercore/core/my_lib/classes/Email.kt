package com.example.truckercore.core.my_lib.classes

/**
 * Represents a validated email address.
 *
 * This inline class enforces validation rules at creation time to ensure the email is well-formed.
 *
 * ### Validation Rules:
 * - Email must **not** be blank.
 * - Email must follow a valid format (e.g., `user@example.com`).
 * - Email is normalized to lowercase.
 *
 * If any rule is violated, an [InvalidEmailException] is thrown.
 *
 * ### Example:
 *
 * ```kotlin
 * val email = Email.from("john.doe@example.com") // ✅ valid
 * val upper = Email.from("JOHN.doe@example.com")      // ✅ normalized to lowercase
 * val blank = Email.from("")                          // ❌ throws InvalidEmailException
 * val malformed = Email.from("invalid@")              // ❌ throws InvalidEmailException
 * ```
 *
 * @property value The validated and normalized (lowercase) email string.
 * @throws InvalidEmailException if the input is blank or incorrectly formatted.
 */
@JvmInline
value class Email private constructor(val value: String) {

    companion object {
        /**
         * Factory method to create a validated [Email] instance.
         *
         * This method performs format and emptiness checks and throws an exception
         * if any validation rule is broken.
         *
         * @param raw the raw email string input.
         * @return a validated [Email] instance with lowercase value.
         * @throws InvalidEmailException if the input is blank or has invalid format.
         */
        fun from(raw: String): Email {
            checkEmpty(raw)
            checkFormat(raw)
            return Email(raw.lowercase())
        }

        private fun checkEmpty(value: String) {
            if (value.isBlank()) {
                throw InvalidEmailException("The email address cannot be empty or blank.")
            }
        }

        private fun checkFormat(value: String) {
            val isEmailFormat =
                value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())
            if (!isEmailFormat) {
                throw InvalidEmailException("The email address format is invalid: '$value'.")
            }
        }
    }

}

/**
 * Exception thrown when an [Email] creation fails due to validation issues.
 *
 * This can happen if:
 * - The input string is blank.
 * - The input string does not match the required email format.
 *
 * @param message A detailed message indicating the validation error.
 */
class InvalidEmailException(message: String? = null) : Exception(message)
