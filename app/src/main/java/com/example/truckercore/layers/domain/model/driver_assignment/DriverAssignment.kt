package com.example.truckercore.layers.domain.model.driver_assignment

import com.example.truckercore.layers.domain.base.contracts.Entity
import com.example.truckercore.layers.domain.base.contracts.ID
import com.example.truckercore.layers.domain.base.contracts.DateRange
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.DriverID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Period

data class DriverAssignment(
    override val id: ID,
    override val companyId: CompanyID,
    override val status: Status,
    override val period: Period,
    val truckId: TruckID,
    val driverID: DriverID
) : Entity, DateRange {

}