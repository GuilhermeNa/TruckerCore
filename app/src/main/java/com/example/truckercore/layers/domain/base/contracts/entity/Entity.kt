package com.example.truckercore.layers.domain.base.contracts.entity

import com.example.truckercore.layers.domain.base.ids.CompanyID

interface Entity : BaseEntity {

    val companyId: CompanyID

}