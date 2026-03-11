package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.layers.domain.base.contracts.OptionalData
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import java.time.LocalDate

data class CompanyOptional(
    val cnpj: Cnpj,
    val name: CompanyName,
    val stateRegistration: StateRegistration,
    val municipalRegistration: MunicipalRegistration,
    val opening: LocalDate
) : OptionalData