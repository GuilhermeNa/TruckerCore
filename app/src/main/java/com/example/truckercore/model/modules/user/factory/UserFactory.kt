package com.example.truckercore.model.modules.user.factory

import com.example.truckercore.model.infrastructure.security.configs.DefaultPermissionsProvider
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules._shared.enums.PersistenceState

object UserFactory {

    operator fun invoke(form: UserForm): User {
        return User(
            id = UserID.generate(),
            companyId = form.companyId,
            uid = form.uid,
            profile = getProfile(form.role),
            persistence = PersistenceState.ACTIVE
        )
    }

    private fun getProfile(role: Role) = Profile(
        role = role,
        permissions = DefaultPermissionsProvider(role)
    )

}