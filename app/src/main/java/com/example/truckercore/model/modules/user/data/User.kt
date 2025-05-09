package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.infrastructure.security.contracts.Authorizable
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.modules._contracts.Entity
import com.example.truckercore.model.modules.authentication.contracts.Authenticable
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.shared.enums.Persistence

data class User(
    override val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    override val profile: Profile
) : Entity, Authenticable, Authorizable {

    fun getRole() = profile.role

}
