package com.example.truckercore.core.my_lib.classes

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.expressions.isFullNameFormat
import com.example.truckercore.core.my_lib.expressions.normalizeAsFullName

/**
 * Represents a validated and formatted name.
 *
 * This inline class enforces formatting and validation rules to ensure consistency and correctness
 * of personal names throughout the domain layer.
 *
 * ### Example:
 * ```kotlin
 * val name = FullName.from("joao da silva") // ✅ "Joao Da Silva"
 * val invalid = FullName.from("joao")       // ❌ throws InvalidNameException
 * ```
 *
 * @property value The validated and formatted full name
 */
@JvmInline
value class Name private constructor(val value: String) {

    companion object {

        /**
         * Creates a validated [Name] value object from a raw string input.
         *
         * This factory method performs all validations and normalization
         * before creating the value object.
         *
         * Validation rules:
         * - The name must not be blank
         * - The name must contain at least two words
         * - The name must contain only alphabetic characters and spaces
         *
         * Normalization rules:
         * - Trims leading and trailing spaces
         * - Collapses multiple spaces into a single space
         * - Converts all words to lowercase
         * - Capitalizes the first letter of words longer than two characters
         *
         * Short words (length ≤ 2) remain lowercase to preserve common
         * name particles such as "de", "da", or "of".
         *
         * @param raw The raw name string provided by the user.
         * @return A formatted and validated [Name] instance.
         * @throws DomainException.InvalidName If any validation rule is violated.
         */
        fun from(raw: String): Name {

            // Step 1: Validate that the input is not blank
            if (raw.isBlank()) {
                throw DomainException.InvalidName("Name must not be blank.")
            }

            // Step 2: Validate full name format
            if (!raw.isFullNameFormat()) {
                throw DomainException.InvalidName("Invalid name format: '$raw'")
            }

            // Step 3: Format and normalize the raw name input
            val formatted = raw.normalizeAsFullName()

            // Step 4: Create the Name value object
            return Name(formatted)
        }

    }

}