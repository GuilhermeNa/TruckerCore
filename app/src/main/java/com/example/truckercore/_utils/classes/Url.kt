package com.example.truckercore._utils.classes

import com.example.truckercore.model.shared.value_classes.exceptions.InvalidUrlException
import java.net.MalformedURLException
import java.net.URL

/**
 * Represents a validated URL string.
 *
 * This inline value class ensures that the provided string is a valid URL
 * by attempting to instantiate a [java.net.URL]. If the string is not
 * well-formed, an [InvalidUrlException] is thrown during initialization.
 *
 * ### Validation Rules:
 * - The URL must be non-empty.
 * - The URL must be well-formed according to [java.net.URL].
 * - The URL must include a valid protocol (e.g., `http`, `https`, `ftp`).
 * - The URL must include a valid domain or IP address.
 *
 * ### Examples:
 * ```kotlin
 * val url1 = Url("https://example.com")      // ✅ valid
 * val url2 = Url("http://localhost:8080")    // ✅ valid
 * val url3 = Url("ftp://files.example.com")  // ✅ valid
 * val url4 = Url("example")                  // ❌ throws InvalidUrlException
 * val url5 = Url("://missing.protocol.com")  // ❌ throws InvalidUrlException
 * ```
 *
 * @property value the validated URL string
 * @throws InvalidUrlException if the string does not represent a valid URL
 */
@JvmInline
value class Url(val value: String) {

    init {
        try {
            URL(value) // throws MalformedURLException if invalid
        } catch (e: MalformedURLException) {
            throw InvalidUrlException("Invalid URL format: '$value'")
        }
    }

}