package com.example.truckercore.layers.domain.base.contracts

import com.example.truckercore.core.my_lib.classes.Cpf
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.drive_license.DriveLicense
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
    val email: Email?
    val userId: UserID?
    val cpf: Cpf
    val cnh: DriveLicense?

    fun nameValue() = name.value

    fun emailValue() = email?.value

}