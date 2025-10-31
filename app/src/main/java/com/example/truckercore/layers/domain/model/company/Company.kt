package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.departments.fleet.FleetDepartment
import com.example.truckercore.layers.domain.departments.hr.HrDepartment

data class Company(
    override val id: CompanyID,
    override val status: Status,
) : BaseEntity {

    private val hr = HrDepartment()

    private val fleet = FleetDepartment()


}