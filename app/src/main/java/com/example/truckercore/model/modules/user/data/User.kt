package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.infrastructure.security.contracts.Authorizable
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.modules._shared.contracts.entity.Entity
import com.example.truckercore.model.modules.authentication.contracts.Authenticable
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class User(
    override val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    override val profile: Profile
) : Entity, Authenticable, Authorizable {

    val uidValue get() = uid.value
    val idValue get() = id.value
    val profileRole get() = profile.role
    val companyIdValue get() = companyId.value

}
