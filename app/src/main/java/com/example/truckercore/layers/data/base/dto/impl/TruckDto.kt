package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.domain.base.enums.Color
import com.example.truckercore.layers.domain.base.enums.Status

class TruckDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val status: Status? = null,
    val color: Color? = null,
    val plate: String? = null,
    val chassi: String? = null,
    val renavam: String? = null,
    val yearModel: YearModelDto? = null
) : Dto {


}