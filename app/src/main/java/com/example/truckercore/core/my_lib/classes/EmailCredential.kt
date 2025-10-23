package com.example.truckercore.core.my_lib.classes

/**
 * Represents the credentials required for email-based authentication.
 *
 * This class aggregates the necessary information for identifying a user via
 * email and validating their access through a secure password.
 *
 * ---
 * ### Example:
 * ```kotlin
 * val email = Email("joao.silva@email.com")
 * val password = Password.from("123456")
 *
 * val credentials = EmailCredential(email, password)
 * ```
 *
 * @property email the email address of the user
 * @property password the user's hashed password
 */
class EmailCredential(val email: Email, val password: Password)