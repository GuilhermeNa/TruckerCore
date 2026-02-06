package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.domain.base.enums.Status

data class CompanyDto(
    override val id: String? = null,
    override val status: Status? = null
) : BaseDto