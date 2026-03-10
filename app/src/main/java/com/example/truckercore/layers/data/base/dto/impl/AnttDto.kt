package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.domain.base.enums.AnttCategory
import com.example.truckercore.layers.domain.base.enums.Status

data class AnttDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val status: Status? = null,
    val url: String? = null,
    val periodDto: PeriodDto? = null,
    val category: AnttCategory? = null,
    val number: Int? = null
): Dto
