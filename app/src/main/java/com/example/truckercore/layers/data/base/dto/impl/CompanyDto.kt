package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import java.time.LocalDate
import java.util.Date

data class CompanyDto(
    override val id: String? = null,
    override val status: Status? = null,
    val cnpj: String? = null,
    val name: String? = null,
    val stateRegistration: String? = null,
    val municipalRegistration: String? = null,
    val opening: Date? = null
) : BaseDto