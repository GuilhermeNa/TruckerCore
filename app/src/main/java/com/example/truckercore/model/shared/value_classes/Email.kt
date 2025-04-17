package com.example.truckercore.model.shared.value_classes

import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.model.shared.value_classes.exceptions.InvalidEmailException

/**
 * Represents a validated email address.
 *
 * This inline class enforces validation rules at creation time to ensure the email is well-formed.
 *
 * ### Validation Rules:
 * - Email must **not** be blank.
 * - Email must follow a valid format (e.g., `user@example.com`).
 *
 * If any rule is violated, an [InvalidEmailException] is thrown.
 *
 * ### Example:
 *
 * ```kotlin
 * val email = Email("john.doe@example.com") // ✅ valid
 *
 * val invalidEmail = Email("")              // ❌ throws InvalidEmailException
 * val malformedEmail = Email("invalid@")    // ❌ throws InvalidEmailException
 * ```
 *
 * You can also use an extension function like `String.isEmailFormat()` to validate the format separately.
 *
 * @property value the raw email string
 * @throws InvalidEmailException if the email is blank or has an invalid format
 */
@JvmInline
value class Email(val value: String) {

    init {
        if (value.isBlank()) {
            throw InvalidEmailException("Email must not be blank.")
        }
        if (!value.isEmailFormat()) {
            throw InvalidEmailException("Invalid email format: '$value'")
        }
    }

}