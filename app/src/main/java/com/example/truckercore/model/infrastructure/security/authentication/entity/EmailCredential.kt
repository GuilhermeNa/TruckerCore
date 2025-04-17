package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.FullName
import com.example.truckercore.model.shared.value_classes.Password

/**
 * Represents the credentials required for email-based authentication.
 *
 * This class aggregates the necessary information for identifying a user via
 * email and validating their access through a secure password.
 *
 * ---
 * ### Example:
 * ```kotlin
 * val name = FullName.from("João da Silva")
 * val email = Email("joao.silva@email.com")
 * val password = Password.from("123456")
 *
 * val credentials = EmailCredential(name, email, password)
 * ```
 *
 * @property name the full name of the user (validated and normalized)
 * @property email the email address of the user
 * @property password the user's hashed password
 */
class EmailCredential(
    val name: FullName,
    val email: Email,
    val password: Password
)