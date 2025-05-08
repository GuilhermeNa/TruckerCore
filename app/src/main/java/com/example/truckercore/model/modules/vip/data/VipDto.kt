package com.example.truckercore.model.modules.vip.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import java.util.Date

internal data class VipDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null,
    val userId: String? = null,
    val vipSince: Date? = null,
    val vipUntil: Date? = null
) : Dto {
    override fun copyWith(id: String?): BaseDto {
        TODO("Not yet implemented")
    }


}