package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.Person
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access

data class User(
    val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val status: Status,
    val access: Access,
    val person: Person
) : Entity {

    init {
        validate()
    }

    private fun validate() {
        checkFK(access.userId)
        checkFK(person.userId!!)
    }

}