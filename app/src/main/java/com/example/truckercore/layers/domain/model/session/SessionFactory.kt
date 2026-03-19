package com.example.truckercore.layers.domain.model.session

import com.example.truckercore.layers.domain.base.contracts.Employee
import com.example.truckercore.layers.domain.model.access.Access
import com.example.truckercore.layers.domain.model.user.UserDraft
import com.example.truckercore.layers.domain.model.user.UserFactory

object SessionFactory {

    fun toEntity(
        userDraft: UserDraft,
        access: Access,
        employee: Employee,
        active: Boolean
    ): Session {
        val user = UserFactory.toEntity(userDraft, access, employee)
        return Session(user, active)
    }

}