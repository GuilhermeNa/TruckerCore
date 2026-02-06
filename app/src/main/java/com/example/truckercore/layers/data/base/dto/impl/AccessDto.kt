package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.model.access.Role

data class AccessDto(
    override val id: String? = null,
    override val status: Status? = null,
    override val companyId: String? = null,
    val authorized: Boolean? = null,
    val userId: String? = null,
    val role: Role? = null
) : Dto
