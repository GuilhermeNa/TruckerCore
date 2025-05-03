package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.user.data_helper.UserID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.entity.BaseEntity

data class  Company(
    override val id: CompanyID,
    override val persistence: Persistence,
    val allowedUserIds: Set<UserID>
) : BaseEntity {

    fun userHasSystemAccess(id: UserID): Boolean {
        TODO("Not yet implemented")
    }


}