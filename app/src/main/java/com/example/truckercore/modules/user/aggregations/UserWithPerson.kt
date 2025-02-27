package com.example.truckercore.modules.user.aggregations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.Person

/**
 * Data class that combines a [User] entity with associated [Person] data.
 * This class represents a user alongside their corresponding person information.
 *
 * @property user The [User] entity containing the user's details such as credentials and other user-related data.
 * @property person The [Person] entity containing personal information such as name, age, address, etc.
 *
 * @see User
 * @see Person
 */
data class UserWithPerson(
    val user: User,
    val person: Person
)
