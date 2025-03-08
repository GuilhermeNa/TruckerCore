package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.InvalidStateException

/**
 * Represents the details of a logged-in user, including both user information and additional personal details.
 *
 * @param user The basic user details (a `User` object).
 * @param personWD The additional person details (a `PersonWithDetails` object) related to the user.
 *
 * @throws InvalidStateException If the `userId` of the `personWD` does not match the `id` of the provided `user`.
 */
data class LoggedUserDetails(
    val user: User,
    val personWD: PersonWithDetails
) {

    init {
        // Validate PersonWithDetails
        if (personWD.userId != user.id)
            throw InvalidStateException("Person details does not belong to the provided user.")
    }

}
