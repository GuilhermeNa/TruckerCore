package com.example.truckercore.model.shared.interfaces.data.entity

import com.example.truckercore.model.modules.company.data_helper.CompanyID

interface Entity: BaseEntity {
    val companyId: CompanyID
}