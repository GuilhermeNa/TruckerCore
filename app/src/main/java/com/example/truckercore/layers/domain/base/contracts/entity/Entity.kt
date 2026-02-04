package com.example.truckercore.layers.domain.base.contracts.entity

import com.example.truckercore.layers.domain.base.ids.CompanyID

/**
 * Base contract for all entities that belong to a company.
 */
interface Entity : BaseEntity {
    val companyId: CompanyID
}