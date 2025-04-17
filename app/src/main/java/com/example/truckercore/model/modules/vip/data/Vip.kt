package com.example.truckercore.model.modules.vip.data

import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.user.data_helper.UserID
import com.example.truckercore.model.modules.vip.data_helper.VipID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.interfaces.data.entity.Entity
import java.time.LocalDateTime

data class Vip(
    override val id: VipID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    val userId: UserID,
    val vipSince: LocalDateTime,
    val vipUntil: LocalDateTime
) : Entity {

    init {
        if (vipSince.isAfter(vipUntil))
            throw InvalidStateException("VIP start date cannot be after the VIP end date.")
    }

}