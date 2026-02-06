package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.user.User

/**
 * Represents a person within the domain.
 *
 * A Person contains basic identification and contact information.
 * It may or may not be linked to a [User] account:
 *
 * - When linked to a [User], the person has access to the system.
 * - When not linked, the person represents a registered employee
 *   without system access granted.
 */
interface Person : Entity {
    val name: Name
    val email: Email
    val userId: UserID?

    fun nameValue() = name.value

    fun emailValue() = email.value

}