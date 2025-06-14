package com.example.truckercore.model.modules._shared._contracts.entity

import com.example.truckercore.model.modules.company.data.CompanyID

interface Entity<T>: BaseEntity<T> {
    val companyId: CompanyID
}