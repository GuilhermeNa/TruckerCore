package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID

data class User(
    val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val persistence: PersistenceState
) : Entity {


}