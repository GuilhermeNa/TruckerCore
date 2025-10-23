package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.data.infrastructure.security.configs.DefaultPermissionsProvider
import com.example.truckercore.data.infrastructure.security.data.Profile
import com.example.truckercore.data.infrastructure.security.data.enums.Role
import com.example.truckercore.data.modules.user.data.UserID
import com.example.truckercore.data.modules._shared.enums.PersistenceState

object UserFactory {

    operator fun invoke(form: UserForm): User {
        return User(
            id = UserID.generate(),
            companyId = form.companyId,
            uid = form.uid,
            profile = getProfile(form.role),
            persistenceState = PersistenceState.ACTIVE
        )
    }

    private fun getProfile(role: Role) = Profile(
        role = role,
        permissions = DefaultPermissionsProvider(role)
    )

}