package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidEmailException
import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidPasswordException
import com.example.truckercore.model.infrastructure.security.authentication.expressions.toHash
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.utils.expressions.capitalizeEveryFirstChar
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.model.shared.utils.expressions.removeBlank
import com.example.truckercore.view_model.expressions.validateUserName
import java.util.Locale

/**
 * UserRegistration class is responsible for handling user registration and validating the details provided.
 * It ensures that the user's name, surname, email, and password meet specific criteria. It also formats
 * and cleans input values such as capitalizing the first letter of names and removing blank spaces.
 */
class EmailAuthCredential private constructor() {

    private lateinit var _name: String
    private lateinit var _email: String
    private lateinit var _password: String

    // Read-only properties to access the validated user details
    val name get() = _name
    val email get() = _email
    val password get() = _password

    /**
     * Primary constructor that takes user details as input and validates them.
     * @param email The user's email address.
     * @param password The user's password.
     */
    constructor(name: String, email: String, password: String) : this() {
        this._name = validateName(name)
        this._email = validateEmail(email)
        this._password = validatePassword(password).toHash()
    }

    /**
     * Function to validate the username and return the original name if valid.
     *
     * @param name The username to be validated.
     * @return The original username if valid.
     * @throws EmptyUserNameException If the username is empty after trimming.
     * @throws InvalidSizeException If the username size is not within the valid range (5 to 29 characters).
     * @throws IncompleteNameException If the username does not contain both a first and last name.
     * @throws WrongNameFormatException If any word in the username contains invalid characters (e.g., numbers or special symbols).
     */
    private fun validateName(name: String): String {
        name.validateUserName()
        return name.trimStart().trimEnd().capitalizeEveryFirstChar()
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

        if (trimmedPass.length < 6 || trimmedPass.length > 12) throw InvalidPasswordException(
            "Password must have at least 6 characters."
        )

        return trimmedPass
    }

}