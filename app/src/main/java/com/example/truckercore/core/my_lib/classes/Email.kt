package com.example.truckercore.core.my_lib.classes

import com.example.truckercore.core.error.DomainException

/**
 * Represents a validated email address.
 *
 * This inline class enforces validation rules at creation time to ensure the email is well-formed.
 *
 * ### Example:
 *
 * ```kotlin
 * val email = Email.from("john.doe@example.com")      // ✅ valid
 * val upper = Email.from("JOHN.doe@example.com")      // ✅ normalized to lowercase
 * val blank = Email.from("")                          // ❌ throws InvalidEmailException
 * val malformed = Email.from("invalid@")              // ❌ throws InvalidEmailException
 * ```
 *
 * @property value The validated and normalized (lowercase) email string.
 */
@JvmInline
value class Email private constructor(val value: String) {

    companion object {

        /**
         * Creates a validated [Email] value object from a raw string input.
         *
         * This factory method is the single entry point for creating an [Email]
         * instance, ensuring that all domain validation rules are applied
         * before the object is instantiated.
         *
         * Validation rules:
         * - The email must not be blank or empty
         * - The email must match a valid email format
         * - The email value is normalized to lowercase
         *
         * @param raw The raw email string provided by the user or external source.
         * @return A validated [Email] instance with a normalized lowercase value.
         * @throws DomainException.InvalidEmail If any validation rule is violated.
         */
        fun from(raw: String): Email {

            // Step 1: Validate that the input is not empty or blank
            // Blank values are not allowed in the domain
            if (raw.isBlank()) {
                throw DomainException.InvalidEmail(
                    "The email address cannot be empty or blank."
                )
            }

            // Step 2: Validate the email format using a regular expression
            // This ensures the value follows a standard email structure
            val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
            if (!raw.matches(emailRegex)) {
                throw DomainException.InvalidEmail(
                    "The email address format is invalid: '$raw'."
                )
            }

            // Step 3: Normalize the value by converting it to lowercase
            // This prevents case-sensitivity issues across the system
            val normalizedEmail = raw.lowercase()

            // Step 4: Create and return the Email value object
            return Email(normalizedEmail)
        }

    }

}