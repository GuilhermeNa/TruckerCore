package com.example.truckercore._utils.classes

import com.example.truckercore._utils.classes.Password.Companion.from
import java.security.MessageDigest

/**
 * Represents a validated and securely hashed password.
 *
 * This value class encapsulates a password value by:
 * - Enforcing format validation rules before creation
 * - Automatically hashing the raw password upon instantiation
 *
 * Instances must be created using the [from] factory method to ensure safety and consistency.
 *
 * ---
 * ### Validation Rules:
 * - Password must be composed **only of digits** (`0-9`)
 * - Password length must be between **6 and 12 characters**
 *
 * If validation fails, an [InvalidPasswordException] is thrown.
 *
 * ---
 * ### Default Hashing Algorithm:
 * - SHA-256 (can be changed by passing a different algorithm string)
 *
 * ### Example:
 * ```kotlin
 * val password = Password.from("123456")         // ✅ result: "8d969eef6ecad3c29a3a629280e686cf0e4eecb019b6ccac4de9cb7f0b0c97cd" (SHA-256)
 * val invalid = Password.from("abc123")          // ❌ throws InvalidPasswordException
 * val tooShort = Password.from("123")            // ❌ throws InvalidPasswordException
 * val tooLong = Password.from("1234567890123")   // ❌ throws InvalidPasswordException
 * ```
 *
 * @property value the hashed password string
 * @throws InvalidPasswordException if the raw password does not meet the required format
 */
@JvmInline
value class Password private constructor(val value: String) {

    companion object {

        /**
         * Factory method that validates and hashes a raw password before instantiating [Password].
         *
         * @param raw the plain-text password input
         * @return a [Password] instance with a hashed value
         * @throws InvalidPasswordException if validation fails
         */
        fun from(raw: String): Password {
            validateRule(raw)
            return Password(raw.toHash())
        }

        // Hashes the raw password using the specified algorithm.
        private fun String.toHash(algorithm: String = "SHA-256"): String {
            return MessageDigest
                .getInstance(algorithm)
                .digest(this.toByteArray())
                .fold("") { str, byte ->
                    str + "%02x".format(byte)
                }
        }

        // Validates whether the raw password meets the expected rule.
        private fun validateRule(raw: String) {
            if (!isValidRule(raw))
                throw InvalidPasswordException(
                    "Password must contain only numbers and be 6 to 12 characters and got: [$raw]."
                )
        }

        // Expected rules for validation
        private fun isValidRule(raw: String) = raw.matches("^\\d{6,12}$".toRegex())

    }

}

class InvalidPasswordException(message: String? = null): Exception(message)



