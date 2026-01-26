package com.example.truckercore.core.my_lib.classes

import com.example.truckercore.core.error.DomainException

/**
 * Represents a password.
 *
 * @property value the password string
 */
@JvmInline
value class Password private constructor(val value: String) {

    companion object {

        /**
         * Factory method that validates a raw password before creating a [Password] value object.
         *
         * Validation rules:
         * - Must not be blank
         * - Must contain only digits
         * - Length must be between 6 and 12 characters
         *
         * @param raw The plain-text password provided by the user.
         * @return A [Password] instance with a validated value.
         * @throws DomainException.InvalidPassword If the password does not meet the rules.
         */
        fun from(raw: String): Password {

            // Step 1: Validate that the password is not blank
            if (raw.isBlank()) {
                throw DomainException.InvalidPassword("Password must not be blank.")
            }

            // Step 2: Validate the password against the rule
            if (!rule(raw)) {
                throw DomainException.InvalidPassword(
                    "Password must contain only numbers and be 6 to 12 characters long. Provided: [$raw]."
                )
            }

            // Step 3: Create and return the Password value object
            return Password(raw)
        }

        // Validates the password format using a regex rule.
        private fun rule(raw: String) = raw.matches("^\\d{6,12}$".toRegex())
    }

}