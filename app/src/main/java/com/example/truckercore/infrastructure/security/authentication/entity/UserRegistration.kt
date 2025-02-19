package com.example.truckercore.infrastructure.security.authentication.entity

import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.utils.expressions.capitalizeFirst
import com.example.truckercore.shared.utils.expressions.isEmailFormat
import com.example.truckercore.shared.utils.expressions.isNameFormat
import com.example.truckercore.shared.utils.expressions.removeBlank
import java.util.Locale

/**
 * UserRegistration class is responsible for handling user registration and validating the details provided.
 * It ensures that the user's name, surname, email, and password meet specific criteria. It also formats
 * and cleans input values such as capitalizing the first letter of names and removing blank spaces.
 */
class UserRegistration() {

    private lateinit var _name: String
    private lateinit var _surname: String
    private lateinit var _email: String
    private lateinit var _password: String

    // Read-only properties to access the validated user details
    val name get() = _name
    val surname get() = _surname
    val email get() = _email
    val password get() = _password

    /**
     * Primary constructor that takes user details as input and validates them.
     * @param name The first name of the user.
     * @param surname The last name of the user.
     * @param email The user's email address.
     * @param password The user's password.
     */
    constructor(name: String, surname: String, email: String, password: String) : this() {
        this._name = validateName(name)
        this._surname = validateSurname(surname)
        this._email = validateEmail(email)
        this._password = validatePassword(password)
    }

    /**
     * Validates the user's name.
     * - Trims leading and trailing spaces.
     * - Capitalizes the first letter.
     * - Ensures the name only contains alphabetic characters.
     * @param name The first name to validate.
     * @return The formatted and validated name.
     * @throws InvalidStateException if the name contains invalid characters.
     */
    private fun validateName(name: String): String {
        val trimmedName = name.removeBlank().capitalizeFirst()

        if (!trimmedName.isNameFormat()) throw InvalidStateException(
            "Invalid name. The name must contain only " +
                    "letters and cannot have spaces or symbols."
        )

        return trimmedName
    }

    /**
     * Validates the user's surname.
     * - Trims leading and trailing spaces.
     * - Capitalizes the first letter.
     * - Ensures the surname only contains alphabetic characters.
     * @param surname The surname to validate.
     * @return The formatted and validated surname.
     * @throws InvalidStateException if the surname contains invalid characters.
     */
    private fun validateSurname(surname: String): String {
        val trimmedSurname = surname.removeBlank().capitalizeFirst()

        if (!trimmedSurname.isNameFormat()) throw InvalidStateException(
            "Invalid surname. The surname must contain only " +
                    "letters and cannot have spaces or symbols."
        )

        return trimmedSurname
    }

    /**
     * Validates the user's email.
     * - Trims leading and trailing spaces.
     * - Converts the email to lowercase.
     * - Ensures the email is in a valid format (sample@mail.com).
     * @param email The email address to validate.
     * @return The validated email.
     * @throws InvalidStateException if the email format is invalid.
     */
    private fun validateEmail(email: String): String {
        val trimmedEmail = email.removeBlank().lowercase(Locale.ROOT)

        if (!trimmedEmail.isEmailFormat()) throw InvalidStateException(
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

        if (trimmedPass.length < 6) throw InvalidStateException(
            "Password must have at least 6 characters."
        )

        return trimmedPass
    }

    /**
     * Returns the complete name of the user by combining the first name and surname.
     * @return A string containing the full name in the format "FirstName LastName".
     */
    fun getCompleteName() = "$name $_surname"

}