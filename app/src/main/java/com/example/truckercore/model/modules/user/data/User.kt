package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.infrastructure.security.contracts.Authorizable
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.modules._shared._contracts.entity.Entity
import com.example.truckercore.model.modules.authentication.contracts.Authenticable
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class User(
    override val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    override val profile: Profile
) : Entity<User>, Authenticable, Authorizable {

    override fun copyWith(persistence: PersistenceState): User {
        return copy(persistenceState = persistence)
    }

}
