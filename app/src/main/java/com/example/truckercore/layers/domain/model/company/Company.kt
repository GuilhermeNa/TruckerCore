package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.departments.fleet.FleetDepartment
import com.example.truckercore.layers.domain.departments.hr.HrDepartment

/**
 * Represents a company within the system.
 *
 * A Company is a root entity that aggregates internal departments
 * such as HR and Fleet.
 */
data class Company(
    override val id: CompanyID,
    override val status: Status
) : BaseEntity {

    private val hr = HrDepartment()

    private val fleet = FleetDepartment()

}