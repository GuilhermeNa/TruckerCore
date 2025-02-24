package com.example.truckercore.infrastructure.security.authentication.entity

import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.utils.expressions.capitalizeFirstChar
import com.example.truckercore.shared.utils.expressions.isEmailFormat
import com.example.truckercore.shared.utils.expressions.isNameFormat
import com.example.truckercore.shared.utils.expressions.removeBlank
import java.util.Locale

// Data class for storing user access requirements
class NewAccessRequirements private constructor() {

    private lateinit var _uid: String
    private lateinit var _name: String
    private lateinit var _surname: String
    private lateinit var _email: String
    private lateinit var _personFlag: PersonCategory

    // Read-only properties to access the validated user details
    val uid get() = _uid
    val name get() = _name
    val surname get() = _surname
    val email get() = _email
    val personFlag get() = _personFlag

    constructor(
        uid: String,
        name: String,
        surname: String,
        email: String,
        personFlag: PersonCategory
    ) : this() {
        _uid = validateUid(uid)
        _name = validateName(name)
        _surname = validateSurname(surname)
        _email = validateEmail(email)
        _personFlag = personFlag
    }
    /**
     * Validates the user's email.
     * - Strips leading and trailing spaces.
     * - Converts the email to lowercase.
     * - Ensures the email is in a valid format (e.g., sample@mail.com).
     * @param email The email address to validate.
     * @return The validated email as a string.
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
     * Validates the surname to ensure it only contains letters.
     * - Strips leading and trailing spaces.
     * - Capitalizes the first letter.
     * - Checks if the surname contains only alphabetic characters.
     * @param surname The surname to validate.
     * @return The validated surname as a string.
     * @throws InvalidStateException if the surname contains non-letter characters.
     */
    private fun validateSurname(surname: String): String {
        val trimmedSurname = surname.removeBlank().capitalizeFirstChar()

        if (!trimmedSurname.isNameFormat()) throw InvalidStateException(
            "The surname must contain only letters."
        )

        return trimmedSurname
    }

    /**
     * Validates the UID to ensure it is not blank.
     * @param uid The UID to validate.
     * @return The validated UID as a string.
     * @throws InvalidStateException if the UID is blank.
     */
    private fun validateUid(uid: String): String {
        val trimmedUid = uid.removeBlank()

        if (trimmedUid.isBlank()) throw InvalidStateException(
            "Blank UID is not allowed when creating a new system access."
        )

        return trimmedUid
    }

    /**
     * Validates the name to ensure it only contains letters.
     * - Strips leading and trailing spaces.
     * - Capitalizes the first letter.
     * - Checks if the name contains only alphabetic characters.
     * @param name The name to validate.
     * @return The validated name as a string.
     * @throws InvalidStateException if the name contains non-letter characters.
     */
    private fun validateName(name: String): String {
        val trimmedName = name.removeBlank().capitalizeFirstChar()

        if (!trimmedName.isNameFormat()) throw InvalidStateException(
            "The name must contain only letters."
        )

        return trimmedName
    }

}

