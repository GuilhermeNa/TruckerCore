package com.example.truckercore._utils.classes

import com.example.truckercore._utils.classes.FullName.Companion.from

/**
 * Represents a validated and formatted full name.
 *
 * This inline class enforces formatting and validation rules to ensure consistency and correctness
 * of personal names throughout the domain layer.
 *
 * ### Creation
 * Use the [from] factory method to create a [FullName] instance. This ensures the name is
 * formatted correctly (e.g., capitalized) and validated before use.
 *
 * ### Validation Rules:
 * - Name must **not** be blank.
 * - Name must contain **at least two words** (e.g., "John Doe").
 * - Name must consist of **letters and spaces only** (no digits or symbols).
 *
 * If any rule is violated, an [InvalidNameException] is thrown.
 *
 * ### Example:
 * ```kotlin
 * val name = FullName.from("joao da silva") // ✅ "Joao Da Silva"
 * val invalid = FullName.from("joao")       // ❌ throws InvalidNameException
 * ```
 *
 * @property value The validated and formatted full name
 * @throws InvalidNameException if the input is blank or invalid
 */
@JvmInline
value class FullName private constructor(val value: String) {

    init {
        if (value.isBlank()) {
            throw InvalidNameException("Name must not be blank.")
        }
        if (!value.isFullNameFormat()) {
            throw InvalidNameException("Invalid name format: '$value'")
        }
    }

    private fun String.isFullNameFormat(): Boolean {
        val trimmed = this.trim()
        val parts = trimmed.split("\\s+".toRegex())
        return parts.size >= 2 && trimmed.matches("[\\p{L} ]+".toRegex())
    }

    companion object {

        /**
         *  Automatically formats the name by:
         *  - Trimming leading/trailing spaces
         *  - Lowercasing the entire input
         *  - Capitalizing the first letter of each word
         */
        fun from(raw: String): FullName {
            val formatted = raw.formatAsFullName()
            return FullName(formatted)
        }

        private fun String.formatAsFullName(): String {
            return trim().split(Regex("\\s+")) // Evita strings vazias se houver múltiplos espaços
                .joinToString(" ") { word ->
                    if (word.length <= 2) word.lowercase()
                    else word.lowercase().replaceFirstChar { it.titlecase() }
                }
        }

    }

}
class InvalidNameException(message: String? = null) : Exception(message)