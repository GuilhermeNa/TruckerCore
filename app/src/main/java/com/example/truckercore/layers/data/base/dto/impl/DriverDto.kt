package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.domain.base.enums.Status

data class DriverDto(
    override val id: String?,
    override val companyId: String?,
    override val status: Status?
):Dto {
}