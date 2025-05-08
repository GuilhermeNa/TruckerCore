package com.example.truckercore.model.modules._contracts

import com.example.truckercore.model.modules.company.data.CompanyID

interface Entity: BaseEntity {
    val companyId: CompanyID
}