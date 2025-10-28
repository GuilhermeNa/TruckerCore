package com.example.truckercore.layers.domain.model.rig

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.RigID
import com.example.truckercore.layers.domain.model.crlv.Crlv
import com.example.truckercore.layers.domain.model.driver.Driver
import com.example.truckercore.layers.domain.model.driver_assignment.DriverAssignmentCollection
import com.example.truckercore.layers.domain.model.federal_aet.FederalAet
import com.example.truckercore.layers.domain.model.federal_aet.FederalAetCollection
import com.example.truckercore.layers.domain.model.hitch.HitchCollection
import com.example.truckercore.layers.domain.model.state_aet.StateAet
import com.example.truckercore.layers.domain.model.state_aet.StateAetCollection
import com.example.truckercore.layers.domain.model.tachograph.Tachograph
import com.example.truckercore.layers.domain.model.truck.Truck

data class Rig(
    override val id: RigID,
    override val companyId: CompanyID,
    override val status: Status,
    val truck: Truck
) : Entity {

    private val _driverAssignmentCollection = DriverAssignmentCollection()

    private val _hitchCollection = HitchCollection()

    private val _federalAetCollection = FederalAetCollection()

    private val _stateAetCollection = StateAetCollection()

    fun assignDriver(newDriver: Driver): Rig {
        if (driver != null) throw DomainException.RuleViolation(DRIVER_ASSIGN_ERROR_MSG)
        return copy(driver = newDriver)
    }

    fun addAll(documents: List<FederalAet>) {
        _federalAetCollection.addAll(documents)
    }

    fun addAll(documents: List<StateAet>) {
        _stateAetCollection.addAll(documents)
    }

    fun addAll(documents: List<Tachograph>) {
        truck.addAll(documents)
    }

    fun addAll(documents: List<Crlv>) {
        truck.addAll(documents)
    }

    //----------------------------------------------------------------------------------------------
    fun getActiveFederalAet() = _federalAetCollection.getActive()

    fun getActiveStateAet() = _stateAetCollection.getActive()

    fun getActiveTachograph() = truck.getActiveTachograph()

    fun getActiveCrlv() = truck.getActiveCrlv()

    //----------------------------------------------------------------------------------------------
    fun hasFederalAetExpiringSoon(withinDays: Long):Boolean =
        _federalAetCollection.hasExpiringSoon(withinDays)

    fun hasStateAetExpiringSoon(withinDays: Long):Boolean =
        _stateAetCollection.hasExpiringSoon(withinDays)

    fun hasTachographExpiringSoon(withinDays: Long): Boolean =
        truck.hasTachographExpiringSoon(withinDays)

    fun hasCrlvExpiringSoon(withinDays: Long): Boolean =
        truck.hasCrlvExpiringSoon(withinDays)

    private companion object {
        private const val DRIVER_ASSIGN_ERROR_MSG = "A driver is already assigned to this rig."
    }

}