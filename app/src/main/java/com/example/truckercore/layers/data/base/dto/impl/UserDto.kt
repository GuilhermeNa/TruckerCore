package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.domain.base.enums.Status

data class UserDto(
    val uid: String? = null,
    override val id: String? = null,
    override val companyId: String? = null,
    override val status: Status? = null
) : Dto
