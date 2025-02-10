package com.example.truckercore.modules.user.aggregations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.Person

data class UserWithPerson(
    val user: User,
    val person: Person
)
