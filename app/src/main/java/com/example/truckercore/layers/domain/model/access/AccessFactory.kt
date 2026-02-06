package com.example.truckercore.layers.domain.model.access

import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.AccessID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID

object AccessFactory {

    fun toAdmin(userId: UserID, companyID: CompanyID) =
        Access(
            id = AccessID.generate(),
            status = Status.ACTIVE,
            companyId = companyID,
            authorized = true,
            userId = userId,
            role = Role.ADMIN
        )

}