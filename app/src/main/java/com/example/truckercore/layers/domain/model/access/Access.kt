package com.example.truckercore.layers.domain.model.access

import com.example.truckercore.infra.security.data.enums.Role
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID

data class Access(
    override val id: ID,
    override val persistence: PersistenceState,
    override val companyId: CompanyID,
    val userId: UserID,
    val role: Role
) : Entity {

}