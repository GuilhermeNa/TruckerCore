package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidEmailException
import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidPasswordException
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.model.shared.utils.expressions.removeBlank
import java.util.Locale

/**
 * UserRegistration class is responsible for handling user registration and validating the details provided.
 * It ensures that the user's name, surname, email, and password meet specific criteria. It also formats
 * and cleans input values such as capitalizing the first letter of names and removing blank spaces.
 */
class EmailAuthCredential private constructor() {

    private lateinit var _email: String
    private lateinit var _password: String

    // Read-only properties to access the validated user details
    val email get() = _email
    val password get() = _password

    /**
     * Primary constructor that takes user details as input and validates them.
     * @param email The user's email address.
     * @param password The user's password.
     */
    constructor(email: String, password: String) : this() {
        this._email = validateEmail(email)
        this._password = validatePassword(password)
    }

    /**
     * Validates the user's email.
     * - Trims leading and trailing spaces.
     * - Converts the email to lowercase.
     * - Ensures the email is in a valid format (sample@mail.com).
     * @param email The email address to validate.
     * @return The validated email.
     * @throws InvalidEmailException if the email format is invalid.
     */
    private fun validateEmail(email: String): String {
        val trimmedEmail = email.removeBlank().lowercase(Locale.ROOT)

        if (!trimmedEmail.isEmailFormat()) throw InvalidEmailException(
            "Invalid email. The email must be in sample@mail.com."
        )

        return trimmedEmail
    }

    /**
     * Validates the user's password.
     * - Trims leading and trailing spaces.
     * - Ensures the password is at least 6 characters long.
     * @param password The password to validate.
     * @return The validated password.
     * @throws InvalidStateException if the password is shorter than 6 characters.
     */
    private fun validatePassword(password: String): String {
        val trimmedPass = password.removeBlank()

        if (trimmedPass.length < 6) throw InvalidPasswordException(
            "Password must have at least 6 characters."
        )

        return trimmedPass
    }

}