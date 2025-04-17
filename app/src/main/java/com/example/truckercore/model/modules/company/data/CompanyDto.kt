package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.modules.user.data_helper.UserID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

data class CompanyDto(
    override val id: String? = null,
    override val persistence: Persistence? = null,
    val allowedUserIds: Set<UserID>? = null
) : BaseDto {


}